package com.asgarov.chapter3.service;

import com.asgarov.chapter3.domain.Person;

import java.util.Map;

public interface RankingService {
    int getRankingFor(String subject, String skill);

    void addRanking(String subject, String observer, String skill, int ranking);

    void updateRanking(String subject, String observer, String skill, int ranking);

    void removeRanking(String subject, String observer, String skill);

    Map<String, Integer> findRankingsFor(String somebody);

    Person findBestPersonFor(String skill);
}
