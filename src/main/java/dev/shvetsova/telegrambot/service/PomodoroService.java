package dev.shvetsova.telegrambot.service;

import dev.shvetsova.telegrambot.DAO.TimerDao;
import dev.shvetsova.telegrambot.model.Pomodoro;
import dev.shvetsova.telegrambot.model.PomodoroStatus;
import dev.shvetsova.telegrambot.model.Timer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

@NoArgsConstructor
public class PomodoroService {
    public void stopWork(long userId, ConcurrentMap<Long, List<Timer>> userTimers, TimerDao timerDao) {
        List<Timer> list = userTimers.get(userId);
        if (list.isEmpty()) return;
        timerDao.stop(userId, list);
        list.clear();
    }

    public void runPomodoro(long userId, Pomodoro pomodoro, ConcurrentMap<Long, List<Timer>> userTimers, TimerDao timerDao) {
        int countRepeats = pomodoro.getRepeats();
        List<Timer> list = userTimers.getOrDefault(userId, new LinkedList<>());
        Instant breakTime = Instant.now();
        for (int j = 1; j <= countRepeats; j++) {
            Instant workTime = breakTime.plus(pomodoro.getWorkTime(), ChronoUnit.MINUTES);
            list.add(new Timer(workTime, PomodoroStatus.WORK));
            if (j % 2 == 1) {
                breakTime = workTime.plus(pomodoro.getBreakTime(), ChronoUnit.MINUTES);
                list.add(new Timer(breakTime, PomodoroStatus.BREAK));
            } else {
                breakTime = workTime.plus(pomodoro.getLongBreakTime(), ChronoUnit.MINUTES);
                list.add(new Timer(breakTime, PomodoroStatus.LONG_BREAK));
            }

        }
        userTimers.put(userId, list);
        timerDao.saveData(userId, list);
    }

}
