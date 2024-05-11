package org.example.comp4651.repository;

import org.example.comp4651.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer>{
    Player findByName(String name);
    // find players of top 3 goals
    List<Player> findTop3ByOrderByGoalDesc();
    // find players of top 3 fouls
    List<Player> findTop3ByOrderByFoulDesc();
    // find players of top 3 importance value
    List<Player> findTop3ByOrderByImportanceValueDesc();

    // find players by team id
    List<Player> findByTeamId(Integer teamId);
}
