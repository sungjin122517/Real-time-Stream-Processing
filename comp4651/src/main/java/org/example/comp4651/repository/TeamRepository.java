package org.example.comp4651.repository;

import org.example.comp4651.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    // find team by name
    Team findByName(String name);

    // find team rank by name
    int findRankByName(String name);

    // find team rank by id
    int findRankById(int id);
}
