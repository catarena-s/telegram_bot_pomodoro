package dev.shvetsova.telegrambot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class AppConfig {
    @Bean
    public EchoBot echoBot(){
        return new EchoBot();
    }
    @Bean
    public TelegramBotsApi telegramBotsApi(EchoBot echoBot)throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(echoBot);
        return botsApi;
    }
}
