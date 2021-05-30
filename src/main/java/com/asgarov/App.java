package com.asgarov;

import com.asgarov.connection.ConnectionFactory;
import org.hibernate.SessionFactory;


/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        SessionFactory sessionFactory = ConnectionFactory.getSessionFactory();
    }
}
