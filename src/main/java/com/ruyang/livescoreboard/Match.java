package com.ruyang.livescoreboard;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDateTime;

@Getter
public class Match {
    @Getter(AccessLevel.NONE)
    private final Log logger = LogFactory.getLog(this.getClass());
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
            logger.error(String.format("Home team score(%s)  is negative, please provide the right score", homeScore));
        }
        this.homeScore = homeScore;
    }

    public void setAwayScore(long awayScore) {
        if (awayScore < 0) {
            awayScore = 0;
            logger.error(String.format("Away team score(%s)  is negative, please provide the right score", awayScore));
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
