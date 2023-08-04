package dev.shvetsova.telegrambot.model;
public class PomodoroDefault extends Pomodoro {
    public static final int COUNT_REPEAT = 2;// количество повторов
    public static final int DEFAULT_MULTIPLIER = 1;// множитель
    public static final int DEFAULT_WORK_TIME = 25;// время работы
    public static final int DEFAULT_BREAK_TIME = 5;// перерыв
    public static final int DEFAULT_LONG_BREAK_TIME = 10;// долгий перерыв
    private static final int LONG_BREAK_STEPS = 4;

    public PomodoroDefault() {
        repeats = COUNT_REPEAT;
        breakTime = DEFAULT_BREAK_TIME;
        longBreakTime = DEFAULT_LONG_BREAK_TIME;
        workTime = DEFAULT_WORK_TIME;
        multiplier = DEFAULT_MULTIPLIER;
    }

    @Override
    public int getLongBreakSteps() {
        return LONG_BREAK_STEPS;
    }
}
