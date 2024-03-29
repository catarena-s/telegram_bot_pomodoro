package dev.shvetsova.telegrambot.model;
public class PomodoroDemo extends PomodoroDefault {
    private static final int LONG_BREAK_STEPS = 3;
    public static final long TIME_SLEEP_DEMO = 1;
    public PomodoroDemo() {
        super();
        repeats = 2;
        breakTime = 1;
        longBreakTime = 1;
        workTime = 1;
        multiplier = 1;
    }

    @Override
    public int getLongBreakSteps() {
        return LONG_BREAK_STEPS;
    }

    public long getTIME_SLEEP() {
        return TIME_SLEEP_DEMO;
    }
}
