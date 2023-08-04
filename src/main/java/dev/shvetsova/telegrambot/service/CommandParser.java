package dev.shvetsova.telegrambot.service;

import dev.shvetsova.telegrambot.menu.PomodoroMenu;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandParser {
    private static BotService botService;

    private static final Set<String> menu = new HashSet<>();

    static {
        menu.add("/start");
        menu.add("/help");
        menu.add("/run");
        menu.add("/demo");
        menu.add("/help-short");
        menu.add("/settings");
        menu.add("/check");
        menu.add("/pomodoro-info");
        menu.add("/commands");
        menu.add("/stop");
    }

    public static PomodoroMenu parse(String input, long userId) {
        PomodoroMenu pomodoroMenuCommands = null;
        try (Scanner scanner = new Scanner(input)) {
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

            }
        }
        return pomodoroMenuCommands;
    }

    public static void setBotService(BotService service) {
        botService = service;
    }

    private static void checkCommand(String a, String v, long userId) {
        if (v == null) {
            MsgSendService.sendMsg(userId, "некорректная команда", PomodoroMenu.START);
            return;
        }
        switch (a) {
            case "-w" -> botService.setWorkTime(Integer.parseInt(v));
            case "-b" -> botService.setBreakTime(Integer.parseInt(v));
            case "-l" -> botService.setLongBreakTime(Integer.parseInt(v));
            case "-r" -> botService.setRepeats(Integer.parseInt(v));
            default -> MsgSendService.sendMsg(userId, "некорректная команда", PomodoroMenu.START);
        }
    }

    public static PomodoroMenu getMenu(String command) {
        return switch (command) {
            case "/start" -> PomodoroMenu.START;
            case "/run" -> PomodoroMenu.RUN;
            case "/demo" -> PomodoroMenu.DEMO;
            case "/help" -> PomodoroMenu.HELP;
            case "/help-short" -> PomodoroMenu.HELP_SHORT;
            case "/settings" -> PomodoroMenu.SETTINGS;
            case "/pomodoro-info" -> PomodoroMenu.POMODORO_INFO;
            case "/commands" -> PomodoroMenu.COMMANDS_INFO;
            case "/check" -> PomodoroMenu.CHECK;
            case "/stop" -> PomodoroMenu.STOP;
            default -> null;
        };
    }
}
