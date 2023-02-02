package dev.shvetsova.telegrambot._deprecated;

//import dev.shvetsova.telegrambot.pomodoro_bot.DAO.TimerDao;
//import dev.shvetsova.telegrambot.bot.Resource;
//import dev.shvetsova.telegrambot.model.pomodoro.Timer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
@Deprecated
public class PomodoroTelegramBot2 extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
  /*  private boolean isRun = false;
    private boolean isStartBotMsg = false;
    private static ConcurrentHashMap<Timer, Long> userTimers = new ConcurrentHashMap<>();

    private final TimerDao timerDao;
    public PomodoroTelegramBot2(TimerDao timerDao) {
        super();
        this.timerDao = timerDao;
      //  userTimers.putAll(timerDao.loadData());
   *//*     PomodoroStatus.setTelegramBot(this);
        Menu.setTelegramBot(this);*//*
    }
    public PomodoroTelegramBot2() {
        this.timerDao = null;
    }

    public void setRun(boolean run) {
        isRun = run;
    }
    //    private static final String YOUR_TOKEN = "";

    public void setStartBotMsg(boolean startBotMsg) {
        isStartBotMsg = startBotMsg;

    }

    public static ConcurrentHashMap<Timer, Long> getUserTimers() {
        return userTimers;
    }
*//*
    public TimerDao getTimerDao() {
        return timerDao;
    }*//*
    @Override
    public void onRegister() {
        super.onRegister();

    }
    public boolean isRun() {
        return isRun;
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
        return Resource.YOUR_TOKEN;
    }


    @Override
    public void onUpdateReceived(Update update) {
//        Sticker sticker = null;
        if (update.hasMessage() && update.getMessage().hasText()) {
            String[] args = update.getMessage().getText().split(" ");
//            Long userId = update.getMessage().getChatId();
//            String inputMessage = update.getMessage().getText();
//            String answer = "";
           // Menu menu = Menu.getMenu(args,update.getMessage().getChatId(),this);
            //menu.run(update.getMessage().getChatId(),this);
//            sendMsg(update.getMessage().getChatId(), answer);
        *//*    switch (args[0]) {
                case "/start":
                    answer = "Привет. Я Pomodoro-бот. Как будем работать?\n" +
                            "Введи время работы и оттыха  в формате:\n" +
                            "<время работы> <время отдыха>\n" +
                            "Например(работать: 30 мин, отдыхать: 5 мин): 30 5";//  <количество повторов>  <длинный перерыв> ";
                    isStartBotMsg = true;
                    BotHelpper.sendMsg(this, update.getMessage().getChatId(), answer);
//                    sendMsg(update.getMessage().getChatId(), answer);
                    break;
                case "/help":
                    answer = "«Метод помидора» — техника управления временем, предложенная Франческо Чирилло в конце 1980-х. \n" +
                            "Методика предполагает увеличение эффективности работы при меньших временных затратах за счёт \n" +
                            "глубокой концентрации и коротких перерывов. \n" +
                            "Доступные команды:\n" +
                            "-help - помощь\n" +
                            "-d - использовать значения по умолчанию(будет исполнена строка команд -w 25 -b 5 -l 15 -r 1 m-1)\n" +
                            "-w - сколько работать (мин)\t-> по умолчанию = 25\n" +
                            "-b - сколько отдыхать (мин)\t-> по умолчанию = 5\n" +
                            "-l - длинный перерыв после 4го помидора (мин)\t-> по умолчанию = 15\n" +
                            "-r - количество повторов \t-> по умолчанию = 1\n" +
                            "-m - множитель(увеличивает время работы не следующих шага) \t-> по умолчанию = 1\n" +
                            "Пример : -w 30 -b 5 -r 2 m-2\n" +
                            "1) работа 30 мин отдых 5\n" +
                            "2) работа 60 мин отдых 5";
                    isStartBotMsg = false;
                    BotHelpper.sendMsg(this, update.getMessage().getChatId(), answer);

                    break;
                case "/go":
                    isRun = true;
                    BotHelpper.sendSticker(this, update.getMessage().getChatId(), Property.START_WORK_ANIMATED_STICKER);
                    break;
                case "/settings":
                    break;
                default: {
                    isRun = false;
                    answer = "Давай работай!";
                    isStartBotMsg = false;
                    if (args.length >= 1) {
                        Instant workTime = Instant.now().plus(Long.parseLong(args[0]), ChronoUnit.MINUTES);
                        userTimers.put(new Timer(workTime, PomodoroType.WORK), userId);
                        timerDao.saveData(update.getMessage().getChatId(), PomodoroType.WORK.toString(), workTime);
                        if (args.length == 2) {
                            Instant breakTime = workTime.plus(Long.parseLong(args[1]), ChronoUnit.MINUTES);
                            userTimers.put(new Timer(breakTime, PomodoroType.BREAK), userId);
                            timerDao.saveData(update.getMessage().getChatId(), PomodoroType.BREAK.toString(), breakTime);
                        }
                    }
                    BotHelpper.sendMsg(this, update.getMessage().getChatId(), answer);
                    BotHelpper.sendSticker(this, update.getMessage().getChatId(), Property.START_WORK_ANIMATED_STICKER);
                }

            }*//*
        }
    }

//    private void sendSticker(Long chatId, String sticker) {
//        Path path = Paths.get(sticker);
//        InputFile stickerFile = new InputFile(path.toFile());
//        try {
//            execute(new SendSticker(chatId.toString(), stickerFile));
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    private void sendMsg(Long chatId, String msgStr) {
//        SendMessage msg = new SendMessage(chatId.toString(), msgStr);
//        msg.enableMarkdown(true);
//        try {
//            execute(msg);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
    public void checkTimer() throws InterruptedException {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        while (true) {
            userTimers.forEach((timer, userId) -> {
                if (Instant.now().isAfter(timer.time)) {
                    userTimers.remove(timer);
                    timer.timerType.run(userId, timer);
*//*                    switch (timer.timerType) {
                        case WORK: {
                            BotHelpper.sendMsg(this,userId, "Пора отдыхать");
                            sendSticker(userId, Property.REST_ANIMATED_STICKER);
                            timerDao.taskDone(userId, timer.timerType.toString(), timer.time);
                            break;
                        }
                        case BREAK: {
                            BotHelpper.sendMsg(this,userId, "Таймер завершил свою работу");
                            sendSticker(userId, Property.FINISH_WORK_STICKER);
                            timerDao.taskDone(userId, timer.timerType.toString(), timer.time);
                            break;
                        }
                        case LONG_BREAK: {
                            BotHelpper.sendMsg(this,userId, "Длинный таймер завершил свою работу");
                            break;
                        }
                    }*//*
                }
            });
            Thread.sleep(1000);
        }
    }*/
}
