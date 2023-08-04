package dev.shvetsova.telegrambot;

import dev.shvetsova.telegrambot.DAO.TimerDao;
import dev.shvetsova.telegrambot.service.BotService;
import dev.shvetsova.telegrambot.service.MsgSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@PropertySource("classpath:bot.origin.properties")
public class PomodoroTelegramBot extends TelegramLongPollingBot {
    private BotService botService;
    private static final Logger log = LoggerFactory.getLogger(PomodoroTelegramBot.class);

    @Autowired
    public PomodoroTelegramBot(TimerDao timerDao) {
        super();
        botService = new BotService(this, timerDao);
        MsgSendService.setSender(this);
    }

    public BotService getBotService() {
        return botService;
    }

    @Value(value = "${TOKEN}")
    public String YOUR_TOKEN;

    @Override
    public String getBotToken() {
        return YOUR_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return "Pomodoro bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        boolean hasMassage = update.hasMessage();
        boolean hasCallbackQuery = update.hasCallbackQuery();
        if ((hasMassage && update.getMessage().hasText()) || (hasCallbackQuery)) {
            String message = hasMassage ? update.getMessage().getText()
                    : update.getCallbackQuery().getData();

            long chatId = hasMassage ? update.getMessage().getChatId() :
                    update.getCallbackQuery().getMessage().getChatId();
            log.debug("user = {}, message = {}", chatId, message);
            botService.execute(chatId, message);
        }
    }
}
