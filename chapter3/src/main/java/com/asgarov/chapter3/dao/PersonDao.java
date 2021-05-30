package com.asgarov.chapter3.dao;

import com.asgarov.chapter3.domain.Person;
import org.hibernate.Session;

public class PersonDao extends GenericDao<Person> {

    public PersonDao() {
        super(Person.class);
    }

    public Person savePerson(Session session, String name) {
        Person person = findByName(session, name);
        if (person == null) {
            person = new Person();
            person.setName(name);
            session.save(person);
        }
        return person;
    }
}
