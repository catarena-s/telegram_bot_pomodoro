package dev.shvetsova.telegrambot;

import dev.shvetsova.telegrambot.DAO.TimerDao;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.shvetsova.telegrambot.service.BotService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    /*@Bean
    public PomodoroTelegramBot pomodoroBot(TimerDao timerDao){
        return new PomodoroTelegramBot(timerDao);
    }
    */
    @Bean
    public PomodoroTelegramBot PomodoroTelegramBot(TimerDao timerDao){
        return new PomodoroTelegramBot(timerDao);
    }

    @Bean
    public DataSource dataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("postgres");
        return new HikariDataSource(hikariConfig);
    }
    @Bean
    public TimerDao timerDao(DataSource dataSource){
        return new TimerDao(dataSource);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(PomodoroTelegramBot bot)throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
        BotService botService = new BotService(bot);
        new Thread(() -> {
            try {
                botService.checkTimer();
//                bot.checkTimer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).run();
        return botsApi;
    }
}
