package org.pva.hibernateChatBot.telegramBot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;

public class Bot extends AbilityBot {

    public Bot(String botToken, String botUsername) {
        super(botToken, botUsername);
    }

    @Override
    public int creatorId() {
        return 127155577;
    }

    public Ability sayHelloWorld() {
        return Ability
                .builder()
                .name("hello")
                .info("says hello world!")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send("Hello world!!!!!!!!!!!!!", ctx.chatId()))
                .build();
    }

//    @Override
//    public void onUpdateReceived(Update update) {
//        Message message = update.getMessage();
//        String text = message.getText();
//        if (message != null && message.hasText()) {
//            switch (text) {
//                case "/start":
//                    startHandler(message);
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
//    private void startHandler(Message message) {
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
