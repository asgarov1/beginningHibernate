package com.asgarov.chapter3.dao;

import com.asgarov.chapter3.domain.Skill;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class GenericDao<T> {

    private final Class<T> clazz;

    public GenericDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findByName(Session session, String name) {
        Query<T> query = session.createQuery("from " + clazz.getSimpleName() + " s where s.name=:name", clazz);
        query.setParameter("name", name);
        return query.uniqueResult();
    }
}
