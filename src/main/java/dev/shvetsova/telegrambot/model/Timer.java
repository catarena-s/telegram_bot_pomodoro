package dev.shvetsova.telegrambot.model;

import dev.shvetsova.telegrambot._deprecated.PomodoroStatus;

import java.time.Instant;

public class Timer {
    public Instant time;
    public PomodoroStatus timerType;

    public Timer(Instant time, PomodoroStatus type) {
        this.time = time;
        this.timerType = type;

    }
}
