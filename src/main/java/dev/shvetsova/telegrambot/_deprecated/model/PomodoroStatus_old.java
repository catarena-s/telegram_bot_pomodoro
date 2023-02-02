package dev.shvetsova.telegrambot._deprecated.model;

import lombok.Getter;
@Deprecated
@Getter
public enum PomodoroStatus_old {
    WORK("Work", "#%02d %9s -%9s | ","Длинный таймер завершил свою работу"),
    BREAK("Break", " %12s -%9s | ","Таймер завершил свою работу"),
    LONG_BREAK("Long break", " %12s -%9s | ","Пора отдыхать");

    private final String massage;
    private final String dataStrFormat;
    private String report;

    PomodoroStatus_old(String string, String dataStrFormat, String report) {
        this.massage = string;
        this.dataStrFormat = dataStrFormat;
        this.report = report;
    }

//    public String getDataStrFormat() {
//        return dataStrFormat;
//    }
//
//    public String getMassage() {
//        return massage;
//    }

//    public long getCountPick(Pomodoro pomodoro) {
//        return switch (this) {
//            case WORK -> pomodoro.getWorkTime();
//            case BREAK -> pomodoro.getBreakTime();
//            case LONG_BREAK -> pomodoro.getLongBreakTime();
//        };
//    }
    public static PomodoroStatus_old getType(String str) {
        switch (str) {
            case "WORK":
                return WORK;
            case "BREAK":
                return BREAK;
            case "LONG_BREAK":
                return LONG_BREAK;
            default:
                return null;
        }
    }

}
