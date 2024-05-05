package com.ruyang.livescoreboard;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Match {
    private String homeTeam;
    private String awayTeam;
    private long homeScore;
    private long awayScore;
    private LocalDateTime startTime;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.startTime = LocalDateTime.now();
    }

    public void setHomeScore(long homeScore) {
        if (homeScore < 0) {
            homeScore = 0;
        }
        this.homeScore = homeScore;
    }

    public void setAwayScore(long awayScore) {
        if (awayScore < 0) {
            awayScore = 0;
        }
        this.awayScore = awayScore;
    }

    public Long getTotalScore() {
        return this.homeScore + this.awayScore;
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s %s", homeTeam, homeScore, awayTeam, awayScore);
    }
}
