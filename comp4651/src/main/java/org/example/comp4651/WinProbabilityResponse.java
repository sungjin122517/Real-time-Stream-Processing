package org.example.comp4651;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WinProbabilityResponse {
    private double homeWinProbability;
    private double awayWinProbability;

    public WinProbabilityResponse(double homeWinProbability, double awayWinProbability) {
        this.homeWinProbability = homeWinProbability;
        this.awayWinProbability = awayWinProbability;
    }
}
