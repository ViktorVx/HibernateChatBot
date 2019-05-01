package org.pva.hibernateChatBot.person;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pva.hibernateChatBot.dao.Dao;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class PersonDao implements Dao<Person> {

    private SessionFactory sessionFactory;
    private EntityManager entityManager;

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
    public List<Person> getAll() {
//        EntityManager entityManager = sessionFactory.createEntityManager();
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Person> criteria = builder.createQuery(Person.class);
//        Root<Person> root = criteria.from(Person.class);
//        criteria.select(root);
//        criteria.where(builder.equal(root.get(Person_.firstName), "John Doe"));
//        List<Person> persons = entityManager.createQuery( criteria ).getResultList();


//        List<Person> persons = entityManager.createQuery(
//                "select p " +
//                        "from Person p " +
//                        "where p.name like :name" )
//                .setParameter( "name", "J%" )
//                .getResultList();

        List<Person> persons = entityManager.createQuery("select p from Person p").getResultList();
        return persons;
    }

    @Override
    public void save(Person person) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(person);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Person person) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(person);
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
