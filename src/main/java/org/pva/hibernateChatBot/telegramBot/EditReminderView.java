package org.pva.hibernateChatBot.telegramBot;

import org.pva.hibernateChatBot.constants.ConstantStorage;
import org.pva.hibernateChatBot.person.Person;
import org.pva.hibernateChatBot.reminder.SimpleReminder;

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

}
