package com.ruyang.livescoreboard;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class ScoreBoard {
    private final List<Match> matches = new ArrayList<>();

    public void startNewMatch(String homeTeam, String awayTeam) {
    }

    public void updateScore(String homeTeam, long homeScore, String awayTeam, long awayScore) {
    }

    public void finishMatch(String homeTeam, String awayTeam) {

    }

    public List<String> getSummary() {
        return List.of();
    }

    public void reset() {

    }
}
