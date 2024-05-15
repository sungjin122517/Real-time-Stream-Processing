package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@SpringBootApplication
@RestController
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @GetMapping("/")
    public WinningProbability getWinningProbability() {
        // Generate a random winning probability (between 0 and 100)
        int probability = new Random().nextInt(101);
        return new WinningProbability(probability);
    }

    static class WinningProbability {
        private int homeWinProbability;  // Changed field name to homeWinProbability

        public WinningProbability(int homeWinProbability) {
            this.homeWinProbability = homeWinProbability;
        }

        public int getHomeWinProbability() {
            return homeWinProbability;
        }
    }
}