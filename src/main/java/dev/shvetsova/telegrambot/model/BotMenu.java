package dev.shvetsova.telegrambot.model;

//import dev.shvetsova.telegrambot.bot.PomodoroTelegramBot;
//import dev.shvetsova.telegrambot.service.MsgSendService;

public class BotMenu {
    private BotMenu() {
    }
//    public static PomodoroMenu getMenu(String[] args, long userId, PomodoroTelegramBot bot){
//        PomodoroMenu pomodoroMenu = null;
//        for (int i = 0; i < args.length; i++) {
//            switch (args[i]) {
//                case "/start":
//                    pomodoroMenu = PomodoroMenu.START;
//                    break;
//                case "/run":
//                    pomodoroMenu = PomodoroMenu.RUN;
//                    break;
//                case "/help":
//                    pomodoroMenu = PomodoroMenu.HELP;
//                    break;
//                case "/help-short":
//                    pomodoroMenu = PomodoroMenu.HELP_SHORT;
//                    break;
//                case "/settings":
//                    pomodoroMenu = PomodoroMenu.SETTINGS;
//                    break;
//                case "/pomodoro-info":
//                    pomodoroMenu = PomodoroMenu.POMODORO_INFO;
//                    break;
//                case "/commands":
//                    pomodoroMenu = PomodoroMenu.COMMANDS_INFO;
//                    break;
//                case "/check":
//                    pomodoroMenu = PomodoroMenu.CHECK;
//                    break;
//                case "/stop": {
//                    pomodoroMenu = PomodoroMenu.STOP;
//                    break;
//                }
//                case "-w": {
//                    bot.getPomodoro().setWorkTime(Integer.parseInt(args[++i]));
//                    break;
//                }
//                case "-b": {
//                    bot.getPomodoro().setBreakTime(Integer.parseInt(args[++i]));
//                    break;
//                }
//                case "-l": {
//                    bot.getPomodoro().setLongBreakTime(Integer.parseInt(args[++i]));
//                    break;
//                }
//                case "-r": {
//                    bot.getPomodoro().setRepeats(Integer.parseInt(args[++i]));
//                    break;
//                }
//                case "":
//                    break;
///*                case "?":
//                    BotHelper.sendMsg(bot, userId, menu.getHelpCommand());
//                    break;*/
//                default:
//                    MsgSendService.sendMsg(bot, userId, "некорректная комманда", PomodoroMenu.START);
//                    return null;
//            }
//        }
//        /*if(args.length>1)
//        translateAnswer(args, userId, bot, pomodoroMenu);*/
//        return pomodoroMenu;
//    }
//    private static void translateAnswer(String[] args, long userId, PomodoroTelegramBot bot, PomodoroMenu menu) {
//        for (int i = 0; i < args.length; i++) {
//            String vsl = args[i+1];
//            switch (args[i]) {
//                case "-w": {
//                    bot.getPomodoro().setWorkTime(Integer.parseInt(args[++i]));
//                    break;
//                }
//                case "-b": {
//                    bot.getPomodoro().setBreakTime(Integer.parseInt(args[++i]));
//                    break;
//                }
//                case "-l": {
//                    bot.getPomodoro().setLongBreakTime(Integer.parseInt(args[++i]));
//                    break;
//                }
//                case "-r": {
//                    bot.getPomodoro().setRepeats(Integer.parseInt(args[++i]));
//                    break;
//                }
//                case "":
//                    break;
//                case "?":
//                    MsgSendService.sendMsg(bot, userId, menu.getHelpCommand());
//                    break;
//                default: {
//                    MsgSendService.sendMsg(bot, userId,
//                            String.format("Некорректные параметры для команды '%s' . " +
//                                    "Воспользуйтесь командой '/help' или '/%$0s -?'", menu.getCommand()));
//                }
//            }
//        }
//    }

}
