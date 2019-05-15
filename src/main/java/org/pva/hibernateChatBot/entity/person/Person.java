package org.pva.hibernateChatBot.entity.person;

import org.pva.hibernateChatBot.entity.enums.Gender;
import org.pva.hibernateChatBot.entity.reminder.simpleReminder.SimpleReminder;
import org.pva.hibernateChatBot.telegramBot.utils.BotUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Person implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private Date birthDate;

    private Long userId;

    private Long chatId;

    private Gender gender;

    private String login;

    private String email;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person() {
    }

    public Person(String lastName, String firstName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public SimpleReminder getSimpleReminderById(Long id, List<SimpleReminder> simpleReminders) {
        for (SimpleReminder reminder : BotUtils.getActiveRemindersList(simpleReminders)) {
            if (reminder.getComplete() == null || !reminder.getComplete())
                if (reminder.getId().equals(id)) return reminder;
        }
        return null;
    }


}
