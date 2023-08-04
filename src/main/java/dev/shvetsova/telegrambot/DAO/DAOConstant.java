package dev.shvetsova.telegrambot.DAO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DAOConstant {
    public static final String INSERT_QUERY = "INSERT INTO timers(user_id, timer_type, timer) VALUES(?, ?, ?)";
    public static final String TASK_STOP_QUERY = "UPDATE timers SET isStop=true WHERE user_id=? AND timer_type=? AND timer=?";
    public static final String LOAD_QUERY = "select user_id, timer_type,timer from timers where isDone=false and isStop=false";
    public static final String TASK_DONE_QUERY = "UPDATE timers SET isdone=true WHERE user_id=? AND timer_type=? AND timer=?";
}
