package org.pva.hibernateChatBot.person;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pva.hibernateChatBot.dao.Dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class PersonDao implements Dao<Person> {

    private SessionFactory sessionFactory;
    private EntityManager entityManager;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.entityManager = sessionFactory.createEntityManager();
    }

    public PersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.entityManager = sessionFactory.createEntityManager();
    }

    @Override
    public Optional<Person> get(long id) {
//        Person person = (Person) (entityManager.createQuery("select p from Person p where p.id=:id").setParameter("id", id).);
//        Session session = sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        Person person = session.load(Person.class, id);
//        tx.commit();
//        session.close();
        Person person = entityManager.find(Person.class, id);
        return Optional.ofNullable(person);
    }

    @Override
    public Person findByLogin(String login) {
        Query query = entityManager.createQuery("select p from Person p where p.login=:login").setParameter("login", login);
        Person person;
        try {
            person = (Person) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return person;
    }

    @Override
    public Person findByUserId(Long userId) {
        Query query = entityManager.createQuery("select p from Person p where p.userId=:userId").setParameter("userId", userId);
        Person person;
        try {
            person = (Person) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return person;
    }

    @Override
    public List<Person> getAll() {
        List<Person> persons = entityManager.createQuery("select p from Person p").getResultList();
        return persons;
    }

    @Override
    public void save(Person person) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(person);
//        session.saveOrUpdate(person);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Person person) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
//        session.update(person);
        session.merge(person);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Person person) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(person);
        tx.commit();
        session.close();
    }
}
