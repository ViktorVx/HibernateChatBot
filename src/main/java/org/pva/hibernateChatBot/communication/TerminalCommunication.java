package org.pva.hibernateChatBot.communication;

import org.pva.hibernateChatBot.enums.Gender;
import org.pva.hibernateChatBot.person.Person;
import org.pva.hibernateChatBot.person.PersonDao;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class TerminalCommunication implements Communication {

    private PersonDao personDao;

    public TerminalCommunication(PersonDao personDao) {
        this.personDao = personDao;
    }

    //*** User Authentication ******************************************************************************************
    @Override
    public Person userAuthentication() throws NoSuchAlgorithmException {
        String login = authenticationEnterLogin();
        String passwordHash = authenticationEnterPassword();
        //todo stop here!!!!!!!!!!!!!!!!


        return null;
    }

    @Override
    public Person authentication(String login, String passwordHash) {
        return null;
    }

    private String authenticationEnterLogin() {
        Scanner scanner = new Scanner(System.in);
        String login;
        System.out.println("Введите Ваш логин:");
        login = scanner.nextLine();
        checkOnExit(login);
        return login;
    }

    private String authenticationEnterPassword() throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        String hashPassword;
        char[] password;
        System.out.println("Введите пароль:");
        password = scanner.nextLine().toCharArray();
        checkOnExit(password);
        hashPassword = Communication.getHashFromArray(password);
        return hashPassword;
    }

    //*** User registration ********************************************************************************************
    @Override
    public Person userRegistration() throws NoSuchAlgorithmException {
        String login = registrationEnterLogin();
        String hashPassword = registrationGetHashPassword();
        String lastName = registartionEnterLastName();
        String firstName = registartionEnterFirstName();
        String middleName = registartionEnterMiddleName();
        Gender gender = registartionEnterGender();
        Date birthDate = registartionEnterBirthDate();

        Person person = new Person();
        person.setLogin(login);
        person.setPassword(hashPassword);
        person.setLastName(lastName);
        person.setFirstName(firstName);
        person.setMiddleName(middleName);
        person.setGender(gender);
        person.setBirthDate(birthDate);

        return person;
    }

    private Date registartionEnterBirthDate() {
        Scanner scanner = new Scanner(System.in);
        String s;
        System.out.println("Введите дату рождения в формате дд.мм.гггг:");
        s = scanner.nextLine();
        checkOnExit(s);
        Date birthDate = null;
        try {
            birthDate = Communication.getDateFromString(s);
        } catch (ParseException e) {
            System.out.println("Дата введена в неправильном формате!");
        }
        return birthDate;

    }

    private Gender registartionEnterGender() {
        Scanner scanner = new Scanner(System.in);
        String s;
        System.out.println("Введите Ваш пол (м/ж/д, m/f/o):");
        s = scanner.nextLine();
        checkOnExit(s);
        return Communication.getGenderFromString(s);
    }

    private String registartionEnterMiddleName() {
        Scanner scanner = new Scanner(System.in);
        String middleName;
        System.out.println("Введите Ваше отчество:");
        middleName = scanner.nextLine();
        checkOnExit(middleName);
        return middleName;
    }

    private String registartionEnterFirstName() {
        Scanner scanner = new Scanner(System.in);
        String firstName;
        System.out.println("Введите Ваше имя:");
        firstName = scanner.nextLine();
        checkOnExit(firstName);
        return firstName;
    }

    private String registartionEnterLastName() {
        Scanner scanner = new Scanner(System.in);
        String lastName;
        while (true) {
            System.out.println("Введите Ваш фамилию:");
            lastName = scanner.nextLine();
            checkOnExit(lastName);
            if (lastName.equals("")) {
                System.out.println("Фамилия не должна быть пустой!");
                continue;
            } else {
                break;
            }
        }
        return lastName;
    }

    private String registrationGetHashPassword() throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        char[] password;
        String hashPassword;
        System.out.println("Введите пароль:");
        password = scanner.nextLine().toCharArray();
        checkOnExit(password);

        hashPassword = Communication.getHashFromArray(password);
        return hashPassword;
    }

    private String registrationEnterLogin() {
        Scanner scanner = new Scanner(System.in);
        String login;
        while (true) {
            System.out.println("Введите Ваш логин:");
            login = scanner.nextLine();
            checkOnExit(login);
            if (isLoginInDatabase(login)) {
                System.out.println("Пользователь с таким логином уже существует!");
                continue;
            } else {
                break;
            }
        }
        return login;
    }

    //*** Agent messages ***********************************************************************************************

    @Override
    public void welcome() {
        System.out.println("Приветсвуем Вас в НАПОМИНАЛКЕ!");
    }

    @Override
    public void messageUserRegistrationSuccess() {
        System.out.println("Пользователь успешно зарегистрирован!");
    }

    //******************************************************************************************************************

    @Override
    public void checkOnExit(String input) {
        if (input.equals("exit")) System.exit(0);
    }

    @Override
    public void checkOnExit(char[] input) {
        if (input.length != 4) return;
        if (input[0] != 'e') return;
        if (input[1] != 'x') return;
        if (input[2] != 'i') return;
        if (input[3] != 't') return;
        System.exit(0);
    }

    @Override
    public boolean isLoginInDatabase(String login) {
        return personDao.findByLogin(login) != null;
    }

    @Override
    public boolean isRegistresUser() {
        System.out.println("Вы уже зарегистрированы в системе?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toLowerCase();
        checkOnExit(input);

        if (input.equals("y") || input.equals("yes") || input.equals("д") || input.equals("да")) return true;
        return false;
    }
}
