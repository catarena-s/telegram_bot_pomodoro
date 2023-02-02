package dev.shvetsova.telegrambot.DAO;

import dev.shvetsova.telegrambot._deprecated.PomodoroStatus;
import dev.shvetsova.telegrambot.model.Timer;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TimerDao implements IDAOConstant {
    private final DataSource dataSource;

    public TimerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveData(Long userId, String type, Instant time) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement(INSERT_QUERY);
            query.setString(1, String.valueOf(userId));
            query.setString(2, type);
            query.setTimestamp(3, Timestamp.from(time));
            query.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Timer, Long> loadData() {
        Map<Timer, Long> dataSet = new ConcurrentHashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement(LOAD_QUERY);

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Long id = Long.parseLong(resultSet.getString("user_id"));
                PomodoroStatus type = PomodoroStatus.getType(resultSet.getString("timer_type"));
                Instant time = resultSet.getTimestamp("timer").toInstant();
                Timer timer = new Timer(time, type);
                dataSet.put(timer, id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSet;
    }

    public Map<Long, List<Timer>> loadData2() {
        Map<Long, List<Timer>> dataSet = new ConcurrentHashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement(LOAD_QUERY);

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
                query.setString(2, timer.timerType.toString());
                query.setTimestamp(3, Timestamp.from(timer.time));
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
                query.setString(2, timer.timerType.toString());
                query.setTimestamp(3, Timestamp.from(timer.time));
                query.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
