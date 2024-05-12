package org.example.comp4651.repository;

import org.example.comp4651.entity.WinProbability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinProbabilityRepository extends JpaRepository<WinProbability, Integer>{
    // find first WinProbability
    WinProbability findFirstByOrderByIdAsc();

    // find last WinProbability
    WinProbability findFirstByOrderByIdDesc();
}
