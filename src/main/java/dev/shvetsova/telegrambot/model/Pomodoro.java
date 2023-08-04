package dev.shvetsova.telegrambot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pomodoro {
    private static final long TIME_SLEEP = 249;//249;//499;//60_000;// задержка 60 сек = 60_000 милисекунд

    protected int repeats;
    protected int breakTime;

    protected int longBreakTime;

    protected int longBreakSteps = 4;

    protected int workTime;

    protected int multiplier;
    private int step = 1;
    public void incrementSteps() {
        step++;
    }

    @Override
    public String toString() {
        return "repeats=" + repeats +
                ", workTime=" + workTime +
                ", breakTime=" + breakTime +
                ", longBreakTime=" + longBreakTime +
                ", longBreakSteps=" + longBreakSteps +
                ", multiplier=" + multiplier +
                ", step=" + step;
    }

    public void init(int workTime, int breakTime, int longBreakTime, int repeats, int multiplier) {
        this.repeats = repeats;
        this.breakTime = breakTime;
        this.longBreakTime = longBreakTime;
        this.workTime = workTime;
        this.multiplier = multiplier;
    }
}
