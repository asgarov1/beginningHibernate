package com.asgarov.chapter3.service;

import com.asgarov.chapter3.dao.PersonDao;
import com.asgarov.chapter3.dao.SkillDao;
import com.asgarov.chapter3.domain.Person;
import com.asgarov.chapter3.domain.Ranking;
import com.asgarov.chapter3.domain.Skill;
import com.asgarov.chapter3.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HibernateRankingService implements RankingService {
    private final PersonDao personDao = new PersonDao();
    private final SkillDao skillDao = new SkillDao();

    @Override
    public int getRankingFor(String subject, String skill) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            int average = getRankingFor(session, subject, skill);
            tx.commit();
            return average;
        }
    }

    public int getRankingFor(Session session, String subject, String skill) {
        Query<Ranking> query = session.createQuery("from Ranking r "
                + "where r.subject.name=:name "
                + "and r.skill.name=:skill", Ranking.class);
        query.setParameter("name", subject);
        query.setParameter("skill", skill);

        IntSummaryStatistics stats = query.list()
                .stream()
                .collect(Collectors.summarizingInt(Ranking::getRanking));

        int average = (int) stats.getAverage();
        return average;
    }

    @Override
    public void addRanking(String subject, String observer, String skill, int ranking) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            addRanking(session, subject, observer, skill, ranking);
            tx.commit();
        }
    }

    @Override
    public void updateRanking(String subject, String observer, String skill, int rank) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            Ranking ranking = findRanking(session, subject, observer, skill);
            if (ranking == null) {
                addRanking(session, subject, observer, skill, rank);
            } else {
                ranking.setRanking(rank);
            }
            tx.commit();
        }
    }

    @Override
    public void removeRanking(String subject, String observer, String skill) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            removeRanking(session, subject, observer, skill);
            tx.commit();
        }
    }

    @Override
    public Map<String, Integer> findRankingsFor(String subject) {
        Map<String, Integer> results;
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        results = findRankingsFor(session, subject);
        tx.commit();
        session.close();
        return results;
    }

    @Override
    public Person findBestPersonFor(String skill) {
        Person person = null;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            person = findBestPersonFor(session, skill);
            tx.commit();
        }
        return person;
    }

    private Person findBestPersonFor(Session session, String skill) {
        Query<Object[]> query = session.createQuery("select r.subject.name, avg(r.ranking)"
                + " from Ranking r where "
                + "r.skill.name=:skill "
                + "group by r.subject.name "
                + "order by avg(r.ranking) desc", Object[].class);
        query.setParameter("skill", skill);
        List<Object[]> result = query.list();
        if (result.size() > 0) {
            return personDao.findByName(session, (String) result.get(0)[0]);
        }
        return null;
    }

    private Map<String, Integer> findRankingsFor(Session session, String subject) {
        Map<String, Integer> results = new HashMap<>();
        Query<Ranking> query = session.createQuery("from Ranking r where "
                + "r.subject.name=:subject order by r.skill.name", Ranking.class);
        query.setParameter("subject", subject);
        List<Ranking> rankings = query.list();
        String lastSkillName = "";
        int sum = 0;
        int count = 0;
        for (Ranking r : rankings) {
            if (!lastSkillName.equals(r.getSkill().getName())) {
                sum = 0;
                count = 0;
                lastSkillName = r.getSkill().getName();
            }
            sum += r.getRanking();
            count++;
            results.put(lastSkillName, sum / count);
        }
        return results;
    }

    private void removeRanking(Session session, String subject, String observer, String skill) {
        Ranking ranking = findRanking(session, subject, observer, skill);
        if (ranking != null) {
            session.delete(ranking);
        }
    }

    private Ranking findRanking(Session session, String subject,
                                String observer, String skill) {
        Query<Ranking> query = session.createQuery("from Ranking r where "
                + "r.subject.name=:subject and "
                + "r.observer.name=:observer and "
                + "r.skill.name=:skill", Ranking.class);
        query.setParameter("subject", subject);
        query.setParameter("observer", observer);
        query.setParameter("skill", skill);
        return query.uniqueResult();
    }

    private void addRanking(Session session, String subjectName,
                            String observerName, String skillName, int rank) {
        Person subject = personDao.savePerson(session, subjectName);
        Person observer = personDao.savePerson(session, observerName);
        Skill skill = skillDao.saveSkill(session, skillName);

        Ranking ranking = new Ranking();
        ranking.setSubject(subject);
        ranking.setObserver(observer);
        ranking.setSkill(skill);
        ranking.setRanking(rank);
        session.save(ranking);
    }

}
