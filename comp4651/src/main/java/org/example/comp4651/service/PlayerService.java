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

    public List<Player> getTop3PlayersByGoals() {
        return playerRepository.findTop3ByOrderByGoalDesc();
    }

    public void modifyImportanceValue(String name, double importanceValue) {
        Player player = playerRepository.findByName(name);
        player.setImportanceValue(importanceValue);
        this.playerRepository.save(player);
    }

    public void modifyFoul(String name, int foul) {
        Player player = playerRepository.findByName(name);
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

}
