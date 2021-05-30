package com.asgarov.chapter4.domain;

import com.asgarov.chapter4.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class EmailTest {
    @Test
    public void testProperInversionCode() {
        Long emailId;
        Long messageId;
        Email email;
        Message message;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            email = new Email("Proper");
            message = new Message("Proper");

            email.setMessage(message);
            message.setEmail(email);

            session.save(email);
            session.save(message);

            emailId = email.getId();
            messageId = message.getId();

            tx.commit();
        }
        assertNotNull(email.getMessage());
        assertNotNull(message.getEmail());

        try (Session session = SessionUtil.getSession()) {
            email = session.get(Email.class, emailId);
            System.out.println(email);
            message = session.get(Message.class, messageId);
            System.out.println(message);
        }
        assertNotNull(email.getMessage());
        assertNotNull(message.getEmail());
    }

    @Test
    public void testCascadeSave() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx=session.beginTransaction();
            Email email = new Email("Email title");
            Message message = new Message("Message content");
            email.setMessage(message);
            message.setEmail(email);
            session.save(email);
            tx.commit();
        }
    }
}