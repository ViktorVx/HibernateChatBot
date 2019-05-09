package org.pva.hibernateChatBot.telegramBot;

import com.vdurmont.emoji.EmojiParser;
import org.pva.hibernateChatBot.constants.ConstantStorage;
import org.pva.hibernateChatBot.person.Person;
import org.pva.hibernateChatBot.person.PersonDao;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Map;

import static java.lang.Math.toIntExact;

public class ResponseHandler {

    private final MessageSender sender;
    private final Map<String, Integer> countMap;

    public ResponseHandler(MessageSender sender, DBContext db) {
        this.sender = sender;
        countMap = db.getMap("COUNTERS");
    }

    //*** REPLYES TO BUTTONS AND MESSAGES ******************************************************************************

    public void replyToButtons(long chatId, String buttonId, Update upd, Person person) throws TelegramApiException {
        switch (buttonId) {
            case ConstantStorage.CBD_EDIT_PERSON_DATA:
                replyToEditPersonalData(upd, person);
                break;
            case ConstantStorage.CBD_EDIT_REGISTER_DATA:
                replyToEditRegisterData(upd, person);
                break;
            case ConstantStorage.EDIT_PERSON_LAST_NAME_MESSAGE:
                replyToEditLastName(chatId);
                break;
            case ConstantStorage.EDIT_PERSON_FIRST_NAME_MESSAGE:
                replyToEditFirstName(chatId);
                break;
            case ConstantStorage.EDIT_PERSON_MIDDLE_NAME_MESSAGE:
                replyToEditMiddleName(chatId);
                break;
            case ConstantStorage.CBD_EDIT_PERSONAL_DATA_BACK_BUTTON:
                replyToEditInfoBackButton(upd);
                break;
        }
    }

    public void replyToMsg(Update upd, Person person, PersonDao personDao) throws TelegramApiException {
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
                replyToEditPersonalData(upd, person);
                break;
            case ConstantStorage.EDIT_PERSON_FIRST_NAME_MESSAGE:
                person.setFirstName(message.getText());
                personDao.update(person);
                replyToEditPersonalData(upd, person);
                break;
            case ConstantStorage.EDIT_PERSON_MIDDLE_NAME_MESSAGE:
                person.setMiddleName(message.getText());
                personDao.update(person);
                replyToEditPersonalData(upd, person);
                break;
        }

    }

    //******************************************************************************************************************
    public void replyToEditInfoBackButton(Update upd) {
        if (upd.hasCallbackQuery()) {
            String MESSAGE = String.format("%s, заполните данные о себе!\n", upd.getCallbackQuery().getFrom().getUserName());
            long message_id = upd.getCallbackQuery().getMessage().getMessageId();
            long chat_id = upd.getCallbackQuery().getMessage().getChatId();
            String inline_message_id = upd.getCallbackQuery().getInlineMessageId();

            EditMessageText new_message = new EditMessageText().
                    setChatId(chat_id).
                    setMessageId(toIntExact(message_id)).
                    setInlineMessageId(inline_message_id).
                    setText(MESSAGE);
            new_message.setReplyMarkup(KeyboardFactory.getInfoEditKeyboard());
            try {
                sender.execute(new_message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void replyToEditLastName(long chatId) {
        String MESSAGE =
                ConstantStorage.EDIT_PERSON_LAST_NAME_MESSAGE;
        try {
            sender.execute(new SendMessage()
                    .setText(MESSAGE)
                    .setChatId(chatId).setReplyMarkup(KeyboardFactory.getForceReplyKeyboard()));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToEditFirstName(long chatId) {
        String MESSAGE =
                ConstantStorage.EDIT_PERSON_FIRST_NAME_MESSAGE;
        try {
            sender.execute(new SendMessage()
                    .setText(MESSAGE)
                    .setChatId(chatId).setReplyMarkup(KeyboardFactory.getForceReplyKeyboard()));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToEditMiddleName(long chatId) {
        String MESSAGE =
                ConstantStorage.EDIT_PERSON_MIDDLE_NAME_MESSAGE;
        try {
            sender.execute(new SendMessage()
                    .setText(MESSAGE)
                    .setChatId(chatId).setReplyMarkup(KeyboardFactory.getForceReplyKeyboard()));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //********************************************

    public void replyToEditPersonalData(Update upd, Person person) throws TelegramApiException {
        StringBuilder msg = new StringBuilder();
        msg.append(EmojiParser.parseToUnicode(":information_source: Персональная информация:\n"));
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
            long chat_id = upd.getMessage().getChatId();
            SendMessage new_message = new SendMessage().
                    setChatId(chat_id).
                    setText(msg.toString());
            new_message.setReplyMarkup(KeyboardFactory.getInfoPersonEditKeyboard());
            try {
                sender.execute(new_message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void replyToEditRegisterData(Update upd, Person person) throws TelegramApiException {
        StringBuilder msg = new StringBuilder();
        msg.append(EmojiParser.parseToUnicode(":floppy_disk: Регистрационные данные:\n"));
        msg.append(String.format("Email: %s\n", person.getEmail() == null ? "-" : person.getEmail()));
        msg.append(String.format("Дата рождения: %s\n", person.getBirthDate() == null ? "-" : new SimpleDateFormat("dd.MM.yyyy").format(person.getBirthDate())));

        String gender = "-";
        if (person.getGender() != null) {
            switch (person.getGender()) {
                case MALE:
                    gender = "Мужской";
                    break;
                case FEMALE:
                    gender = "Женский";
                    break;
                default:
                    gender = "Экзотический";
            }
        }
        msg.append(String.format("Пол: %s\n", gender));

        EditMessageText new_message = new EditMessageText();
        if (upd.hasCallbackQuery()) {
            long message_id = upd.getCallbackQuery().getMessage().getMessageId();
            long chat_id = upd.getCallbackQuery().getMessage().getChatId();
            String inline_message_id = upd.getCallbackQuery().getInlineMessageId();

            new_message.setChatId(chat_id).
                    setMessageId(toIntExact(message_id)).
                    setInlineMessageId(inline_message_id).
                    setText(msg.toString());
        }
        if (upd.hasMessage()) {
            long chat_id = upd.getMessage().getChatId();
            new_message.setChatId(chat_id).setText(msg.toString());
        }

        new_message.setReplyMarkup(KeyboardFactory.getRegisterDataEditKeyboard());
        try {
            sender.execute(new_message);
        } catch (Exception e) {
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
}
