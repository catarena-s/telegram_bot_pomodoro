package dev.shvetsova.telegrambot.DAO;

public interface IDAOConstant {
    String INSERT_QUERY = "INSERT INTO public.timers(user_id, timer_type, timer) VALUES(?, ?, ?)";
    String TASK_STOP_QUERY = "UPDATE public.timers SET isStop=true WHERE user_id=? AND timer_type=? AND timer=?";
    String LOAD_QUERY = "select user_id, timer_type,timer from public.timers where isDone=false and isStop=false";
    String TASK_DONE_QUERY = "UPDATE public.timers SET isdone=true WHERE user_id=? AND timer_type=? AND timer=?";
}
