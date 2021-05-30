package com.asgarov.chapter4.domain;

import com.asgarov.chapter4.domain.mapped.Email;
import com.asgarov.chapter4.domain.mapped.Message;
import com.asgarov.chapter4.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MappedEmailTest {
    @Test
    public void testImpliedRelationship() {
        Long emailId;
        Long messageId;
        Email email;
        Message message;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            email = new Email("Inverse Email");
            message = new Message("Inverse Message");
//             email.setMessage(message);
            message.setEmail(email);
            session.save(email);
            session.save(message);
            emailId = email.getId();
            messageId = message.getId();
            tx.commit();
        }
        assertEquals(email.getSubject(), "Inverse Email");
        assertEquals(message.getContent(), "Inverse Message");
        assertNull(email.getMessage());
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
}