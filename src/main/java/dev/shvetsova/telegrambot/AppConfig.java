package dev.shvetsova.telegrambot;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.shvetsova.telegrambot.DAO.TimerDao;
import dev.shvetsova.telegrambot.service.BotService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/datasourse.origin.properties")
public class AppConfig {
    @Value(value = "${db.url}")
    private String dbUrl;
    @Value(value = "${db.user}")
    private String dbUser;

    @Value(value = "${db.password}")
    private String dbPassword;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
//        hikariConfig.setUsername("postgres");
//        hikariConfig.setPassword("postgres");
        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setUsername(dbUser);
        hikariConfig.setPassword(dbPassword);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(PomodoroTelegramBot bot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
        BotService botService = bot.getBotService();
        new Thread(() -> {
            try {
                botService.checkTimer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).run();
        return botsApi;
    }
}
