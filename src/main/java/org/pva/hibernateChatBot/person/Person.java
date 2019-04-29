package org.pva.hibernateChatBot.person;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Person {

    @Id
    @GeneratedValue
    Long id;

    @Column
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column
    String middleName;

    @Column
    Date birthDate;

    @Column
    Gender gender;

    @ElementCollection
    List<String> phones = new ArrayList<String>();


    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
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

    @Enumerated(EnumType.ORDINAL)
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
}
