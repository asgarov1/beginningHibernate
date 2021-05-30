package com.asgarov.chapter3.domain;

import com.asgarov.chapter3.service.HibernateRankingService;
import com.asgarov.chapter3.service.RankingService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class RankingTest extends BaseTest {

    private final RankingService rankingService = new HibernateRankingService();

    @BeforeMethod
    void populateRankingData() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            rankingService.addRanking("J. C. Smell", "Gene Showrama", "Java", 6);
            rankingService.addRanking("J. C. Smell", "Scottball Most", "Java", 7);
            rankingService.addRanking("J. C. Smell", "Drew Lombardo", "Java", 8);
            tx.commit();
        }
    }

    @AfterMethod
    void cleanUp() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Query<Ranking> query = session.createQuery("from Ranking", Ranking.class);
            query.list().forEach(session::delete);
            tx.commit();
        }
    }

    @Test
    public void testRankings() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Query<Ranking> query = session.createQuery("from Ranking r "
                    + "where r.subject.name=:name "
                    + "and r.skill.name=:skill", Ranking.class);
            query.setParameter("name", "J. C. Smell");
            query.setParameter("skill", "Java");

            IntSummaryStatistics stats = query.list()
                    .stream()
                    .collect(Collectors.summarizingInt(Ranking::getRanking));
            long count = stats.getCount();
            int average = (int) stats.getAverage();
            tx.commit();
            session.close();
            assertEquals(count, 3);
            assertEquals(average, 7);
        }
    }

    @Test
    public void changeRanking() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Query<Ranking> query = session.createQuery("from Ranking r "
                    + "where r.subject.name=:subject and "
                    + "r.observer.name=:observer and "
                    + "r.skill.name=:skill", Ranking.class);
            query.setParameter("subject", "J. C. Smell");
            query.setParameter("observer", "Gene Showrama");
            query.setParameter("skill", "Java");
            Ranking ranking = query.uniqueResult();
            assertNotNull(ranking, "Could not find matching ranking");
            ranking.setRanking(9);
            tx.commit();
        }
        assertEquals(rankingService.getRankingFor("J. C. Smell", "Java"), 8);
    }

    @Test
    public void removeRanking() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Ranking ranking = findRanking(session, "J. C. Smell", "Gene Showrama", "Java");
            assertNotNull(ranking, "Ranking not found");
            session.delete(ranking);
            tx.commit();
        }
        assertEquals(rankingService.getRankingFor("J. C. Smell", "Java"), 7);
    }

    private Ranking findRanking(Session session, String subject, String observer, String skill) {
        Query<Ranking> query = session.createQuery("from Ranking r "
                + "where r.subject.name=:subject and "
                + "r.observer.name=:observer and "
                + " r.skill.name=:skill", Ranking.class);
        query.setParameter("subject", subject);
        query.setParameter("observer", observer);
        query.setParameter("skill", skill);
        Ranking ranking = query.uniqueResult();
        return ranking;
    }
}