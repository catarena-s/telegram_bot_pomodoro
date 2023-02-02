package dev.shvetsova.tools;

public class HelpPrinter {
    private HelpPrinter() {
    }

    public static void printMessage(String message) {
        System.out.print(message);
    }

    public static void printEmptySting() {
        System.out.println();
    }

    public static void printMessage(String messageTemplate, Object... args) {
        System.out.printf(messageTemplate, args);
    }

    public static void printCommands() {
        printMessage(IConstante.MSG_COMMANDS_LIST);
    }

    public static void helpMessage() {
        printMessage(IConstante.MSG_COMMANDS_LIST + IConstante.MSG_EXAMPLES);
    }

    public static void printSeparator() {
        printMessage("-------------------------------------------------------------------------------------------\n");
    }
}
