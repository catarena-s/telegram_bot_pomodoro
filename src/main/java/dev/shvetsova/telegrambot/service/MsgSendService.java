package dev.shvetsova.telegrambot.service;

import dev.shvetsova.utils.Resource;
import dev.shvetsova.telegrambot.menu.PomodoroMenu;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MsgSendService {
    static Integer lastId = null;
    private MsgSendService() {
    }

    public static void sendMsg(AbsSender sender, Long chatId, String msgStr) {
        SendMessage msg = new SendMessage(chatId.toString(), msgStr);
        msg.enableMarkdown(true);
//        setButtons(msg);
        try {
            sender.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void sendMsg(AbsSender sender, Long chatId, String msgStr, PomodoroMenu menu) {
        SendMessage msg = new SendMessage(chatId.toString(), msgStr);
        msg.enableMarkdown(true);
        if (lastId != null && (menu == PomodoroMenu.RUN || menu == PomodoroMenu.STOP)) {
            delMessage(sender, chatId);
        }
        SendAnimation animation = null;
        if (menu == PomodoroMenu.RUN) {
            animation = initAnimation(chatId);
        }
        sendInlineKeyBoardMessage(msg, menu);
        try {
            sender.execute(msg);
            if (animation != null) {
                Message message = sender.execute(animation);
                lastId = message.getMessageId();
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static SendAnimation initAnimation(Long chatId) {
        SendAnimation animation;
        Path path = Paths.get(Resource.SAND_CLOCK_gif);
        InputFile stickerFile = new InputFile(path.toFile());
        animation = new SendAnimation(chatId.toString(), stickerFile);
        return animation;
    }

    private static void delMessage(AbsSender sender, Long chatId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(lastId);
        lastId = null;
        try {
            sender.execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendSticker(AbsSender sender, Long chatId, String sticker) {
        Path path = Paths.get(sticker);
        InputFile stickerFile = new InputFile(path.toFile());
        try {
            //    setButtons(new SendMessage(chatId.toString(), ""));
            sender.execute(new SendSticker(chatId.toString(), stickerFile));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(keyboardMarkup);

        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow(1);
        KeyboardButton button = new KeyboardButton("help");
        button.setText("/help");

        KeyboardRow row2 = new KeyboardRow(2);
        row2.add("/check");
        row2.add("/stop");

        keyboard.add(row1);
        keyboard.add(row2);
        keyboardMarkup.setKeyboard(keyboard);
    }

    public static void sendInlineKeyBoardMessage(SendMessage sendMessage, PomodoroMenu menu) {
        if (!menu.isHasInlineMenu()) return;

        String[][] commands = menu.getSettings();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //Создаем объект разметки клавиатуры

        InlineKeyboardButton[] inlineKeyboardButtons = new InlineKeyboardButton[commands.length];
        for (int i = 0; i < commands.length; i++) {
            inlineKeyboardButtons[i] = new InlineKeyboardButton();
            inlineKeyboardButtons[i].setText(commands[i][1]); //Текст самой кнопки
            inlineKeyboardButtons[i].setCallbackData(commands[i][0]);
            inlineKeyboardButtons[i].setPay(false);
        }


        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        if (commands.length > 2) {
            for (int i = 0; i < 2; i++) {
                keyboardButtonsRow.add(inlineKeyboardButtons[i]);
            }
            for (int i = 2; i < inlineKeyboardButtons.length; i++) {
                keyboardButtonsRow2.add(inlineKeyboardButtons[i]);
            }
        } else {
            Collections.addAll(keyboardButtonsRow, inlineKeyboardButtons);
        }

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); //Создаём ряд
        rowList.add(keyboardButtonsRow);
        if (!keyboardButtonsRow2.isEmpty()) rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);


    } // Кнопки

}
