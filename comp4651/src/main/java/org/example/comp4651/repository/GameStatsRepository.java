package org.example.comp4651.repository;

import org.example.comp4651.entity.GameStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStatsRepository extends JpaRepository<GameStats, Integer> {
    GameStats findById(int id);
}
