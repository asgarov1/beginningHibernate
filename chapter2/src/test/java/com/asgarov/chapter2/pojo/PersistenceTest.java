package com.asgarov.chapter2.pojo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class PersistenceTest {

    SessionFactory factory;

    @BeforeSuite
    public void setup() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        factory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    @Test
    public void saveMessage() {
        Message message = new Message("Hello, World");
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(message);
            transaction.commit();
        }
    }

    @Test(dependsOnMethods = "saveMessage")
    public void readMessage() {
        try (Session session = factory.openSession()) {
            List<Message> list = session.createQuery("from Message", Message.class).list();
            assertEquals(list.size(), 1);
            list.forEach(System.out::println);
        }
    }

}