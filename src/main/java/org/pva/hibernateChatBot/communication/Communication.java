package org.pva.hibernateChatBot.communication;

import org.pva.hibernateChatBot.enums.Gender;
import org.pva.hibernateChatBot.person.Person;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface Communication {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    void welcome();

    Person authentication(String login, String passwordHash);

    boolean isRegistresUser();

    void messageLoginSuccess(String login);

    void messageLoginFail();

    void checkOnExit(String input);

    void checkOnExit(char[] input);

    Person userRegistration() throws NoSuchAlgorithmException;

    Person userAuthentication() throws NoSuchAlgorithmException;

    void messageUserRegistrationSuccess();

    boolean isLoginInDatabase(String login);

    static String getHashFromArray(char[] password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] bytes = new byte[password.length];
        for (int i = 0; i != bytes.length; i++) {
            bytes[i] = (byte) password[i];
        }
        byte[] hash = digest.digest(bytes);

        return String.format("%064x", new BigInteger(1, hash));
    }

    static Gender getGenderFromString(String s) {
        if (s == null) return Gender.UNKNOWN;
        Gender gender;
        switch (s) {
            case "m":
                gender = Gender.MALE;
                break;
            case "м":
                gender = Gender.MALE;
                break;
            case "f":
                gender = Gender.FEMALE;
                break;
            case "ж":
                gender = Gender.FEMALE;
                break;
            default:
                gender = Gender.UNKNOWN;
        }
        return gender;
    }

    static Date getDateFromString(String s) throws ParseException {
        Date date = null;
        date = simpleDateFormat.parse(s);
        return date;
    }

}
