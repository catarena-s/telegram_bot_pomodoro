package com.shvetsova.bots;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
@Configuration
public class AppConfig {
    /*@Bean
    public EchoBot echoBot(){
        return new EchoBot();
    }
    @Bean
    public TelegramBotsApi telegramBotsApi(EchoBot echoBot)throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(echoBot);
        return botsApi;
    }*/

    @Bean
    public PomodoroTelegramBot pomodoroBot(){
        return new PomodoroTelegramBot();
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(PomodoroTelegramBot bot)throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);

        new Thread(() -> {
            try {

                    bot.checkTimer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).run();
        return botsApi;
    }
}
