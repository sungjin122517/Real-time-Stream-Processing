package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @GetMapping
    public WinningProbability getWinningProbability() {
        // Generate a random winning probability (between 0 and 100)
        double probability = new Random().nextDouble(101);
        return new WinningProbability(probability, 100-probability);
    }

    static class WinningProbability {
        private double homeWinProbability;  // Changed field name to homeWinProbability
        private double awayWinProbability; // Changed field name to

        public WinningProbability(double homeWinProbability, double awayWinProbability) {
            this.homeWinProbability = homeWinProbability;
            this.awayWinProbability = awayWinProbability;
        }

        public double getHomeWinProbability() {
            return homeWinProbability;
        }
        public double getAwayWinProbability() {
            return awayWinProbability;
        }
    }
}