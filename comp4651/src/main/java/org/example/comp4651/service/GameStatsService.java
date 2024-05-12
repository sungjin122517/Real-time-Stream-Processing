package org.example.comp4651.service;

import lombok.RequiredArgsConstructor;
import org.example.comp4651.entity.GameStats;
import org.example.comp4651.entity.Player;
import org.example.comp4651.entity.WinProbability;
import org.example.comp4651.repository.GameStatsRepository;
import org.example.comp4651.repository.PlayerRepository;
import org.example.comp4651.repository.TeamRepository;
import org.example.comp4651.repository.WinProbabilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameStatsService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final GameStatsRepository gameStatsRepository;
    private final WinProbabilityRepository winProbabilityRepository;

    public void adjustForRank(int gameStatId) {
        GameStats gameStats = gameStatsRepository.findById(gameStatId);
        WinProbability winProbability = new WinProbability();

        int homeId = gameStats.getHomeTeam().getId();
        int awayId = gameStats.getAwayTeam().getId();

        double homeRank = teamRepository.findById(homeId).getRank();
        double awayRank = teamRepository.findById(awayId).getRank();
        double rankDiff = homeRank - awayRank;
        double adjustment = rankDiff * (-2.5);

        double homeWinProb = 50 + adjustment;
        double awayWinProb = 50 - adjustment;

        homeWinProb = normalizeProb(homeWinProb, awayWinProb).getFirst();
        awayWinProb = 100 - homeWinProb;

        winProbability.setHomeWinProb(homeWinProb);
        winProbability.setAwayWinProb(awayWinProb);

        winProbabilityRepository.save(winProbability);
    }

//    public void setPlayerImportance(int gameStatId) {
//        GameStats gameStats = gameStatsRepository.findById(gameStatId);
//
//        int homeId = gameStats.getHomeTeam().getId();
//        int awayId = gameStats.getAwayTeam().getId();
//
//        List<Player> homePlayers = playerRepository.findByTeamId(homeId);
//        List<Player> awayPlayers = playerRepository.findByTeamId(awayId);
//
//        // get the sum of importance value of home players who are playing
//        double homeImportance = homePlayers.stream().filter(Player::isPlaying).mapToDouble(Player::getImportanceValue).sum();
//        // get the sum of importance value of away players who are playing
//        double awayImportance = awayPlayers.stream().filter(Player::isPlaying).mapToDouble(Player::getImportanceValue).sum();
//
//        gameStats.setHomeImportanceValue(homeImportance);
//        gameStats.setAwayImportanceValue(awayImportance);
//
//        gameStatsRepository.save(gameStats);
//    }

    public void adjustForPlayerImportance(int gameStatId) {
        GameStats gameStats = gameStatsRepository.findById(gameStatId);
        WinProbability lastWinProbability = winProbabilityRepository.findFirstByOrderByIdAsc();

        int homeId = gameStats.getHomeTeam().getId();
        int awayId = gameStats.getAwayTeam().getId();

        List<Player> homePlayers = playerRepository.findByTeamId(homeId);
        List<Player> awayPlayers = playerRepository.findByTeamId(awayId);

        // get the sum of importance value of home players who are playing
        double homeImportance = homePlayers.stream().filter(Player::isPlaying).mapToDouble(Player::getImportanceValue).sum();
        // get the sum of importance value of away players who are playing
        double awayImportance = awayPlayers.stream().filter(Player::isPlaying).mapToDouble(Player::getImportanceValue).sum();

        double importanceDiff = homeImportance - awayImportance;
        double adjustment = importanceDiff * 10;

        double homeWinProb = lastWinProbability.getHomeWinProb() + adjustment;
        double awayWinProb = lastWinProbability.getAwayWinProb() - adjustment;

        homeWinProb = normalizeProb(homeWinProb, awayWinProb).getFirst();
        awayWinProb = 100 - homeWinProb;

        WinProbability winProbability = new WinProbability();
        winProbability.setHomeWinProb(homeWinProb);
        winProbability.setAwayWinProb(awayWinProb);

        winProbabilityRepository.save(winProbability);
    }

    public void adjustForScore(int gameStatId) {
        GameStats gameStats = gameStatsRepository.findById(gameStatId);
        WinProbability firstWinProbability = winProbabilityRepository.findFirstByOrderByIdAsc();

        int homeScore = gameStats.getHomeScore();
        int awayScore = gameStats.getAwayScore();

        double scoreDiff = homeScore - awayScore;
        double adjustment = scoreDiff * 0.8;

        double homeWinProb = firstWinProbability.getHomeWinProb() + adjustment;
        double awayWinProb = firstWinProbability.getAwayWinProb() - adjustment;

        homeWinProb = normalizeProb(homeWinProb, awayWinProb).getFirst();
        awayWinProb = 100 - homeWinProb;

        // add new win probabilities to win probability table
        WinProbability winProbability = new WinProbability();
        winProbability.setHomeWinProb(homeWinProb);
        winProbability.setAwayWinProb(awayWinProb);

        winProbabilityRepository.save(winProbability);
    }


    public void adjustForFouls(int gameStatId) {
        GameStats gameStats = gameStatsRepository.findById(gameStatId);
        WinProbability firstWinProbability = winProbabilityRepository.findFirstByOrderByIdAsc();

        int homeFouls = gameStats.getHomeFouls();
        int awayFouls = gameStats.getAwayFouls();

        double foulsDiff = homeFouls - awayFouls;
        double adjustment = foulsDiff * (-0.5);

        double homeWinProb = firstWinProbability.getHomeWinProb() - adjustment;
        double awayWinProb = firstWinProbability.getAwayWinProb() + adjustment;

        homeWinProb = normalizeProb(homeWinProb, awayWinProb).getFirst();
        awayWinProb = 100 - homeWinProb;

        // add new probability to win probability table
        WinProbability winProbability = new WinProbability();
        winProbability.setHomeWinProb(homeWinProb);
        winProbability.setAwayWinProb(awayWinProb);

        winProbabilityRepository.save(winProbability);
    }

    private List<Double> normalizeProb(double home, double away) {
        double sum = home + away;
        home = (home / sum) * 100;
        away = (away / sum) * 100;
        home = Math.max(0, Math.min(100, home));
        away = Math.max(0, Math.min(100, away));

        String homeStr = String.format("%.2f", home);
        String awayStr = String.format("%.2f", away);

        home = Double.parseDouble(homeStr);
        away = Double.parseDouble(awayStr);

        return List.of(home, away);
    }

}
