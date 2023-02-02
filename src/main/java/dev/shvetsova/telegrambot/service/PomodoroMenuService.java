package dev.shvetsova.telegrambot.service;

import dev.shvetsova.telegrambot.PomodoroTelegramBot;
import dev.shvetsova.telegrambot._deprecated.PomodoroStatus;
import dev.shvetsova.telegrambot._deprecated.model.Pomodoro;
import dev.shvetsova.telegrambot.model.Timer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PomodoroMenuService {
    public void stopWork(PomodoroTelegramBot bot, long userId) {
        ConcurrentHashMap<Long, List<Timer>> userTimers2 = PomodoroTelegramBot.getUserTimers();
        List<Timer> list = userTimers2.get(userId);
        if (list.isEmpty() || list == null) return;
        bot.getTimerDao().stop(userId, list);
        list.clear();
//        bot.getTimerDao().stop(userId, list);
//        list.clear();

    }

    public void runPomodoro(long userId, PomodoroTelegramBot telegramBot, Pomodoro pomodoro) {
        int countRepeats = pomodoro.getRepeats();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

//        String answer = MenuHelper.MSG_LET_WORK+"  " + LocalTime.now().format(formatter) +
//                String.format(" (%dmin)",pomodoro.getWorkTime());
        ConcurrentHashMap<Long, List<Timer>> userTimers = PomodoroTelegramBot.getUserTimers();
        List<Timer> list = userTimers.getOrDefault(userId, new LinkedList<>());//Collections.synchronizedList(userTimers.getOrDefault(userId,new LinkedList<>()));
        Instant breakTime = Instant.now();
        for (int j = 1; j <= countRepeats; j++) {
            Instant workTime = breakTime.plus(Long.valueOf(pomodoro.getWorkTime()), ChronoUnit.MINUTES);
            list.add(new Timer(workTime, PomodoroStatus.WORK));
            if (j % 2 == 1) {
                breakTime = workTime.plus(Long.valueOf(pomodoro.getBreakTime()), ChronoUnit.MINUTES);
                list.add(new Timer(breakTime, PomodoroStatus.BREAK));
            } else {
                breakTime = workTime.plus(Long.valueOf(pomodoro.getLongBreakTime()), ChronoUnit.MINUTES);
                list.add(new Timer(breakTime, PomodoroStatus.LONG_BREAK));
            }

        }
        userTimers.put(userId, list);
        telegramBot.getTimerDao().saveData(userId, list);

//        BotHelper.sendMsg(bot1, userId, answer, RUN);
    }

}
