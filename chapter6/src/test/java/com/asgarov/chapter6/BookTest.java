package com.asgarov.chapter6;

import com.asgarov.chapter6.domain.embedded.Address;
import com.asgarov.chapter6.domain.embedded.Author;
import com.asgarov.chapter6.util.SessionUtil;
import org.hibernate.Session;
import org.testng.annotations.Test;

public class BookTest {
    @Test
    public void example() {
        try (Session session = SessionUtil.getSession()) {
            session.getTransaction().begin();
            session.save(new Book("title", 255));
            session.getTransaction().commit();
        }
    }

    @Test
    public void author_address_street() {
        Author author;
        try (Session session = SessionUtil.getSession()) {
            session.getTransaction().begin();
            author= new Author(new Address("Hauptstrasse"));
            session.save(author);
            session.getTransaction().commit();
        }

        try (Session session = SessionUtil.getSession()) {
            session.getTransaction().begin();
            Author author2 = session.get(Author.class, author.getId());
            session.getTransaction().commit();
        }
    }
}