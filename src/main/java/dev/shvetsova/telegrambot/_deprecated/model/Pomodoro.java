package dev.shvetsova.telegrambot._deprecated.model;

@Deprecated
public class Pomodoro {
    private static final long TIME_SLEEP = 249;//249;//499;//60_000;// задержка 60 сек = 60_000 милисекунд

    protected int repeats;
    protected int breakTime;

    protected int longBreakTime;

    protected int longBreakSteps = 4;

    protected int workTime;

    protected int multiplier;
    private int step = 1;

    public long getTIME_SLEEP() {
        return TIME_SLEEP;
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

    public int getLongBreakSteps() {
        return longBreakSteps;
    }

    public void setLongBreakSteps(int longBreakSteps) {
        this.longBreakSteps = longBreakSteps;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
    /*  @Override
    public String toString() {
        return "Pomodoro settings:" + "\n" +
                "workTime=" + workTime + "\n" +
                "breakTime=" + breakTime + "\n" +
                "longBreakTime=" + longBreakTime + "\n" +
                "repeats=" + repeats + "\n" +
                "multiplier=" + multiplier + "\n"
                ;
    }



    public void setLongBreakSteps(int longBreakSteps) {
        this.longBreakSteps = longBreakSteps;
    }

    public int getLongBreakSteps() {
        return longBreakSteps;
    }*/

//    protected boolean isDemoMode = false;
//    public boolean isDemoMode() {
//        return isDemoMode;
//    }
//
//    public void setDemoMode(boolean demoMode) {
//        isDemoMode = demoMode;

//    }

   /* public int getStep() {
        return step;
    }*/

    public void incrementSteps() {
        step++;
    }
//    public void restartCalcSteps(){

//        step = 1;

//    }
//    public void setDefault() {
//        repeats = COUNT_REPEAT;
//        breakTime = DEFAULT_BREAK_TIME;
//        longBreakTime = DEFAULT_LONG_BREAK_TIME;
//        workTime = DEFAULT_WORK_TIME;
//        multiplier = DEFAULT_MULTIPLIER;

//    }
//    public void setDemo(){
//        repeats = 2;
//        breakTime = 5;
//        longBreakTime = 15;
//        workTime = DEFAULT_WORK_TIME;
//        multiplier = DEFAULT_MULTIPLIER;

//    }

/*    public String getDefault() {
        final String format = "-w %d -b %d -l %d -r %d m- %d\n";
        return String.format(format, DEFAULT_WORK_TIME, DEFAULT_BREAK_TIME, DEFAULT_LONG_BREAK_TIME, COUNT_REPEAT, DEFAULT_MULTIPLIER);
    }*/

    /* public int getRepeats() {
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

     public int getMultiplier() {
         return multiplier;
     }

     public void setMultiplier(int multiplier) {
         this.multiplier = multiplier;
     }
 */
    public void init(int workTime, int breakTime, int longBreakTime, int repeats, int multiplier) {
        this.repeats = repeats;
        this.breakTime = breakTime;
        this.longBreakTime = longBreakTime;
        this.workTime = workTime;
        this.multiplier = multiplier;
//        this.step = step;
    }
}
