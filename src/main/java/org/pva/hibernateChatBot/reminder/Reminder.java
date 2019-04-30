package org.pva.hibernateChatBot.reminder;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Reminder {

    @Id
    @GeneratedValue
    Long id;

}
