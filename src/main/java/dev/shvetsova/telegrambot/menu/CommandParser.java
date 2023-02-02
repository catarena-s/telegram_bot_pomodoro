package dev.shvetsova.telegrambot.menu;

import dev.shvetsova.telegrambot.service.MsgSendService;
import dev.shvetsova.telegrambot.PomodoroTelegramBot;

import java.util.*;

public class CommandParser {
    public static void setBot(PomodoroTelegramBot botPomodoro) {
        bot = botPomodoro;
    }

    private static PomodoroTelegramBot bot;
    private static Set<String> menu = new HashSet<>();

    static {
        menu.add("/start");
        menu.add("/help");
        menu.add("/run");
        menu.add("/help-short");
        menu.add("/settings");
        menu.add("/pomodoro");
        menu.add("/commands");
        menu.add("/stop");
    }

    ;

    public static PomodoroMenu parse(String input, long userId) {
        String[] commands = input.split(" ");
        Scanner scanner = new Scanner(input);
        PomodoroMenu pomodoroMenuCommands = null;
        if(menu.contains(commands[0])) pomodoroMenuCommands = getMenu(commands[0]);
        while (scanner.hasNext()) {
            String str = scanner.next();
            if (pomodoroMenuCommands == null && menu.contains(str)) {
                pomodoroMenuCommands = getMenu(str);
                continue;
            }
            String a = null;
            if (scanner.hasNext())
                a = scanner.next();
            checkCommand(str, a, userId);

            //pomodoroMenu = getMenu(commands[0]);
        }
   /*     if(commands.length==1) return pomodoroMenu;

        for (int i = 0; i < commands.length; i+=2) {
            String a= commands[i];
            String v = commands[i];
            chekCommand(a,v,userId);
        }*/
        return pomodoroMenuCommands;
    }

    private static void checkCommand(String a, String v, long userId) {
        if(v == null) {
            MsgSendService.sendMsg(bot, userId, "некорректная комманда", PomodoroMenu.START);
            return;
        }
        switch (a) {
            case "-w": {
                bot.getPomodoro().setWorkTime(Integer.parseInt(v));
                break;
            }
            case "-b": {
                bot.getPomodoro().setBreakTime(Integer.parseInt(v));
                break;
            }
            case "-l": {
                bot.getPomodoro().setLongBreakTime(Integer.parseInt(v));
                break;
            }
            case "-r": {
                bot.getPomodoro().setRepeats(Integer.parseInt(v));
                break;
            }
            default:
                MsgSendService.sendMsg(bot, userId, "некорректная комманда", PomodoroMenu.START);
        }
    }

    public static PomodoroMenu getMenu(String comand) {
        PomodoroMenu  pomodoroMenuCommands = null;
        switch (comand) {
            case "/start":
                pomodoroMenuCommands = PomodoroMenu.START;
                break;
            case "/run":
                pomodoroMenuCommands = PomodoroMenu.RUN;
                break;
            case "/help":
                pomodoroMenuCommands = PomodoroMenu.HELP;
                break;
            case "/help-short":
                pomodoroMenuCommands = PomodoroMenu.HELP_SHORT;
                break;
            case "/settings":
                pomodoroMenuCommands = PomodoroMenu.SETTINGS;
                break;
            case "/pomodoro-info":
                pomodoroMenuCommands = PomodoroMenu.POMODORO_INFO;
                break;
            case "/commands":
                pomodoroMenuCommands = PomodoroMenu.COMMANDS_INFO;
                break;
            case "/check":
                pomodoroMenuCommands = PomodoroMenu.CHECK;
                break;
            case "/stop": {
                pomodoroMenuCommands = PomodoroMenu.STOP;
                break;
            }
        }
        return pomodoroMenuCommands;
    }
}
