package org.pva.hibernateChatBot.telegramBot;

import com.vdurmont.emoji.EmojiParser;
import org.pva.hibernateChatBot.constants.ConstantStorage;
import org.pva.hibernateChatBot.person.Person;
import org.pva.hibernateChatBot.reminder.simpleReminder.SimpleReminder;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

}
