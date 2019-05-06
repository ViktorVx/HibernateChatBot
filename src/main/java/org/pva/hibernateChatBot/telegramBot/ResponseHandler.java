package org.pva.hibernateChatBot.telegramBot;

import org.pva.hibernateChatBot.constants.ConstantStorage;
import org.pva.hibernateChatBot.person.Person;
import org.pva.hibernateChatBot.person.PersonDao;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
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
                        "/info - информация о пользователе\n" +
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

    //*** Reply to buttons *********************************************************************************************

    public void replyToButtons(long chatId, User user, String buttonId, Update upd, Person person) throws TelegramApiException {
        switch (buttonId) {
            case "edit_person_data":
                replyToEditPersonalData(chatId, user, upd, person);
                break;
            case "edit_register_name":
                System.out.println("register");
                break;
            case "edit_last_name":
                replyToEditLastName(chatId, person);
                break;
        }
    }

    public void replyToMsg(long chatId, Update upd, Person person, PersonDao personDao) throws TelegramApiException {
        Message message = upd.getMessage();
        Message reply = upd.getMessage().getReplyToMessage();
        String msg = "";
        if (reply != null && reply.hasText()) {
            msg = reply.getText();
        }

        switch (msg) {
            case ConstantStorage.EDIT_PERSON_LAST_NAME_MESSAGE:
                person.setLastName(message.getText());
                personDao.update(person);
                replyToEditPersonalData(chatId, upd.getMessage().getFrom(), upd, person);
                break;
        }
    }

    //*****************************************
    public void replyToEditLastName(long chatId, Person person) {
        String MESSAGE =
                "Введите фамилию:";
        try {
            sender.execute(new SendMessage()
                    .setText(MESSAGE)
                    .setChatId(chatId).setReplyMarkup(KeyboardFactory.getForceReplyKeyboard()));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    //********************************************

    public void replyToEditPersonalData(long chatId, User user, Update upd, Person person) throws TelegramApiException {
        StringBuilder msg = new StringBuilder();
        msg.append("ℹ️Персональная информация:\n");
        msg.append(String.format("Фамилия: %s\n", person.getLastName() == null ? "-" : person.getLastName()));
        msg.append(String.format("Имя: %s\n", person.getFirstName() == null ? "-" : person.getFirstName()));
        msg.append(String.format("Отчество: %s\n", person.getMiddleName() == null ? "-" : person.getMiddleName()));

        if (upd.hasCallbackQuery()) {
            long message_id = upd.getCallbackQuery().getMessage().getMessageId();
            long chat_id = upd.getCallbackQuery().getMessage().getChatId();
            String inline_message_id = upd.getCallbackQuery().getInlineMessageId();

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
        if (upd.hasMessage()) {
            long message_id = upd.getMessage().getMessageId();
            long chat_id = upd.getMessage().getChatId();
//            String inline_message_id = upd.getMessage().getInlineMessageId();



            SendMessage new_message = new SendMessage().
                    setChatId(chat_id).
//                    setMessageId(toIntExact(message_id)).
//                    setInlineMessageId(inline_message_id).
                    setText(msg.toString());
            new_message.setReplyMarkup(KeyboardFactory.getInfoPersonEditKeyboard());
            try {
                sender.execute(new_message);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
}
