package org.pva.hibernateChatBot.telegramBot;

import com.vdurmont.emoji.EmojiParser;
import org.pva.hibernateChatBot.constants.ConstantStorage;
import org.pva.hibernateChatBot.person.Person;
import org.pva.hibernateChatBot.reminder.Reminder;
import org.pva.hibernateChatBot.reminder.simpleReminder.SimpleReminder;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditReminderView {

    public static SimpleReminder getSimpleReminderFromMessage(Person person, String msg) {
        Pattern pattern = Pattern.compile("/".concat(ConstantStorage.PREFIX_REMINDERS_LIST).concat("([0-9]+)"));
        Matcher matcher = pattern.matcher(msg);
        Long remId = null;
        if (matcher.find()) {
            try {
                remId = Long.valueOf(matcher.group(1));
            } catch (Exception e) {
                return null;
            }
        }
        return person.getSimpleReminderById(remId);
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

    public static void viewRemindersList(Person person, Update upd, MessageSender sender) {
        String message = EmojiParser.parseToUnicode(":calendar: Список напоминаний (/addsimplereminder):\n");
        List<Reminder> reminderList = person.getActiveRimindersList();
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
        long chatId = upd.getMessage().getChatId();
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
}
