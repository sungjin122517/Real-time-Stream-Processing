package org.example.comp4651.service;

import lombok.RequiredArgsConstructor;
import org.example.comp4651.entity.Team;
import org.example.comp4651.repository.GameStatsRepository;
import org.example.comp4651.repository.PlayerRepository;
import org.example.comp4651.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GameStatsService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final GameStatsRepository gameStatsRepository;

    private double homeTeamWinProb = 50;
    private double awayTeamWinProb = 50;

    public void adjustForRank(int homeId, int awayId) {
//        Optional<Team> homeTeam = teamRepository.findById(homeId);
//        Optional<Team> awayTeam = teamRepository.findById(awayId);
        double homeRank = teamRepository.findRankById(homeId);
        double awayRank = teamRepository.findRankById(awayId);
        double rankDiff = homeRank - awayRank;
        double adjustment = rankDiff * (-0.5);
        // modify home team win probability
        gameStatsRepository.findById(homeId).setHomeWinProb(gameStatsRepository.findById(homeId).getHomeWinProb() + adjustment);
        // modify away team win probability
        gameStatsRepository.findById(awayId).setAwayWinProb(gameStatsRepository.findById(awayId).getAwayWinProb() - adjustment);
        normalizeProb();
    }

    public void adjustForPlayerImportance(double homeImportance, double awayImportance) {
        double importanceDiff = homeImportance - awayImportance;
        double adjustment = importanceDiff * 0.1;

        normalizeProb();
    }

    public void adjustForScore(int homeScore, int awayScore) {
        double scoreDiff = homeScore - awayScore;
        double adjustment = scoreDiff * 2;
        homeTeamWinProb += adjustment;
        awayTeamWinProb -= adjustment;
        normalizeProb();
    }

    public void adjustForFouls(int homeFouls, int awayFouls) {
        double foulsDiff = homeFouls - awayFouls;
        double adjustment = foulsDiff * (-0.5);
        homeTeamWinProb -= adjustment;
        awayTeamWinProb += adjustment;
        normalizeProb();
    }

    private void normalizeProb() {
        double sum = homeTeamWinProb + awayTeamWinProb;
        homeTeamWinProb = (homeTeamWinProb / sum) * 100;
        awayTeamWinProb = (awayTeamWinProb / sum) * 100;
        homeTeamWinProb = Math.max(0, Math.min(100, homeTeamWinProb));
        awayTeamWinProb = Math.max(0, Math.min(100, awayTeamWinProb));
    }

//    public double getHomeTeamWinProb() {
//        return homeTeamWinProb;
//    }
//
//    public double getAwayTeamWinProb() {
//        return awayTeamWinProb;
//    }
}
