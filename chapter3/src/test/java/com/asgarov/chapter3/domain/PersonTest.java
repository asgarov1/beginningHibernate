package com.asgarov.chapter3.domain;

import com.asgarov.chapter3.dao.PersonDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

public class PersonTest extends BaseTest{

    private final PersonDao personDao = new PersonDao();

    @Test
    public void testSavePerson() {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Person person = new Person();
            person.setName("J.C. Smell");

            session.save(person);

            transaction.commit();
        }
    }

    @Test(dependsOnMethods = "testSavePerson")
    public void testFindPerson() {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Person person = personDao.findByName(session, "J.C. Smell");
            System.out.println(person);

            transaction.commit();
        }
    }

}