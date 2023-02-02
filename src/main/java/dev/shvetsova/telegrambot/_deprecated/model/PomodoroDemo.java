package dev.shvetsova.telegrambot._deprecated.model;
@Deprecated
public class PomodoroDemo extends PomodoroDefault {
    private static final int LONG_BREAK_STEPS = 3;
    public static final long TIME_SLEEP_DEMO = 1;//249;//499;//60_000;// задержка 60 сек = 60_000 милисекунд
    public PomodoroDemo() {
        super();
        repeats = 3;
    }

    @Override
    public int getLongBreakSteps() {
        return LONG_BREAK_STEPS;
    }

    public long getTIME_SLEEP() {
        return TIME_SLEEP_DEMO;
    }


}
