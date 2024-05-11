package org.example.comp4651.controller;

import org.example.comp4651.entity.Player;
import org.example.comp4651.service.GameService;
import org.example.comp4651.service.GameStatsService;
import org.example.comp4651.service.PlayerService;
import org.example.comp4651.service.TeamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final PlayerService playerService;
    private final TeamService teamService;
    private final GameService gameService;
    private final GameStatsService gameStatsService;

    public HomeController(PlayerService playerService, TeamService teamService, GameService gameService, GameStatsService gameStatsService) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.gameService = gameService;
        this.gameStatsService = gameStatsService;
    }

    @GetMapping
    public List<Player> getTop3PlayersByGoals() {
        return playerService.getTop3PlayersByGoals();
    }
}
