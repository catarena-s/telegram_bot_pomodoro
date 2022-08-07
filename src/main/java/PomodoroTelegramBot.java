import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PomodoroTelegramBot extends TelegramLongPollingBot {

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        var pomodoroBot = new PomodoroTelegramBot();
        botsApi.registerBot(new PomodoroTelegramBot());

        new Thread(() -> {
            try {
                pomodoroBot.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).run();
    }

    public enum PomodoroType {
        WORK, BREAK, LONG_BREAK
    }

    private static final ConcurrentHashMap<PomodoroTelegramBot.Timer, Long> userTimers = new ConcurrentHashMap<>();
    private static final String YOUR_TOKEN = "5467177008:AAGA-yVRyaHcEYLzNxqardMMDmqpuzdIVEQ";
    private boolean isStartBotMsg = false;
    static class Timer {
        Instant time;
        PomodoroType timerType;


        public Timer(Instant time, PomodoroType type) {
            this.time = time;
            this.timerType = type;
        }
    }

    public PomodoroTelegramBot() {
        super();
    }

    private void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(keyboardMarkup);

        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow(1);
        row.add("help");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
    }

    @Override
    public String getBotUsername() {
        return "Pomodoro bot";
    }

    @Override
    public String getBotToken() {
        return YOUR_TOKEN;
    }

    private static final String START_WORK_STICKER = "src/main/resources/start_work.webp";
    private static final String START_WORK_ANIMATED_STICKER = "src/main/resources/AnimatedWorkedSticker.tgs";
    private static final String REST_ANIMATED_STICKER = "src/main/resources/AnimatedRestSticker.tgs";
    private static final String FINISH_WORK_STICKER = "src/main/resources/finished_work.webp";
    @Override
    public void onUpdateReceived(Update update) {
        Sticker sticker = null;
        if (update.hasMessage() && update.getMessage().hasText()) {
            var args = update.getMessage().getText().split(" ");
            var userId = update.getMessage().getChatId();
            String inputMessage = update.getMessage().getText();
            String answer = "";
            switch (args[0]) {
                case "/start":
                    answer = "Привет. Я Pomodoro-бот. как будем работать?\n Введи время работы и оттыха";
                    isStartBotMsg =true;
                    break;
                default: {
                    answer = "Давай работай!";
                    isStartBotMsg = false;
                    if (args.length >= 1) {
                        var workTime = Instant.now().plus(Long.parseLong(args[0]), ChronoUnit.MINUTES);
                        userTimers.put(new Timer(workTime, PomodoroType.WORK), userId);
                        if (args.length == 2) {
                            var breakTime = workTime.plus(Long.parseLong(args[1]), ChronoUnit.MINUTES);
                            userTimers.put(new Timer(breakTime, PomodoroType.BREAK), userId);
                        }
                        sendSticker(update.getMessage().getChatId(),START_WORK_ANIMATED_STICKER);
                    }

                }

            }
            sendMsg(update.getMessage().getChatId(), answer,sticker);

        }
    }

    private void sendSticker(Long chatId, String sticker) {
        Path path = Paths.get(sticker);
        InputFile stickerfile = new InputFile(path.toFile());
        try {
            execute(new SendSticker(chatId.toString(),stickerfile));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMsg(Long chatId, String msgStr, Sticker sticker) {
        SendMessage msg = new SendMessage(chatId.toString(), msgStr);
        msg.enableMarkdown(true);
        try {
            if(isStartBotMsg)  setButtons(msg);

            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void run() throws InterruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        while (true) {
        //    System.out.printf("Количество таймеров пользователей = %d\n", userTimers.size());
            userTimers.forEach((timer, userId) -> {
        //        System.out.printf("Проверка userId = %d, userTime = %s, now = %s\n", userId, timer.toString(), LocalTime.now().format(formatter));
                if (Instant.now().isAfter(timer.time)) {
                    userTimers.remove(timer);
                    switch (timer.timerType) {
                        case WORK: {
                            sendMsg(userId, "Пора отдыхать", null);
                            sendSticker(userId,REST_ANIMATED_STICKER);
                            break;
                        }
                        case BREAK: {
                            sendMsg(userId, "Таймер завершил свою работу", null);
                            sendSticker(userId,FINISH_WORK_STICKER);
                            break;
                        }
                        case LONG_BREAK: {
                            sendMsg(userId, "Длинный таймер завершил свою работу", null);
                            break;
                        }
                    }
                }
            });
            Thread.sleep(1000);
        }
    }
}
