package org.example.comp4651.controller;

import org.example.comp4651.WinProbabilityResponse;
import org.example.comp4651.entity.GameStats;
import org.example.comp4651.repository.GameStatsRepository;
import org.example.comp4651.service.GameStatsService;
import org.example.comp4651.service.PlayerService;
import org.example.comp4651.service.TeamService;
import org.example.comp4651.service.WinProbabilityService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {

    private final PlayerService playerService;
    private final TeamService teamService;
    private final GameStatsService gameStatsService;
    private final WinProbabilityService winProbabilityService;
    private final GameStatsRepository gameStatsRepository;

    public HomeController(PlayerService playerService, TeamService teamService, GameStatsService gameStatsService, WinProbabilityService winProbabilityService, GameStatsRepository gameStatsRepository) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.gameStatsService = gameStatsService;
        this.winProbabilityService = winProbabilityService;
        this.gameStatsRepository = gameStatsRepository;
    }

    @GetMapping
    public WinProbabilityResponse getWinProbabilities() {
        GameStats gameStat = gameStatsRepository.findFirstByOrderByIdAsc();

        if (gameStat.getSituation() == 1) {
            gameStatsService.adjustForFouls(gameStat);
        }
        else if (gameStat.getSituation() == 2) {
            gameStatsService.adjustForScore(gameStat);
        }

        List<Double> winProbabilities = winProbabilityService.getWinProb();
        return new WinProbabilityResponse(winProbabilities.get(0), winProbabilities.get(1));
    }

//    public Flux<WinProbabilityResponse> getWinProbabilitiesStream() {
//        return Flux.interval(Duration.ofSeconds(5))
//                .map(i -> {
//                    List<Double> winProbabilities = winProbabilityService.getWinProb();
//                    WinProbabilityResponse response = new WinProbabilityResponse(winProbabilities.get(0), winProbabilities.get(1));
//                    System.out.println(response);
//                    return response;
//                });
//    }


}
