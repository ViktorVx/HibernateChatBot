package org.pva.hibernateChatBot.telegramBot;

import org.pva.hibernateChatBot.reminder.SimpleReminder;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

public class ResponseHandler {

    private final MessageSender sender;
    private final Map<String, Integer> countMap;

    public ResponseHandler(MessageSender sender, DBContext db) {
        this.sender = sender;
        countMap = db.getMap("COUNTERS");
    }

    @Deprecated
    public void replyToEnterLogin(long chatId) {
        try {
            sender.execute(new SendMessage()
                    .setText("Введите Ваш уникальный логин:")
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.getStartCountKeyboard()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToEnterReminderDate(long chatId, SimpleReminder simpleReminder) {
        try {
            sender.execute(new SendMessage()
                    .setText("Когда нужно напомнить (дата в формате дд.ММ.гггг)?")
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.getForceReplyKeyboard()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Deprecated
    public void replyToCount(long chatId) {
        try {
            Integer counter = countMap.compute(String.valueOf(sender.getMe().getId()), (id, count) -> count == null ? 1 : ++count);
            String message = String.format("Your count is now *%d*!", counter);

            sender.execute(new SendMessage()
                    .setText(message)
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.getStartCountKeyboard()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToStart(long chatId, User user) throws TelegramApiException {
        String MESSAGE = String.format("Привет, %s!\n" +
                "Ты зашел в бот-напоминайку!\n" +
                "О чем напомнить?\n" +
                "/addsimplereminder - добавить простое напоминание\n" +
                "/viewreminders - показать ближайшие напоминания\n" +
                "Настройки:\n" +
                "/start - начало работы\n" +
                "/help - помощь", user.getUserName());
        try {
            sender.execute(new SendMessage()
                    .setText(MESSAGE)
                    .setChatId(chatId));
//                    .setReplyMarkup(KeyboardFactory.getStartCountKeyboard()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToHelp(long chatId, User user) throws TelegramApiException {
        String MESSAGE =
                "Доступны следующие команды:\n" +
                        "/start - начало работы\n" +
                        "/help - помощь\n" +
                        "/addsimplereminder - добавить простое напоминание\n" +
                        "/viewreminders - показать напоминания";
        try {
            sender.execute(new SendMessage()
                    .setText(MESSAGE)
                    .setChatId(chatId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToButtons(long chatId, User user, String buttonId) throws TelegramApiException {
        switch (buttonId) {
            case "start":
                replyToStart(chatId, user);
                break;
            case "count":
                replyToCount(chatId);
                break;
        }
    }
}
