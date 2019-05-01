package org.pva.hibernateChatBot.person;

public class PersonService {

    public static String getFullName(Person person) {
        if (person == null) return "";
        StringBuilder stringBuilder = new StringBuilder();
        if (person.getLastName() != null) stringBuilder.append(person.getLastName()).append(" ");
        if (person.getFirstName() != null) stringBuilder.append(person.getFirstName()).append(" ");
        if (person.getMiddleName() != null) stringBuilder.append(person.getMiddleName());
        return stringBuilder.toString().trim();
    }

}
