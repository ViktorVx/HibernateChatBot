package org.pva.hibernateChatBot.telegramBot.views;

import com.vdurmont.emoji.EmojiParser;
import org.pva.hibernateChatBot.entity.person.Person;
import org.pva.hibernateChatBot.entity.reminder.Reminder;
import org.pva.hibernateChatBot.entity.reminder.simpleReminder.SimpleReminder;
import org.pva.hibernateChatBot.telegramBot.constants.ConstantStorage;
import org.pva.hibernateChatBot.telegramBot.keyboards.KeyboardFactory;
import org.pva.hibernateChatBot.telegramBot.utils.BotUtils;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EditReminderView {

    public static SimpleReminder getSimpleReminderFromMessage(String personId, List<SimpleReminder> simpleReminderList, String msg) {
        SimpleReminder simpleReminder = null;
        Long remId = BotUtils.getReminderIdFromText(msg);
        for (SimpleReminder reminder : simpleReminderList) {
            if (reminder.getId().equals(remId)) {
                simpleReminder = reminder;
                break;
            }
        }
        return simpleReminder;
    }

    public static void successCompleteReminder(long chatId, SimpleReminder simpleReminder, MessageSender sender) {
        try {
            sender.execute(new SendMessage().
                    setChatId(chatId).
                    setText(EmojiParser.parseToUnicode(":white_check_mark: Напоминание \"".concat(simpleReminder.getText()).
                            concat("\" успешно завершено! Просмотреть список напоминаний - /viewreminders"))));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void successDeleteReminder(long chatId, SimpleReminder simpleReminder, MessageSender sender) {
        try {
            sender.execute(new SendMessage().
                    setChatId(chatId).
                    setText(EmojiParser.parseToUnicode(":x: Напоминание \"".concat(simpleReminder.getText()).
                            concat("\" успешно удалено! Просмотреть список напоминаний - /viewreminders"))));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void viewRemindersList(Person person, Update upd, List<SimpleReminder> reminderList, MessageSender sender) {
        String message = EmojiParser.parseToUnicode(":calendar: Список напоминаний (/addsimplereminder):\n");
        Collections.sort(reminderList, Comparator.comparing(o -> ((SimpleReminder) o).getRemindDate()));
        for (Reminder reminder : reminderList) {
            SimpleReminder simpleReminder = (SimpleReminder) reminder;
            message = message.concat(String.format("/".concat(ConstantStorage.PREFIX_REMINDERS_LIST).concat("%d %s %s - %s\n"),
                    simpleReminder.getId(),
                    new SimpleDateFormat(ConstantStorage.FORMAT_DATE).format(simpleReminder.getRemindDate()),
                    new SimpleDateFormat(ConstantStorage.FORMAT_TIME).format(simpleReminder.getRemindDate()),
                    simpleReminder.getText()));
        }
        try {
            sender.execute(new EditMessageText()
                    .setText(message)
                    .setChatId(upd.getCallbackQuery().getMessage().getChatId())
                    .setReplyMarkup(null)
                    .setMessageId(upd.getCallbackQuery().getMessage().getMessageId())
                    .setInlineMessageId(upd.getCallbackQuery().getInlineMessageId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void viewSelectReminder(SimpleReminder simpleReminder, Update upd, MessageSender sender) {
        long chatId;
        if (upd.hasMessage()) {
            chatId = upd.getMessage().getChatId();
        } else {
            chatId = upd.getCallbackQuery().getMessage().getChatId();
        }
        try {
            if (simpleReminder == null) return;
            sender.execute(new SendMessage().setChatId(chatId).
                    setText(EmojiParser.parseToUnicode(
                            String.format(":memo: Редактировать напоминание (%s):\n" +
                                            "Текст: %s\n" +
                                            "Дата: %s\n" +
                                            "Время: %s\n",
                                    "/rem".concat(String.valueOf(simpleReminder.getId())),
                                    simpleReminder.getText(),
                                    new SimpleDateFormat(ConstantStorage.FORMAT_DATE).format(simpleReminder.getRemindDate()),
                                    new SimpleDateFormat(ConstantStorage.FORMAT_TIME).format(simpleReminder.getRemindDate())))).
                    setReplyMarkup(KeyboardFactory.getEditReminderKeyboard()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void editNewReminderText(long chatId, MessageSender sender) {
        String MESSAGE = ConstantStorage.MSG_EDIT_NEW_REMINDER_TEXT;
        try {
            sender.execute(new SendMessage()
                    .setText(MESSAGE)
                    .setChatId(chatId).setReplyMarkup(KeyboardFactory.getForceReplyKeyboard()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void editNewReminderDate(long chatId, MessageSender sender) {
        String MESSAGE = ConstantStorage.MSG_EDIT_NEW_REMINDER_DATE;
        try {
            sender.execute(new SendMessage()
                    .setText(MESSAGE)
                    .setChatId(chatId).setReplyMarkup(KeyboardFactory.getForceReplyKeyboard()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void editNewReminderTime(long chatId, MessageSender sender) {
        String MESSAGE = ConstantStorage.MSG_EDIT_NEW_REMINDER_TIME;
        try {
            sender.execute(new SendMessage()
                    .setText(MESSAGE)
                    .setChatId(chatId).setReplyMarkup(KeyboardFactory.getForceReplyKeyboard()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
