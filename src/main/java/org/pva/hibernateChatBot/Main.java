package org.pva.hibernateChatBot;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.pva.hibernateChatBot.person.Person;
import org.pva.hibernateChatBot.person.PersonDao;
import org.pva.hibernateChatBot.person.PersonService;
import org.pva.hibernateChatBot.telegramBot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.List;

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

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {


        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        while (true) {

        }
    }

    private static void getFromDatabase() {
        List<Person> personList = personDao.getAll();
        Person singlePerson = personDao.get(2).orElse(null);
        for (Person person : personList) {
            System.out.println(PersonService.getFullName(person));
        }
        System.out.println("**************************************");
        System.out.println(PersonService.getFullName(singlePerson));
    }
}