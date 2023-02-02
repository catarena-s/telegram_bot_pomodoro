package dev.shvetsova.telegrambot._deprecated;
@Deprecated
public class Pomodoro2 {
    private int repeats = 1;
    private int breakTime = 1;
    private int longBreakTime = 1;
    private int workTime = 1;
    //    private int multiplier = 1;
    private int step = 1;

    public int getStep() {
        return step;
    }

    public void incrementSteps() {
        step++;
    }

    public void restartCalcSteps() {
        step = 1;
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public int getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(int breakTime) {
        this.breakTime = breakTime;
    }

    public int getLongBreakTime() {
        return longBreakTime;
    }

    public void setLongBreakTime(int longBreakTime) {
        this.longBreakTime = longBreakTime;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

//    public int getMultiplier() {
//        return multiplier;
//    }
//
//    public void setMultiplier(int multiplier) {
//        this.multiplier = multiplier;
//    }

    @Override
    public String toString() {
        return "Pomodoro settings:" + "\n"
                + "   workTime= " + workTime + "\n"
                + "   breakTime= " + breakTime + "\n"
                + "   longBreakTime= " + longBreakTime + "\n"
                + "   repeats= " + repeats + "\n"
//              +  "   multiplier= " + multiplier + "\n"
                ;
    }

    public void init(int workTime, int breakTime, int longBreakTime, int repeats, int multiplier) {

    }
}
