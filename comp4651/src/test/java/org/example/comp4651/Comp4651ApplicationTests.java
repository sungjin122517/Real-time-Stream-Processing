package org.example.comp4651;

import org.example.comp4651.entity.GameStats;
import org.example.comp4651.entity.Player;
import org.example.comp4651.entity.Team;
import org.example.comp4651.repository.GameStatsRepository;
import org.example.comp4651.repository.PlayerRepository;
import org.example.comp4651.repository.TeamRepository;
import org.example.comp4651.service.GameStatsService;
import org.example.comp4651.service.PlayerService;
import org.example.comp4651.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Comp4651ApplicationTests {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private GameStatsRepository gameStatsRepository;

	@Autowired
	private GameStatsService gameStatsService;

	@Test
	void testJpa() {

	}

}
