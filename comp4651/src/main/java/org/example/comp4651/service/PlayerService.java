package org.example.comp4651.service;

import lombok.RequiredArgsConstructor;
import org.example.comp4651.entity.Player;
import org.example.comp4651.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public void modifyImportanceValue(String name) {
        Player player = playerRepository.findByName(name);
        double foulPenalty = 0.1;
        double goalBonus = 0.2;

        int foul = player.getFoul();
        int goal = player.getGoal();
        double importanceValue = player.getImportanceValue() + goal * goalBonus - foul * foulPenalty;
        importanceValue = Math.max(0, importanceValue);
        importanceValue = Math.min(1, importanceValue);

        player.setImportanceValue(importanceValue);
        this.playerRepository.save(player);
    }

    public void modifyFoul(String name) {
        Player player = playerRepository.findByName(name);

        // increment foul count of player
        int foul = player.getFoul() + 1;

        player.setFoul(foul);
        this.playerRepository.save(player);
    }

    public void modifyPlayingStatus(String name) {
        Player player = playerRepository.findByName(name);

        // get playing status of player
        boolean playing = player.isPlaying();

        player.setPlaying(!playing);
        this.playerRepository.save(player);
    }

    public void modifyGoal(String name, int score) {
        Player player = playerRepository.findByName(name);

        // increment goal count of player
        int goal = player.getGoal() + score;

        player.setGoal(goal);
        this.playerRepository.save(player);
    }

}
