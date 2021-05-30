package com.asgarov.chapter3.dao;

import com.asgarov.chapter3.domain.Person;
import com.asgarov.chapter3.domain.Skill;
import org.hibernate.Session;

public class SkillDao extends GenericDao<Skill> {

    public SkillDao() {
        super(Skill.class);
    }

    public Skill saveSkill(Session session, String name) {
        Skill skill = findByName(session, name);
        if (skill == null) {
            skill = new Skill();
            skill.setName(name);
            session.save(skill);
        }
        return skill;
    }
}
