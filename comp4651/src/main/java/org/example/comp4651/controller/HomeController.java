package org.example.comp4651.controller;

import org.example.comp4651.WinProbabilityResponse;
import org.example.comp4651.service.GameStatsService;
import org.example.comp4651.service.PlayerService;
import org.example.comp4651.service.TeamService;
import org.example.comp4651.service.WinProbabilityService;
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
    private final WinProbabilityService winProbabilityService;

    public HomeController(PlayerService playerService, TeamService teamService, GameStatsService gameStatsService, WinProbabilityService winProbabilityService) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.gameStatsService = gameStatsService;
        this.winProbabilityService = winProbabilityService;
    }

    @GetMapping
    public WinProbabilityResponse getWinProbabilities() {
        List<Double> winProbabilities = winProbabilityService.getWinProb();
        return new WinProbabilityResponse(winProbabilities.get(0), winProbabilities.get(1));
    }

}
