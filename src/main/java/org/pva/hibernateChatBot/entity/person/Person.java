package org.pva.hibernateChatBot.entity.person;

import org.pva.hibernateChatBot.entity.enums.Gender;
import org.pva.hibernateChatBot.entity.reminder.Reminder;
import org.pva.hibernateChatBot.entity.reminder.simpleReminder.SimpleReminder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(indexes = {@Index(columnList = "userid", name = "user_id_hidx")})
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String middleName;

    @Column
    private Date birthDate;

    @Column(unique = true, nullable = false)
    private Long userId;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reminder> reminderList = new ArrayList<Reminder>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Reminder> getReminderList() {
        return reminderList;
    }

    public void setReminderList(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Reminder> getActiveRimindersList() {
        return getReminderList().stream().
                filter(rmd -> rmd.getComplete()==null ? true : !rmd.getComplete()).collect(Collectors.toList());
    }

    public SimpleReminder getSimpleReminderById(Long id) {
        List<Reminder> reminderList = getActiveRimindersList();
        for (Reminder reminder : reminderList) {
            if (reminder.getId().equals(id)) return (SimpleReminder) reminder;
        }
        return null;
    }


}
