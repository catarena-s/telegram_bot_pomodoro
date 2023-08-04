package dev.shvetsova.telegrambot.DAO;

import dev.shvetsova.telegrambot.model.PomodoroStatus;
import dev.shvetsova.telegrambot.model.Timer;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static dev.shvetsova.telegrambot.DAO.DAOConstant.INSERT_QUERY;
import static dev.shvetsova.telegrambot.DAO.DAOConstant.LOAD_QUERY;
import static dev.shvetsova.telegrambot.DAO.DAOConstant.TASK_DONE_QUERY;
import static dev.shvetsova.telegrambot.DAO.DAOConstant.TASK_STOP_QUERY;

@Component
@RequiredArgsConstructor
public class TimerDao {
    private static final Logger log = LoggerFactory.getLogger(TimerDao.class);
    private final DataSource dataSource;

    public Map<Long, List<Timer>> loadData() {
        Map<Long, List<Timer>> dataSet = new ConcurrentHashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement(LOAD_QUERY);
            log.debug(query.toString());

            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                Long id = Long.parseLong(resultSet.getString("user_id"));
                PomodoroStatus type = PomodoroStatus.getType(resultSet.getString("timer_type"));
                Instant time = resultSet.getTimestamp("timer").toInstant();
                Timer timer = new Timer(time, type);
                List<Timer> timers = dataSet.getOrDefault(id, new LinkedList<>());
                timers.add(timer);
                dataSet.put(id, timers);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSet;
    }

    public void taskDone(Long userId, String type, Instant time) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement(TASK_DONE_QUERY);
            query.setString(1, String.valueOf(userId));
            query.setString(2, type);
            query.setTimestamp(3, Timestamp.from(time));
            log.debug(query.toString());
            query.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop(Long userId, @NotNull List<Timer> list) {
        try (Connection connection = dataSource.getConnection()) {
            for (Timer timer : list) {
                PreparedStatement query = connection.prepareStatement(TASK_STOP_QUERY);
                query.setString(1, String.valueOf(userId));
                query.setString(2, timer.timerType().toString());
                query.setTimestamp(3, Timestamp.from(timer.time()));
                log.debug(query.toString());
                query.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveData(long userId, @NotNull List<Timer> list) {
        try (Connection connection = dataSource.getConnection()) {
            for (Timer timer : list) {
                PreparedStatement query = connection.prepareStatement(INSERT_QUERY);
                query.setString(1, String.valueOf(userId));
                query.setString(2, timer.timerType().toString());
                query.setTimestamp(3, Timestamp.from(timer.time()));
                log.debug(query.toString());
                query.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
