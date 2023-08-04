package dev.shvetsova.telegrambot.service;

import dev.shvetsova.telegrambot.DAO.TimerDao;
import dev.shvetsova.telegrambot.PomodoroTelegramBot;
import dev.shvetsova.telegrambot.menu.PomodoroMenu;
import dev.shvetsova.telegrambot.model.Pomodoro;
import dev.shvetsova.telegrambot.model.PomodoroDemo;
import dev.shvetsova.telegrambot.model.PomodoroStatus;
import dev.shvetsova.telegrambot.model.Timer;
import dev.shvetsova.telegrambot.utils.MenuHelper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static dev.shvetsova.telegrambot.utils.MenuHelper.MSG_HELP_COMMANDS;
import static dev.shvetsova.telegrambot.utils.MenuHelper.MSG_HELP_POMODORO;

@Getter
@Setter
@PropertySource("classpath:bot.properties")
public class BotService {
    @Value(value = "${FINISH.WORK.STICKER.url}")
    public String FINISH_WORK_STICKER;

    private final ConcurrentHashMap<Long, List<Timer>> userTimers = new ConcurrentHashMap<>();
    private final TimerDao timerDao;
    private final PomodoroTelegramBot telegramBot;
    private boolean isDemoMode = false;
    private final Pomodoro pomodoro;
    private final PomodoroDemo pomodoroDemo;
    private int repeats;
    private int breakTime;
    private int longBreakTime;
    private int workTime;
    private int multiplier;
    private final PomodoroService pomodoroService = new PomodoroService();

    public BotService(PomodoroTelegramBot telegramBot, TimerDao timerDao) {
        CommandParser.setBotService(this);

        this.telegramBot = telegramBot;
        this.pomodoro = new Pomodoro();
        this.pomodoroDemo = new PomodoroDemo();

        this.timerDao = timerDao;
        if (timerDao == null) return;
        userTimers.putAll(timerDao.loadData());

        repeats = 1;
        breakTime = 5;
        longBreakTime = 15;
        workTime = 25;
        multiplier = 1;
        pomodoro.init(workTime, breakTime, longBreakTime, repeats, multiplier);
    }

    public void checkTimer() throws InterruptedException {
        while (true) {
            userTimers.forEach((userId, list) -> {
                if (!list.isEmpty()) {
                    Timer timer = list.get(0);
                    if (Instant.now().isAfter(timer.time())) {
                        list.remove(timer);
                        Timer next = list.isEmpty() ? null : list.get(0);
                        setStatus(userId, timer, next, isDemoMode ? pomodoroDemo : pomodoro);
                    }
                }
            });
            Thread.sleep(1000);
        }
    }

    private void setStatus(Long userId, Timer timer, Timer next, Pomodoro pomodoro) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String demo = isDemoMode ? "demo - " : "";
        switch (timer.timerType()) {
            case WORK -> {
                MsgSendService.sendMsg(userId,
                        demo + "Пора отдыхать" + "  " + LocalTime.now().format(formatter)
                                + String.format(" (%dmin)",
                                next.timerType().equals(PomodoroStatus.BREAK) ?
                                        pomodoro.getBreakTime() :
                                        pomodoro.getLongBreakTime())
                        , PomodoroMenu.RUN);
                timerDao.taskDone(userId, timer.timerType().toString(), timer.time());
            }
            case BREAK -> {
                startMsgIfHasNext(userId, timer, next, demo + "Таймер завершил свою работу", pomodoro);
                timerDao.taskDone(userId, timer.timerType().toString(), timer.time());
            }
            case LONG_BREAK -> {
                startMsgIfHasNext(userId, timer, next, demo + "Длинный таймер завершил свою работу", pomodoro);
            }
        }
    }

    private void startMsgIfHasNext(Long userId, Timer timer, Timer next, String msg, Pomodoro pomodoro) {
        String demo = isDemoMode ? "demo - " : "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (next != null) {
            String answer = demo + MenuHelper.MSG_LET_WORK + "  " + LocalTime.now().format(formatter)
                    + String.format(" (%dmin)", pomodoro.getWorkTime());
            MsgSendService.sendMsg(userId, answer, PomodoroMenu.RUN);

        } else {
            MsgSendService.sendMsg(userId, msg, PomodoroMenu.STOP);
            MsgSendService.sendSticker(telegramBot, userId, FINISH_WORK_STICKER);
        }
        timerDao.taskDone(userId, timer.timerType().toString(), timer.time());
    }

    public void execute(long chatId, String message) {

        PomodoroMenu pomodoroMenu = CommandParser.parse(message, chatId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        switch (pomodoroMenu) {
            case START -> {
                MsgSendService.sendMsg(chatId, MenuHelper.MENU_START_MESSAGE, PomodoroMenu.START);
            }
            case HELP -> {
                MsgSendService.sendMsg(chatId, "Чем помочь?", PomodoroMenu.HELP_SHORT);
            }
            case RUN -> {
                isDemoMode = false;
                pomodoro.init(workTime, breakTime, longBreakTime, repeats, multiplier);
                startPomodoro(chatId, formatter, pomodoro);
            }
            case DEMO -> {
                isDemoMode = true;
                startPomodoro(chatId, formatter, pomodoroDemo);
            }
            case CHECK -> {
                MsgSendService.sendMsg(chatId, pomodoro.toString(), PomodoroMenu.CHECK);
            }
            case POMODORO_INFO -> {
                MsgSendService.sendMsg(chatId, MSG_HELP_POMODORO, PomodoroMenu.POMODORO_INFO);
            }
            case COMMANDS_INFO -> {
                MsgSendService.sendMsg(chatId, MSG_HELP_COMMANDS, PomodoroMenu.COMMANDS_INFO);
            }
            case STOP -> {
                String demo = isDemoMode ? "demo - " : "";
                pomodoroService.stopWork(chatId, userTimers, timerDao);
                MsgSendService.sendMsg(chatId, demo + "Таймер остановлен", PomodoroMenu.STOP);
            }
            default -> {
            }
        }
    }

    private void startPomodoro(long chatId, DateTimeFormatter formatter, Pomodoro currentPomodoro) {
        String demo = isDemoMode ? "demo - " : "";
        String answer = demo + MenuHelper.MSG_LET_WORK + "  " +
                LocalTime.now().format(formatter) +
                String.format(" (%dmin)", currentPomodoro.getWorkTime());
        pomodoroService.runPomodoro(chatId, currentPomodoro, userTimers, timerDao);
        MsgSendService.sendMsg(chatId, answer, PomodoroMenu.RUN);
    }
}
