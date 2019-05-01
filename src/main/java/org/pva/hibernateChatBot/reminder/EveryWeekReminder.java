package org.pva.hibernateChatBot.reminder;

import org.pva.hibernateChatBot.enums.CirclePeriod;
import org.pva.hibernateChatBot.enums.WeekDay;
import org.pva.hibernateChatBot.person.Person;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "EveryWeekReminder")
public abstract class EveryWeekReminder extends CircleReminder {

    @ElementCollection
    Set<WeekDay> weekDaySet = new HashSet<WeekDay>();

    public EveryWeekReminder(Person person, Date creationDate, String text, CirclePeriod type, Date remindTime, Set<WeekDay> weekDaySet) {
        super(creationDate, text, type, remindTime);
        this.weekDaySet = weekDaySet;
    }

    public EveryWeekReminder() {
    }
}
