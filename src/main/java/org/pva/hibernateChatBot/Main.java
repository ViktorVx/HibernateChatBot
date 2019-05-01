package org.pva.hibernateChatBot;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.pva.hibernateChatBot.enums.Gender;
import org.pva.hibernateChatBot.person.Person;

import java.util.Date;

public class Main {
    private static final SessionFactory ourSessionFactory;
    private static final String EXIT = "exit";
    private static final String START = "start";
    private static final Integer RANDOM_FROM = 1;
    private static final Integer RANDOM_TO = 100000;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
//        final Session session = getSession();
//        try {
//            System.out.println("querying all the managed entities...");
//            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
//            for (EntityType<?> entityType : metamodel.getEntities()) {
//                final String entityName = entityType.getName();
//                final Query query = session.createQuery("from " + entityName);
//                System.out.println("executing: " + query.getQueryString());
//                for (Object o : query.list()) {
//                    System.out.println("  " + o);
//                }
//            }
//        } finally {
//            session.close();
//        }
        while (true) {
//            Scanner scanner = new Scanner(System.in);
//            String command = scanner.nextLine();
//            if (command.equals(EXIT)) break;
//            if (command.equals(START)) {
            fulfillDatabase();
            break;
//            }

//            List<String> fio = Arrays.asList(command.split(" "));
//            Person person = new Person(fio.get(0), fio.get(1), fio.get(2));
//
//            Session session = getSession();
//            Transaction tx = session.beginTransaction();
//            session.persist(person);
//            tx.commit();
//            session.close();
        }
    }

    private static void fulfillDatabase() {
        Person person1 = new Person(getRandom("Иванов"), getRandom("Иван"), getRandom("Иванович"));
        person1.setBirthDate(new Date());
        person1.setGender(Gender.MALE);


        Person person2 = new Person(getRandom("Петров"), getRandom("Петр"), getRandom("Петрович"));
        person2.setBirthDate(new Date());
        person2.setGender(Gender.MALE);

        Person person3 = new Person(getRandom("Соколова"), getRandom("Наталья"), getRandom("Павловна"));
        person3.setBirthDate(new Date());
        person3.setGender(Gender.FEMALE);

        Session session = getSession();
        Transaction tx = session.beginTransaction();
        session.persist(person1);
        session.persist(person2);
        session.persist(person3);
        tx.commit();
        session.close();
    }

    private static String getRandom(String str) {
        return str.concat("(").concat(String.valueOf(RANDOM_FROM + (int) (Math.random() * RANDOM_TO))).concat(")");
    }
}