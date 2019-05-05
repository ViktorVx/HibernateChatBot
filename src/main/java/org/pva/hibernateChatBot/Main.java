package org.pva.hibernateChatBot;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.pva.hibernateChatBot.person.PersonDao;
import org.pva.hibernateChatBot.telegramBot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {

    private static final SessionFactory ourSessionFactory;
    private static PersonDao personDao;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
        personDao = new PersonDao(ourSessionFactory);
    }

    public static void main(final String[] args) throws Exception {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot(ourSessionFactory));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
        while (true) { }
    }
}