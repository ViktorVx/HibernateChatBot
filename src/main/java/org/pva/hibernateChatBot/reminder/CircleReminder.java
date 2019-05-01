package org.pva.hibernateChatBot.reminder;

import org.pva.hibernateChatBot.enums.CirclePeriod;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "circleReminder")
public abstract class CircleReminder extends Reminder {

    @Column
    @Enumerated(EnumType.ORDINAL)
    CirclePeriod type;

    @Column
    Date remindTime;

    public CirclePeriod getType() {
        return type;
    }

    public void setType(CirclePeriod type) {
        this.type = type;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    public CircleReminder(Date creationDate, String text, CirclePeriod type, Date remindTime) {
        super(creationDate, text);
        this.type = type;
        this.remindTime = remindTime;
    }

    public CircleReminder() {
    }
}
