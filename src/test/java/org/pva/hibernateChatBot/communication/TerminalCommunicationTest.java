package org.pva.hibernateChatBot.communication;

import org.junit.Test;
import org.pva.hibernateChatBot.enums.Gender;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class TerminalCommunicationTest {

    @Test
    public void getHashFromArray() throws NoSuchAlgorithmException {
        char[] chars = {'1', '2', '3'};
        assertEquals("a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", Communication.getHashFromArray(chars));
    }

    @Test
    public void getGenderFromString() {
        assertEquals(Gender.MALE, Communication.getGenderFromString("m"));
        assertEquals(Gender.MALE, Communication.getGenderFromString("м"));
        assertEquals(Gender.FEMALE, Communication.getGenderFromString("f"));
        assertEquals(Gender.FEMALE, Communication.getGenderFromString("ж"));
        assertEquals(Gender.UNKNOWN, Communication.getGenderFromString("o"));
        assertEquals(Gender.UNKNOWN, Communication.getGenderFromString(""));
        assertEquals(Gender.UNKNOWN, Communication.getGenderFromString(null));
        assertEquals(Gender.UNKNOWN, Communication.getGenderFromString("2323dads"));
    }
}