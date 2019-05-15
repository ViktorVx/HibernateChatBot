package org.pva.hibernateChatBot.entity.reminder.simpleReminder;

import org.pva.hibernateChatBot.entity.person.Person;
import org.pva.hibernateChatBot.entity.reminder.Reminder;

import java.util.Date;

public class SimpleReminder extends Reminder {

    Date remindDate;

    public Date getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }

    public SimpleReminder() {
    }

    public SimpleReminder(Date creationDate, String text, Date remindDate) {
        super(creationDate, text);
        this.remindDate = remindDate;
    }
}
