package org.example.comp4651.repository;

import org.example.comp4651.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    // find team by name
    Team findByName(String name);

    // find team by id
    Team findById(int id);
}
