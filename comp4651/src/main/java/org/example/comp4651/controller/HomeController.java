package org.example.comp4651.controller;

import org.example.comp4651.WinProbabilityResponse;
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
    private final GameStatsService gameStatsService;

    public HomeController(PlayerService playerService, TeamService teamService, GameStatsService gameStatsService) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.gameStatsService = gameStatsService;
    }

    @GetMapping
    public WinProbabilityResponse getWinProbabilities() {
        List<Double> winProbabilities = gameStatsService.getWinProb(1);
        return new WinProbabilityResponse(winProbabilities.get(0), winProbabilities.get(1));
    }

}
