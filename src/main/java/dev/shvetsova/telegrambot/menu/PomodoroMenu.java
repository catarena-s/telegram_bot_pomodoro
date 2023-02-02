package dev.shvetsova.telegrambot.menu;

import dev.shvetsova.telegrambot.PomodoroTelegramBot;
import dev.shvetsova.telegrambot._deprecated.Pomodoro2;
import dev.shvetsova.telegrambot._deprecated.model.Pomodoro;
import dev.shvetsova.telegrambot.service.MsgSendService;
import dev.shvetsova.telegrambot.service.PomodoroMenuService;
import dev.shvetsova.utils.MenuHelper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static dev.shvetsova.utils.MenuHelper.MSG_HELP_COMANDS;
import static dev.shvetsova.utils.MenuHelper.MSG_HELP_Pomodoro;

public enum PomodoroMenu {
    START("/start", new String[][]{MenuButtons.buttonHelp, MenuButtons.buttonRun, MenuButtons.buttonCheck}, true),
    HELP_SHORT("/help-short", new String[][]{MenuButtons.buttonPomodoroInfo, MenuButtons.buttonCommandsInfo}, true),
    RUN("/run", new String[][]{MenuButtons.buttonStop, MenuButtons.buttonHelp}, true),
    HELP("/help", new String[][]{MenuButtons.buttonPomodoroInfo, MenuButtons.buttonCommandsInfo}, true),
    CHECK("/check", new String[][]{MenuButtons.buttonHelp, MenuButtons.buttonRun, MenuButtons.buttonSettings}, true),
    SETTINGS("/settings", null, false),
    POMODORO_INFO("/pomodoro", new String[][]{MenuButtons.buttonHelp, MenuButtons.buttonCommandsInfo, MenuButtons.buttonRun, MenuButtons.buttonCheck}, true),
    COMMANDS_INFO("/commands", new String[][]{MenuButtons.buttonHelp, MenuButtons.buttonPomodoroInfo, MenuButtons.buttonRun, MenuButtons.buttonCheck}, true),
    STOP("/stop", new String[][]{MenuButtons.buttonHelp, MenuButtons.buttonRun, MenuButtons.buttonCheck}, true);

    private String[] args;

    private String command;
    private String[][] settings;
    private boolean hasInlineMenu;
    private static Pomodoro pomodoro = null;
    private int repeats;
    private int breakTime;
    private int longBreakTime;
    private int workTime;
    private int multiplier;

    //    private static PomodoroTelegramBot bot = null;
    private final PomodoroMenuService pomodoroMenuService = new PomodoroMenuService();

    public boolean isHasInlineMenu() {
        return hasInlineMenu;
    }

    public String getCommand() {
        return command;
    }

    public static void setBot(final PomodoroTelegramBot pomodoroTelegramBot) {
//        bot = pomodoroTelegramBot;
        pomodoro = pomodoroTelegramBot.getPomodoro();
    }

    public String[][] getSettings() {
        return settings;
    }

    public String getHelpCommand() {
        return Arrays.toString(settings);
    }

    PomodoroMenu(String s, String[][] strings, boolean hasInlineMenu) {
        this.command = s;
        this.settings = strings;
        this.hasInlineMenu = hasInlineMenu;
        repeats = 1;
        breakTime = 1;
        longBreakTime = 1;
        workTime = 1;
        multiplier = 1;
//        this.pomodoro = new Pomodoro();
    }
//
//    public static PomodoroMenu getMenu(String[] args, long userId, PomodoroTelegramBot bot) {
//        PomodoroMenu current = null;
//        for (int i = 0; i < args.length; i++) {
//            switch (args[i]) {
//                case "/start":
//                    current = START;
//                    break;
///*                case "/settings":
//                    current = SETTINGS;
//                    break;*/
//                case "/run":
//                    current = RUN;
//                    break;
//                case "/help":
//                    current = HELP;
//                    break;
///*                case "/work":
//                    current = WORK;
//                    break;*/
//                case "/check":
//                    current = CHECK;
//                    break;
//                case "/stop": {
//                    current = STOP;
//                    break;
//                }
//                default:
//                    translateAnswer(args, userId, bot, i);
//                    return current;
////                BotHelper.sendMsg(bot, userId, pomodoro.toString());
//            }
//        }
//        return current;
//    }

    public void run(long userId, PomodoroTelegramBot telegramBot) {
        pomodoro = telegramBot.getPomodoro();
        switch (this) {
            case START -> {
                MsgSendService.sendMsg(telegramBot, userId, MenuHelper.MENU_START_MESSAGE, START);
//                break;
            }
            case HELP -> {
                MsgSendService.sendMsg(telegramBot, userId, "Чем помочь?", HELP_SHORT);
//                break;
            }
            case RUN -> {
                pomodoro.init(workTime, breakTime, longBreakTime, repeats, multiplier);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String answer = MenuHelper.MSG_LET_WORK + "  " + LocalTime.now().format(formatter) +
                        String.format(" (%dmin)", pomodoro.getWorkTime());
                pomodoroMenuService.runPomodoro(userId, telegramBot, pomodoro);
                MsgSendService.sendMsg(telegramBot, userId, answer, RUN);
//                runPomodoro(userId, bot1);
//                break;
            }
            case CHECK -> {
                MsgSendService.sendMsg(telegramBot, userId, pomodoro.toString(), CHECK);
//                break;
            }
            case POMODORO_INFO -> {
                MsgSendService.sendMsg(telegramBot, userId, MSG_HELP_Pomodoro, POMODORO_INFO);
//                break;
            }
            case COMMANDS_INFO -> {
                MsgSendService.sendMsg(telegramBot, userId, MSG_HELP_COMANDS, COMMANDS_INFO);
//                break;
            }
            case STOP -> {
//                stopWork(bot1, userId);
                pomodoroMenuService.stopWork(telegramBot, userId);
                MsgSendService.sendMsg(telegramBot, userId, "Таймер остановлен", STOP);
//                break;
            }
            default -> {
            }
        }
    }
//
//    private void stopWork(PomodoroTelegramBot bot1, long userId) {
//        ConcurrentHashMap<Long, List<Timer>> userTimers2 = bot1.getUserTimers();
//        List<Timer> list = userTimers2.get(userId);
////        synchronized (list) {
//        if (list.isEmpty() || list == null) return;
//        bot1.getTimerDao().stop(userId, list);
//        list.clear();
////        }
//
//    }
//
//    private void runPomodoro(long userId, PomodoroTelegramBot bot1) {
//        int countRepeats = pomodoro.getRepeats();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//
//        String answer = MenuHelper.MSG_LET_WORK + "  " + LocalTime.now().format(formatter) +
//                String.format(" (%dmin)", pomodoro.getWorkTime());
//        ConcurrentHashMap<Long, List<Timer>> userTimers = bot1.getUserTimers();
//        List<Timer> list = userTimers.getOrDefault(userId, new LinkedList<>());
//        //Collections.synchronizedList(userTimers.getOrDefault(userId,new LinkedList<>()));
//        Instant breakTime = Instant.now();
//        for (int j = 1; j <= countRepeats; j++) {
//            Instant workTime = breakTime.plus(Long.valueOf(pomodoro.getWorkTime()), ChronoUnit.MINUTES);
//            list.add(new Timer(workTime, PomodoroStatus.WORK));
//            if (j % 2 == 1) {
//                breakTime = workTime.plus(Long.valueOf(pomodoro.getBreakTime()), ChronoUnit.MINUTES);
//                list.add(new Timer(breakTime, PomodoroStatus.BREAK));
//            } else {
//                breakTime = workTime.plus(Long.valueOf(pomodoro.getLongBreakTime()), ChronoUnit.MINUTES);
//                list.add(new Timer(breakTime, PomodoroStatus.LONG_BREAK));
//            }
//
//        }
//        userTimers.put(userId, list);
//        bot1.getTimerDao().saveData(userId, list);
//
//        BotHelper.sendMsg(bot, userId, answer, RUN);
//    }

//    private static void translateAnswer(String[] args, long userId, PomodoroTelegramBot currentBot, int index) {
//        for (int i = index; i < args.length; i++) {
//            switch (args[i]) {
//                case "-w":
//                    pomodoro.setWorkTime(Integer.parseInt(args[++i]));
//                    break;
//                case "-b":
//                    pomodoro.setBreakTime(Integer.parseInt(args[++i]));
//                    break;
//                case "-l":
//                    pomodoro.setLongBreakTime(Integer.parseInt(args[++i]));
//                    break;
//                case "-r":
//                    pomodoro.setRepeats(Integer.parseInt(args[++i]));
//                    break;
//                case "":
//                    break;
//                default: {
//                    BotHelper.sendMsg(currentBot, userId, "некорректная комманда", START);
//                }
//            }
//        }
//    }
}
