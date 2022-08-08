package com.shvetsova.bots;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PomodoroTelegramBot extends TelegramLongPollingBot {
    public boolean isRun() {
        return isRun;
    }

    private boolean isRun = false;

    public enum PomodoroType {
        WORK, BREAK, LONG_BREAK
    }

    private static final String START_WORK_STICKER = "src/main/resources/start_work.webp";
    private static final String START_WORK_ANIMATED_STICKER = "src/main/resources/AnimatedWorkedSticker.tgs";
    private static final String REST_ANIMATED_STICKER = "src/main/resources/AnimatedRestSticker.tgs";
    private static final String FINISH_WORK_STICKER = "src/main/resources/finished_work.webp";
    private static final ConcurrentHashMap<PomodoroTelegramBot.Timer, Long> userTimers = new ConcurrentHashMap<>();
    private static final String YOUR_TOKEN = "5467177008:AAGA-yVRyaHcEYLzNxqardMMDmqpuzdIVEQ";
    //    private static final String YOUR_TOKEN = "";
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
        row.add("/help");
        row.add("/settings");
        row.add("/go");

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
                    answer = """
                            Привет. Я Pomodoro-бот. как будем работать?
                            Введи время работы и оттыха в формате: 
                            -w[время работы] -b[время отдыха] -r[количество повторов] -l[длинный перерыв]""";
                    isStartBotMsg = true;
                    sendMsg(update.getMessage().getChatId(), answer);
                    break;
                case "/help":
                    answer = """
                            «Метод помидора» — техника управления временем, предложенная Франческо Чирилло в конце 1980-х. 
                            Методика предполагает увеличение эффективности работы при меньших временных затратах за счёт 
                            глубокой концентрации и коротких перерывов. 
                            Доступные команды:
                            -help - помощь
                            -d - использовать значения по умолчанию(будет исполнена строка команд -w 25 -b 5 -l 15 -r 1 m-1)
                            -w - сколько работать (мин)\\t-> по умолчанию = 25
                            -b - сколько отдыхать (мин)\\t-> по умолчанию = 5
                            -l - длинный перерыв после 4го помидора (мин)\\t-> по умолчанию = 15
                            -r - количество повторов \\t-> по умолчанию = 1
                            -m - множитель(увеличивает время работы не следующих шага) \\t-> по умолчанию = 1
                            Пример : -w 30 -b 5 -r 2 m-2
                            1) работа 30 мин отдых 5
                            2) работа 60 мин отдых 5
                            """;
                    isStartBotMsg = false;
                    sendMsg(update.getMessage().getChatId(), answer);

                    break;
                case "/go":
                    isRun = true;
                    sendSticker(update.getMessage().getChatId(), START_WORK_ANIMATED_STICKER);
                    break;
                default: {
                    answer = "Давай работай!";
                    isRun=false;
                    isStartBotMsg = false;
                    if (args.length >= 1) {
                        var workTime = Instant.now().plus(Long.parseLong(args[0]), ChronoUnit.MINUTES);
                        userTimers.put(new Timer(workTime, PomodoroType.WORK), userId);
                        if (args.length == 2) {
                            var breakTime = workTime.plus(Long.parseLong(args[1]), ChronoUnit.MINUTES);
                            userTimers.put(new Timer(breakTime, PomodoroType.BREAK), userId);
                        }

                    }
                    sendMsg(update.getMessage().getChatId(), answer);
                }

            }


        }
    }

    private void sendSticker(Long chatId, String sticker) {
        Path path = Paths.get(sticker);
        InputFile stickerfile = new InputFile(path.toFile());
        try {
            execute(new SendSticker(chatId.toString(), stickerfile));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMsg(Long chatId, String msgStr) {
        SendMessage msg = new SendMessage(chatId.toString(), msgStr);
        msg.enableMarkdown(true);
        try {
            if (isStartBotMsg) setButtons(msg);

            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void checkTimer() throws InterruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        while (true) {
            //    System.out.printf("Количество таймеров пользователей = %d\n", userTimers.size());
            if(isRun()){
                userTimers.forEach((timer, userId) -> {
                //        System.out.printf("Проверка userId = %d, userTime = %s, now = %s\n", userId, timer.toString(), LocalTime.now().format(formatter));
                if (Instant.now().isAfter(timer.time)) {
                    userTimers.remove(timer);
                    switch (timer.timerType) {
                        case WORK: {
                            sendMsg(userId, "Пора отдыхать");
                            sendSticker(userId, REST_ANIMATED_STICKER);
                            if(! userTimers.containsValue(userId)) isRun = false;
                            break;
                        }
                        case BREAK: {
                            sendMsg(userId, "Таймер завершил свою работу");
                            sendSticker(userId, FINISH_WORK_STICKER);

                            break;
                        }
                        case LONG_BREAK: {
                            sendMsg(userId, "Длинный таймер завершил свою работу");
                            break;
                        }
                    }
                }
            });

            }
            Thread.sleep(1000);
        }
    }
}
