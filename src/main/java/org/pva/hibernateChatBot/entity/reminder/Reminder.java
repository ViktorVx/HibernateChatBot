package org.pva.hibernateChatBot.entity.reminder;

import java.io.Serializable;
import java.util.Date;

public abstract class Reminder implements Serializable {

    private Long id;

    private Date creationDate;

    private String text;

    private Boolean complete;

    public Reminder() { }

    public Reminder(Date creationDate, String text) {
        this.creationDate = creationDate;
        this.text = text;
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

    public Boolean isComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
