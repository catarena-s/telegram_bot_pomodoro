package dev.shvetsova.telegrambot.model;

import java.time.Instant;

public record Timer(Instant time, PomodoroStatus timerType) {
}
