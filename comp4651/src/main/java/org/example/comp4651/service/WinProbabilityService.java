package org.example.comp4651.service;

import lombok.RequiredArgsConstructor;
import org.example.comp4651.entity.WinProbability;
import org.example.comp4651.repository.WinProbabilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WinProbabilityService {

    private final WinProbabilityRepository winProbabilityRepository;

    public List<Double> getWinProb() {
        WinProbability winProb = winProbabilityRepository.findFirstByOrderByIdDesc();
        if (winProb == null) {
            return List.of(50.0, 50.0);
        } else {
            return List.of(winProb.getHomeWinProb(), winProb.getAwayWinProb());
        }

    }
}
