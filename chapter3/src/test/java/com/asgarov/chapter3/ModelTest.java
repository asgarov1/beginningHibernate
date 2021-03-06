package com.asgarov.chapter3;

import com.asgarov.chapter3.domain.Person;
import com.asgarov.chapter3.domain.Ranking;
import com.asgarov.chapter3.domain.Skill;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ModelTest {
    @Test
    public void testModelCreation() {
        Person subject = new Person();
        subject.setName("J.C. Smell");

        Person observer=new Person();
        observer.setName("Drew Lombardo");

        Skill skill=new Skill();
        skill.setName("Java");

        Ranking ranking=new Ranking();
        ranking.setSubject(subject);
        ranking.setObserver(observer);
        ranking.setSkill(skill);
        ranking.setRanking(8);
        // just to give us visual verification
        System.out.println(ranking);
    }
}