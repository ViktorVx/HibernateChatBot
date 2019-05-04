package org.pva.hibernateChatBot.telegramBot;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

public class ResponseHandler {

    private final MessageSender sender;
    private final Map<String, Integer> countMap;

    public ResponseHandler(MessageSender sender, DBContext db) {
        this.sender = sender;
        countMap = db.getMap("COUNTERS");
    }

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

    public void replyToStart(long chatId) {
        String MESSAGE = "Привет, username!\n" +
                "Ты зашет в бот-напоминайку!\n" +
                "О чем напомнить?\n" +
                "/enterlogin - ввести логин \n" +
                "/addSimpleReminder - добавить простое напоминание\n" +
                "/addCircleReminder - добавить циклическое напоминание\n" +
                "/viewActiveReminders - показать активные напоминания\n" +
                "/viewCircleReminders - показать все циклические напоминания\n" +
                "/viewClosestReminders - показать ближайшие напоминания\n" +
                "Настройки:\n" +
                "/start - начало работы\n" +
                "/settings - настройки пользователя\n" +
                "/help - помощь";
        try {
            sender.execute(new SendMessage()
                    .setText(MESSAGE)
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.getStartCountKeyboard()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToButtons(long chatId, String buttonId) {
        switch (buttonId) {
            case "start":
                replyToStart(chatId);
                break;
            case "count":
                replyToCount(chatId);
                break;
        }
    }
}
