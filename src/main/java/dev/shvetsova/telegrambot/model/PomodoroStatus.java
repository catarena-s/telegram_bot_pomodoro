package dev.shvetsova.telegrambot.model;

public enum PomodoroStatus {
    WORK("Длинный таймер завершил свою работу"),
    BREAK("Таймер завершил свою работу"),
    LONG_BREAK("Пора отдыхать");

    public String getMessage() {
        return message;
    }

    private final String message;
    PomodoroStatus(String msg) {
        message = msg;
    }

    public static PomodoroStatus getType(String str) {
        return switch (str) {
            case "WORK" -> WORK;
            case "BREAK" -> BREAK;
            case "LONG_BREAK" -> LONG_BREAK;
            default -> null;
        };
    }
}
