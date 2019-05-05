package org.pva.hibernateChatBot.reminder;

import org.pva.hibernateChatBot.enums.CirclePeriod;
import org.pva.hibernateChatBot.person.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "EveryYearReminder")
public abstract class EveryYearReminder extends CircleReminder {

    @Column
    Date remindDate;

    public EveryYearReminder(Person person, Date creationDate, String text, CirclePeriod type, Date remindTime, Date remindDate) {
        super(creationDate, text, type, remindTime);
        this.remindDate = remindDate;
    }

    public EveryYearReminder() {
    }
}
