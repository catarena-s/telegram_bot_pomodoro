package dev.shvetsova.telegrambot.menu;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PomodoroMenu {
    START(new String[][]{
            MenuButtons.buttonHelp, MenuButtons.buttonCheck,
            MenuButtons.buttonRun, MenuButtons.buttonDemo}, true),
    HELP_SHORT(new String[][]{MenuButtons.buttonPomodoroInfo, MenuButtons.buttonCommandsInfo}, true),
    RUN(new String[][]{MenuButtons.buttonStop, MenuButtons.buttonHelp}, true),
    DEMO(new String[][]{MenuButtons.buttonStop, MenuButtons.buttonHelp}, true),
    HELP(new String[][]{MenuButtons.buttonPomodoroInfo, MenuButtons.buttonCommandsInfo}, true),
    CHECK(new String[][]{MenuButtons.buttonHelp, /*MenuButtons.buttonSettings,*/
            MenuButtons.buttonRun, MenuButtons.buttonDemo}, true),
    SETTINGS(null, false),
    POMODORO_INFO(new String[][]{MenuButtons.buttonHelp, MenuButtons.buttonCommandsInfo,
            MenuButtons.buttonRun, MenuButtons.buttonDemo,
            MenuButtons.buttonCheck}, true),
    COMMANDS_INFO(new String[][]{MenuButtons.buttonHelp, MenuButtons.buttonPomodoroInfo,
            MenuButtons.buttonRun, MenuButtons.buttonDemo,
            MenuButtons.buttonCheck}, true),
    STOP(new String[][]{MenuButtons.buttonHelp, MenuButtons.buttonCheck,
            MenuButtons.buttonRun, MenuButtons.buttonDemo}, true);

    private final String[][] settings;
    private final boolean hasInlineMenu;

    public boolean isHasInlineMenu() {
        return hasInlineMenu;
    }

    public String[][] getSettings() {
        return settings;
    }

}
