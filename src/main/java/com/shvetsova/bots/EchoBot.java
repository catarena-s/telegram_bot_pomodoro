package com.shvetsova.bots;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EchoBot extends TelegramLongPollingBot {
    private static final String  YOUR_TOKEN="5467177008:AAGA-yVRyaHcEYLzNxqardMMDmqpuzdIVEQ";
    @Override
    public String getBotUsername() {
        return "Practice Cmd EchoBor";
    }

    @Override
    public String getBotToken() {
        return YOUR_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String inputMessage = update.getMessage().getText();
            String answer = "";
            switch (inputMessage){
                case "/start": answer = "Привет. Я Эхо-бот. Пообщаемя?";break;
                default: answer = inputMessage;
            }
            SendMessage msg = new SendMessage(update.getMessage().getChatId().toString(),
                    answer);
            try {
                execute(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
