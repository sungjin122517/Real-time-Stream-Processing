package org.example.comp4651;

import org.example.comp4651.entity.Player;
import org.example.comp4651.repository.PlayerRepository;
import org.example.comp4651.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Comp4651ApplicationTests {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private PlayerService playerService;

	@Test
	void testJpa() {
//		Player p1 = new Player();
//		p1.setName("John");
//		p1.setImportanceValue(0.8);
//		p1.setFoul(0);
//		p1.setGoal(0);
//		p1.setPlaying(true);
//		this.playerRepository.save(p1);
//
//		Player p2 = new Player();
//		p2.setName("Mary");
//		p2.setImportanceValue(0.9);
//		p2.setFoul(0);
//		p2.setGoal(0);
//		p2.setPlaying(false);
//		this.playerRepository.save(p2);

		Player p = this.playerRepository.findByName("John");
		// modify importance value of player "John" to 0.95
		this.playerService.modifyImportanceValue("John", 0.95);

	}

}
