package dev.shvetsova.telegrambot.service;

import dev.shvetsova.telegrambot.menu.PomodoroMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MsgSendService {
    private static final Logger log = LoggerFactory.getLogger(MsgSendService.class);

    public static String SAND_CLOCK_gif = "src/main/resources/bot/loading-windows98.gif";
    private static AbsSender sender;

    private static Integer lastId;

    private MsgSendService() {
    }

    public static void sendMsg(Long chatId, String msgStr, PomodoroMenu menu) {
        log.debug("userId= {}, massage = {}", chatId, msgStr);
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

    public static void setSender(AbsSender sender) {
        MsgSendService.sender = sender;
    }

    private static SendAnimation initAnimation(Long chatId) {
        SendAnimation animation;
        Path path = Paths.get(SAND_CLOCK_gif);
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
            sender.execute(new SendSticker(chatId.toString(), stickerFile));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
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
            keyboardButtonsRow.addAll(Arrays.asList(inlineKeyboardButtons).subList(0, 2));
            keyboardButtonsRow2.addAll(Arrays.asList(inlineKeyboardButtons).subList(2, inlineKeyboardButtons.length));
        } else {
            Collections.addAll(keyboardButtonsRow, inlineKeyboardButtons);
        }

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); //Создаём ряд
        rowList.add(keyboardButtonsRow);
        if (!keyboardButtonsRow2.isEmpty()) rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }

}
