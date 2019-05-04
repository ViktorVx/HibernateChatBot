package org.pva.hibernateChatBot.telegramBot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.objects.Flag.MESSAGE;
import static org.telegram.abilitybots.api.objects.Flag.REPLY;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class Bot extends AbilityBot {

    private static final String BOT_TOKEN = System.getenv("TELEGRAM_TOKEN");
    private static final String BOT_USERNAME = "ReminderVxBot";
    private static final Integer BOT_CREATOR_ID = 127155577;
    private final ResponseHandler responseHandler;

    public Bot() {
        super(BOT_TOKEN, BOT_USERNAME);
        responseHandler = new ResponseHandler(sender, db);
    }

    @Override
    public int creatorId() {
        return BOT_CREATOR_ID;
    }

    public Ability replyToEnterLogin() {
        String msg = "Введите логин:";
        return Ability.
                builder().
                name("enterlogin").
                info("введите логин").
                privacy(PUBLIC).
                locality(ALL).
                input(0).
                action(ctx -> {
                    try {
                        sender.execute(new SendMessage()
                                .setText(msg).setChatId(ctx.chatId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }).
                reply(upd -> {
                            System.out.println("I'm in a reply!");
                            try {
                                sender.execute(new SendMessage()
                                        .setText(upd.getMessage().getText()).setChatId(upd.getMessage().getChatId()));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        },
                        //MESSAGE,
                        REPLY/*,
                        isReplyToBot(),
                        isReplyToMessage(msg)*/).
                build();
    }

    private Predicate<Update> isReplyToMessage(String message) {
        return upd -> {
            Message reply = upd.getMessage().getReplyToMessage();
            System.out.println(reply.getReplyToMessage());
            return reply.hasText() && reply.getText().equalsIgnoreCase(message);
        };
    }

    private Predicate<Update> isReplyToBot() {
        return upd -> upd.getMessage().getReplyToMessage().getFrom().getUserName().equalsIgnoreCase(getBotUsername());
    }

    public Ability replyToStart() {
        return Ability
                .builder()
                .name("start")
                .info("Starts the bot!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx ->  responseHandler.replyToStart(ctx.chatId()))
                .build();
    }

    public Ability useDatabaseToCountPerUser() {
        return Ability.builder()
                .name("count")
                .info("Increments a counter per user")
                .locality(ALL)
                .privacy(PUBLIC)
                .input(0)
                .action(ctx -> {
                    responseHandler.replyToCount(ctx.chatId());
                })
                .build();
    }

    public Reply replyToButtons() {
        Consumer<Update> action = upd -> responseHandler.replyToButtons(getChatId(upd), upd.getCallbackQuery().getData());
        return Reply.of(action, Flag.CALLBACK_QUERY);
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
