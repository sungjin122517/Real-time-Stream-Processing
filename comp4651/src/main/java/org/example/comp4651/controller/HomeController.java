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

import java.time.Duration;
import java.util.List;
import java.util.Random;

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
        GameStats gameStat = gameStatsRepository.findFirstByOrderByGameidAsc();

        if (gameStat.getSituation() == 1 || gameStat.getSituation() == 9) {
//            System.out.println("foul@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$");
            gameStatsService.adjustForFouls(gameStat);
        }
        // else if gameStat is between 3 and 7 or 11 and 15
        else if (gameStat.getSituation() >= 3 && gameStat.getSituation() <= 7 || gameStat.getSituation() >= 11 && gameStat.getSituation() <= 15) {
//            System.out.println("score@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$");
            gameStatsService.adjustForScore(gameStat);
        }

        List<Double> winProbabilities = winProbabilityService.getWinProb();
        double probabilityScore = winProbabilities.get(0) ;
        return new WinProbabilityResponse(probabilityScore, 100-probabilityScore);

//        double homeFoul = gameStat.getHomeFoul();
//        double awayFoul = gameStat.getAwayFoul();
//        return new WinProbabilityResponse(homeFoul, awayFoul);
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
