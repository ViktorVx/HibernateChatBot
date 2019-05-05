package org.pva.hibernateChatBot.reminder;

import org.pva.hibernateChatBot.person.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "SimpleReminder")
public class SimpleReminder extends Reminder {

    @Column
    Date remindDate;

    public Date getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }

    public SimpleReminder() {
    }

    public SimpleReminder(Person person, Date creationDate, String text, Date remindDate) {
        super(creationDate, text);
        this.remindDate = remindDate;
    }
}
