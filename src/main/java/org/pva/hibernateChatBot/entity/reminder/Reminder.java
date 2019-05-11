package org.pva.hibernateChatBot.entity.reminder;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "reminder")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    Date creationDate;

    @Column(length = 6000)
    String text;

    @Column
    Boolean complete;

    public Reminder(Date creationDate, String text) {
        this.creationDate = creationDate;
        this.text = text;
    }

    public Reminder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
