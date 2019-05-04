package org.pva.hibernateChatBot.telegramBot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import java.util.Map;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class Bot extends AbilityBot {

    private static final String BOT_TOKEN = System.getenv("TELEGRAM_TOKEN");
    private static final String BOT_USERNAME = "ReminderVxBot";
    private static final Integer BOT_CREATOR_ID = 127155577;

    public Bot() {
        super(BOT_TOKEN, BOT_USERNAME);
    }

    @Override
    public int creatorId() {
        return BOT_CREATOR_ID;
    }

    public Ability replyToStart() {
        String MESSAGE = "Привет, username!\n" +
                "Ты зашет в бот-напоминайку!\n" +
                "Очем напомнить?\n" +
                "/addSimpleReminder - добавить простое напоминание\n" +
                "/addCircleReminder - добавить циклическое напоминание\n" +
                "/viewActiveReminders - показать активные напоминания\n" +
                "/viewCircleReminders - показать все циклические напоминания\n" +
                "/viewClosestReminders - показать ближайшие напоминания\n" +
                "Настройки:\n" +
                "/start - начало работы\n" +
                "/settings - настройки пользователя\n" +
                "/help - помощь";
        return Ability
                .builder()
                .name("start")
                .info("Starts the bot!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(MESSAGE, ctx.chatId()))
                .build();
    }

    public Ability sayHelloWorld() {
        return Ability
                .builder()
                .name("hello")
                .info("says hello world!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Hello world!!!!!!!!!!!!!", ctx.chatId()))
                .build();
    }

    public Ability useDatabaseToCountPerUser() {
        return Ability.builder()
                .name("count")
                .info("Increments a counter per user")
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(ctx -> {
                    Map<String, Integer> countMap = db.getMap("COUNTERS");
                    int userId = ctx.user().getId();

                    Integer counter = countMap.compute(String.valueOf(userId), (id, count) -> count == null ? 1 : ++count);

                    String message = String.format("%s, your count is now *%d*!", ctx.user().getLastName(), counter);
                    silent.send(message, ctx.chatId());
                })
                .build();
    }

//    @Override
//    public void onUpdateReceived(Update update) {
//        Message message = update.getMessage();
//        String text = message.getText();
//        if (message != null && message.hasText()) {
//            switch (text) {
//                case "/start":
//                    replyToStart(message);
//                    break;
//                case "/help":
//                    helpHandler(message);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }



//    private void helpHandler(Message message) {
//        String MESSAGE_TEXT = "Доступные команды:\n" +
//                "/addSimpleReminder - добавить простое напоминание\n" +
//                "/addCircleReminder - добавить циклическое напоминание\n" +
//                "/viewActiveReminders - показать активные напоминания\n" +
//                "/viewCircleReminders - показать все циклические напоминания\n" +
//                "/viewClosestReminders - показать ближайшие напоминания\n" +
//                "Настройки:\n" +
//                "/start - начало работы\n" +
//                "/settings - настройки пользователя\n" +
//                "/help - помощь";
//        SendMessage sendMsg = new SendMessage();
//        sendMsg.enableMarkdown(true);
//        sendMsg.setText(MESSAGE_TEXT);
//        sendMsg.setChatId(message.getChatId());
//        try {
//            execute(sendMsg);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void replyToStart(Message message) {
//        String MESSAGE_TEXT = "Привет, %s!\n" +
//                "Ты зашет в бот-напоминайку!\n" +
//                "Очем напомнить?\n" +
//                "/addSimpleReminder - добавить простое напоминание\n" +
//                "/addCircleReminder - добавить циклическое напоминание\n" +
//                "/viewActiveReminders - показать активные напоминания\n" +
//                "/viewCircleReminders - показать все циклические напоминания\n" +
//                "/viewClosestReminders - показать ближайшие напоминания\n" +
//                "Настройки:\n" +
//                "/start - начало работы\n" +
//                "/settings - настройки пользователя\n" +
//                "/help - помощь";
//        String username = message.getChat().getUserName();
//        SendMessage sendMsg = new SendMessage();
//        sendMsg.enableMarkdown(true);
//        sendMsg.setText(String.format(MESSAGE_TEXT, username == null ? "безымянный пользователь" : username));
//        sendMsg.setChatId(message.getChatId());
////        setButtons(sendMsg);
////        sendMsg.setReplyToMessageId(message.getMessageId());
//        try {
//            execute(sendMsg);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void setButtons(SendMessage sendMessage) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//        KeyboardRow keyboardRow1 = new KeyboardRow();
//        keyboardRow1.add(new KeyboardButton("/start"));
////        keyboardRow1.add(new KeyboardButton("Пока!"));
//
//        keyboardRowList.add(keyboardRow1);
//        replyKeyboardMarkup.setKeyboard(keyboardRowList);
//    }

//    @Override
//    public String getBotUsername() {
//        return "ReminderVxBot";
//    }
//
//    @Override
//    public String getBotToken() {
//        return System.getenv("TELEGRAM_TOKEN");
//    }


}
