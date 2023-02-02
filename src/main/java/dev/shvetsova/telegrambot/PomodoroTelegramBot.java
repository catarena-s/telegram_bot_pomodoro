package dev.shvetsova.telegrambot;

import dev.shvetsova.telegrambot.DAO.TimerDao;
import dev.shvetsova.telegrambot._deprecated.Pomodoro2;
import dev.shvetsova.telegrambot._deprecated.model.Pomodoro;
import dev.shvetsova.telegrambot.menu.CommandParser;
import dev.shvetsova.telegrambot.menu.PomodoroMenu;
import dev.shvetsova.telegrambot.model.Timer;
import dev.shvetsova.utils.Resource;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PomodoroTelegramBot extends TelegramLongPollingBot {

    private static ConcurrentHashMap<Long, List<Timer>> userTimers = new ConcurrentHashMap<>();
    private final Pomodoro pomodoro;
    private final TimerDao timerDao;
//    BotMenuService botMenuService;

    public static ConcurrentHashMap<Long, List<Timer>> getUserTimers() {
        return userTimers;
    }
//    public IPomodoro getPomodoro() {
//        return pomodoro;
//    }


    public Pomodoro getPomodoro() {
        return pomodoro;
    }

    public PomodoroTelegramBot(TimerDao timerDao) {
        super();
//        botMenuService = new BotMenuService(new BotMenu());
        pomodoro = new Pomodoro();
        CommandParser.setBot(this);
        this.timerDao = timerDao;
//        this.timerDao = timerDao;
////        PomodoroMenu.setBot(this);//
        if (timerDao == null) return;
        userTimers.putAll(timerDao.loadData2());
    }
  /*  public PomodoroTelegramBot(TimerDao timerDao) {
        super();

*//*        this.timerDao = timerDao;
        userTimers.putAll(timerDao.loadData());
        PomodoroType.setTelegramBot(this);
        Menu.setTelegramBot(this);*//*
    }*/
  /*  public PomodoroTelegramBot() {
        this.timerDao = null;
    }
*/

    //    private static final String YOUR_TOKEN = "";

//    public void setStartBotMsg(boolean startBotMsg) {
//        isStartBotMsg = startBotMsg;
//
//    }


    public TimerDao getTimerDao() {
        return timerDao;
    }

    @Override
    public String getBotToken() {
        return Resource.YOUR_TOKEN;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public String getBotUsername() {
        return "Pomodoro bot";
    }

    private void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(keyboardMarkup);

        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow(1);
        row.add("/help");
        row.add("/settings");
        row.add("/go");

        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
    }

    @Override
    public void onUpdateReceived(Update update) {
//        Sticker sticker = null;
        boolean hasMassage = update.hasMessage();
        boolean hasCallbackQuery = update.hasCallbackQuery();
        if ((hasMassage && update.getMessage().hasText()) || (hasCallbackQuery)) {
            //
            String[] args = new String[0];
//            if(hasMassage)
            String message = hasMassage ? update.getMessage().getText()//.split(" ")
                    : update.getCallbackQuery().getData();
            //.split(" ");
//            Long userId = update.getMessage().getChatId();
//            String inputMessage = update.getMessage().getText();
//            String answer = "";
            long chatId = hasMassage ? update.getMessage().getChatId() :
                    update.getCallbackQuery().getMessage().getChatId();
//            PomodoroMenu pomodoroMenu = PomodoroMenu.ge
            PomodoroMenu pomodoroMenu = CommandParser.getMenu(message);
//            PomodoroMenu PomodoroMenu = CommandParser.parse(message, chatId);
//            BotMenuService botMenuService
            pomodoroMenu.run(chatId,  this);
        }
    }

 /*   public void checkTimer() throws InterruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        while (true) {
            userTimers.forEach((userId, list) -> {
                if (!list.isEmpty()) {
                    Timer timer = list.get(0);
                    if (Instant.now().isAfter(timer.time)) {
                        list.remove(timer);
                        Timer next = list.isEmpty() ? null : list.get(0);
                     //   timer.timerType.run(userId, timer);
                        switch (timer.timerType) {
                            case WORK: {
                                MsgSendService.sendMsg(this, userId, "Пора отдыхать" + "  " + LocalTime.now().format(formatter)
                                                + String.format(" (%dmin)",
                                                next.timerType == PomodoroStatus.BREAK ? pomodoro.getBreakTime() : pomodoro.getLongBreakTime())
                                        , PomodoroMenu.RUN);
//                                BotHelper.sendSticker(this, userId, Resourse.REST_ANIMATED_STICKER);
                                timerDao.taskDone(userId, timer.timerType.toString(), timer.time);
                                break;
                            }
                            case BREAK: {
                                startMsgIfHasNext(userId, timer ,next, "Таймер завершил свою работу");
                                timerDao.taskDone(userId, timer.timerType.toString(), timer.time);
                                break;
                            }
                            case LONG_BREAK: {
//                                BotHelper.sendMsg(this, userId, "Длинный таймер завершил свою работу");
//                                BotHelper.sendSticker(this, userId, Resourse.FINISH_WORK_STICKER);
                                startMsgIfHasNext(userId,timer, next, "Длинный таймер завершил свою работу");
                                break;
                            }
                        }
                    }
                }
            });
            Thread.sleep(1000);
        }
    }
*/
 /*   private void startMsgIfHasNext(Long userId,  Timer timer ,Timer next, String msg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (next != null) {
            String answer = MenuHelper.MSG_LET_WORK + "  " + LocalTime.now().format(formatter)
                    + String.format(" (%dmin)", pomodoro.getWorkTime());
            MsgSendService.sendMsg(this, userId, answer, PomodoroMenu.RUN);

//            BotHelper.sendSticker(this, userId, Resourse.START_WORK_ANIMATED_STICKER);
        } else {
            MsgSendService.sendMsg(this, userId, msg, PomodoroMenu.STOP);
            MsgSendService.sendSticker(this, userId, Resource.FINISH_WORK_STICKER);
        }
        timerDao.taskDone(userId, timer.timerType.toString(), timer.time);
    }*/
}
