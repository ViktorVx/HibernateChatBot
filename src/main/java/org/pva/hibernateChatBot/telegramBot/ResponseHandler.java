package org.pva.hibernateChatBot.telegramBot;

import org.pva.hibernateChatBot.person.Person;
import org.pva.hibernateChatBot.reminder.SimpleReminder;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

import static java.lang.Math.toIntExact;

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
                "/info - информация о пользователе\n" +
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

    public void replyToInfo(long chatId, User user) throws TelegramApiException {
        String MESSAGE = String.format("%s, заполните данные о себе!\n", user.getUserName());
        try {
            sender.execute(new SendMessage()
                    .setText(MESSAGE)
                    .setChatId(chatId).setReplyMarkup(KeyboardFactory.getInfoEditKeyboard()));
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

    public void replyToButtons(long chatId, User user, String buttonId, Update upd, Person person) throws TelegramApiException {
        switch (buttonId) {
            case "start":
                replyToStart(chatId, user);
                break;
            case "count":
                replyToCount(chatId);
                break;
            case "edit_person_data":
                replyToEditPersonalData(chatId, user, upd, person);
                break;
            case "edit_register_name":
                System.out.println("register");
                break;
        }
    }

    public void replyToEditPersonalData(long chatId, User user, Update upd, Person person) throws TelegramApiException {
        if (upd.hasCallbackQuery()) {
            long message_id = upd.getCallbackQuery().getMessage().getMessageId();
            long chat_id = upd.getCallbackQuery().getMessage().getChatId();
            String inline_message_id = upd.getCallbackQuery().getInlineMessageId();

            StringBuilder msg = new StringBuilder();
            msg.append("ℹ️Персональная информация:\n");
            msg.append(String.format("Фамилия: %s\n", person.getLastName()));
            msg.append(String.format("Имя: %s\n", person.getFirstName()));
            msg.append(String.format("Отчество: %s\n", person.getMiddleName()));

            EditMessageText new_message = new EditMessageText().
                    setChatId(chat_id).
                    setMessageId(toIntExact(message_id)).
                    setInlineMessageId(inline_message_id).
                    setText(msg.toString());
            new_message.setReplyMarkup(KeyboardFactory.getInfoPersonEditKeyboard());
            try {
                sender.execute(new_message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
