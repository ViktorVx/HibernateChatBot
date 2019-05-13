package org.pva.hibernateChatBot;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.pva.hibernateChatBot.telegramBot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {

    private static final SessionFactory ourSessionFactory;
    private static Integer MAIN_TASK_PERIOD = 3600000;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void main(final String[] args) {
        Bot bot = new Bot(ourSessionFactory);
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
        bot.mainShedulledTask(MAIN_TASK_PERIOD);
        Thread reminderCreator = new Thread(() -> {
            while (true) {
                try {
                    bot.mainShedulledTask(MAIN_TASK_PERIOD);
                    Thread.sleep(MAIN_TASK_PERIOD);
                } catch (InterruptedException ex) {
                }
            }
        });
        reminderCreator.start();
    }


}