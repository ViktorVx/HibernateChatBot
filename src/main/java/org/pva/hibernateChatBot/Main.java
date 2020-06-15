package org.pva.hibernateChatBot;

import org.pva.hibernateChatBot.telegramBot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {

    private static Integer MAIN_TASK_PERIOD = 60000;

    public static void main(String[] args) {
        Bot bot = new Bot();
        runBot(bot);
        runReminderCreator(bot);
    }

    private static void runBot(Bot bot) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    private static void runReminderCreator(Bot bot) {
        Thread reminderCreator = new Thread(() -> {
            while (true) {
                try {
                    bot.mainScheduledTask(MAIN_TASK_PERIOD);
                    Thread.sleep(MAIN_TASK_PERIOD);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        reminderCreator.start();
    }
}