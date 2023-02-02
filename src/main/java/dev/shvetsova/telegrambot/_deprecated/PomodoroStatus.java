package dev.shvetsova.telegrambot._deprecated;

//import dev.shvetsova.telegrambot.bot.PomodoroTelegramBot;
//import dev.shvetsova.telegrambot.model.pomodoro.Timer;
@Deprecated
public enum PomodoroStatus {
    WORK("Длинный таймер завершил свою работу"),
    BREAK("Таймер завершил свою работу"),
    LONG_BREAK("Пора отдыхать");

    public String getMessage() {
        return message;
    }

    private String message;
//    private static PomodoroTelegramBot pomodoroTelegramBot = new PomodoroTelegramBot(null);
    PomodoroStatus(String msg) {
        message = msg;
    }

    public static PomodoroStatus getType(String str) {
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

//    public void run(long userId, Timer timer) {
//      /*  switch (this) {
//            case WORK -> {
//             *//*   BotHelper.sendMsg(pomodoroTelegramBot, userId, MSG_LET_REST);
//                BotHelper.sendSticker(pomodoroTelegramBot, userId, Resourse.REST_ANIMATED_STICKER);
//                pomodoroTelegramBot.getTimerDao().taskDone(userId, toString(), timer.time);*//*
//            }
//            case BREAK -> {
//               *//* BotHelper.sendMsg(pomodoroTelegramBot, userId, MSG_TIMER_STOPED);
//                BotHelper.sendSticker(pomodoroTelegramBot, userId, Resourse.FINISH_WORK_STICKER);
//                pomodoroTelegramBot.getTimerDao().taskDone(userId, toString(), timer.time);*//*
//            }
//            case LONG_BREAK -> BotHelper.sendMsg(pomodoroTelegramBot, userId, MSG_LONG_TIMER_STOPED);
//        }*/
//    }
//
//    static void setTelegramBot(PomodoroTelegramBot telegramBot) {
//   //     pomodoroTelegramBot = telegramBot;
//    }
}
