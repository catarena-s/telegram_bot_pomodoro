package dev.shvetsova.telegrambot.service;

import dev.shvetsova.telegrambot.DAO.TimerDao;
import dev.shvetsova.telegrambot.PomodoroTelegramBot;
import dev.shvetsova.telegrambot._deprecated.PomodoroStatus;
import dev.shvetsova.telegrambot._deprecated.model.Pomodoro;
import dev.shvetsova.telegrambot.menu.PomodoroMenu;
import dev.shvetsova.telegrambot.model.Timer;
import dev.shvetsova.utils.MenuHelper;
import dev.shvetsova.utils.Resource;

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BotService {
    private final ConcurrentHashMap<Long, List<Timer>> userTimers;
    private final TimerDao timerDao;
    private final PomodoroTelegramBot telegramBot;
    private final Pomodoro pomodoro;

    public BotService(PomodoroTelegramBot telegramBot) {
        this.userTimers = PomodoroTelegramBot.getUserTimers();
        this.timerDao = telegramBot.getTimerDao();
        this.telegramBot = telegramBot;
        this.pomodoro = telegramBot.getPomodoro();
//        this.timerDao = timerDao;
//        PomodoroMenu.setBot(this);//
//        if (timerDao == null) return;
//        userTimers.putAll(timerDao.loadData2());
    }

    public void checkTimer() throws InterruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        while (true) {
            userTimers.forEach((userId, list) -> {
                if (!list.isEmpty()) {
                    Timer timer = list.get(0);
                    if (Instant.now().isAfter(timer.time)) {
                        list.remove(timer);
                        Timer next = list.isEmpty() ? null : list.get(0);
                        //   timer.timerType.run(userId, timer);
                        switch (timer.timerType) {
                            case WORK: {
                                MsgSendService.sendMsg(telegramBot, userId, "Пора отдыхать" + "  " + LocalTime.now().format(formatter)
                                                + String.format(" (%dmin)",
                                                next.timerType == PomodoroStatus.BREAK ?
                                                        pomodoro.getBreakTime() :
                                                        pomodoro.getLongBreakTime())
                                        , PomodoroMenu.RUN);
//                                BotHelper.sendSticker(this, userId, Resourse.REST_ANIMATED_STICKER);
                                timerDao.taskDone(userId, timer.timerType.toString(), timer.time);
                                break;
                            }
                            case BREAK: {
                                startMsgIfHasNext(userId, timer, next, "Таймер завершил свою работу");
                                timerDao.taskDone(userId, timer.timerType.toString(), timer.time);
                                break;
                            }
                            case LONG_BREAK: {
//                                BotHelper.sendMsg(this, userId, "Длинный таймер завершил свою работу");
//                                BotHelper.sendSticker(this, userId, Resourse.FINISH_WORK_STICKER);
                                startMsgIfHasNext(userId, timer, next, "Длинный таймер завершил свою работу");
                                break;
                            }
                        }
                    }
                }
            });
            Thread.sleep(1000);
        }
    }

    private void startMsgIfHasNext(Long userId, Timer timer, Timer next, String msg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (next != null) {
            String answer = MenuHelper.MSG_LET_WORK + "  " + LocalTime.now().format(formatter)
                    + String.format(" (%dmin)", pomodoro.getWorkTime());
            MsgSendService.sendMsg(telegramBot, userId, answer, PomodoroMenu.RUN);

//            BotHelper.sendSticker(this, userId, Resourse.START_WORK_ANIMATED_STICKER);
        } else {
            MsgSendService.sendMsg(telegramBot, userId, msg, PomodoroMenu.STOP);
            MsgSendService.sendSticker(telegramBot, userId, Resource.FINISH_WORK_STICKER);
        }
        timerDao.taskDone(userId, timer.timerType.toString(), timer.time);
    }
}
