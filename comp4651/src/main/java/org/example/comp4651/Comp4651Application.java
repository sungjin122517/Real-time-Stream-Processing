package org.example.comp4651;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import org.example.comp4651.entity.WinProbability;
import org.example.comp4651.repository.WinProbabilityRepository;
import org.example.comp4651.entity.Team;
import org.example.comp4651.repository.TeamRepository;
import org.example.comp4651.service.GameStatsService;

@SpringBootApplication
public class Comp4651Application {

	public static void main(String[] args) {
		SpringApplication.run(Comp4651Application.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(WinProbabilityRepository winProbabilityRepository, TeamRepository teamRepository, GameStatsService gameStatsService) {
		return args -> {
			if (winProbabilityRepository.count() == 0) {
				WinProbability winProb = new WinProbability();
				winProb.setHomeWinProb(50);
				winProb.setAwayWinProb(50);
				winProbabilityRepository.save(winProb);
			}

			if (teamRepository.count() == 0) {
				Team team1 = new Team();
				team1.setName("Boston Celtics");
				team1.setRank(1);
				teamRepository.save(team1);

				Team team2 = new Team();
				team2.setName("New York Knicks");
				team2.setRank(4);
				teamRepository.save(team2);

				// adjust prob for team rank
				gameStatsService.adjustForRank("1");
			}



		};
	}
}
