package org.pva.hibernateChatBot.telegramBot;

import org.junit.Test;

import static org.junit.Assert.*;

public class BotTest {

    @Test
    public void getBotUsername() {
        Bot bot = new Bot();
        assertEquals("ReminderVxBot", bot.getBotUsername());
    }

    @Test
    public void getBotToken() {
        
    }
}