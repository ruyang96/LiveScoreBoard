package com.ruyang.livescoreboard;

import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Getter
public class ScoreBoard {
    private final List<Match> matches = new ArrayList<>();

    public void startNewMatch(String homeTeam, String awayTeam) {
        // the home team and away team names must be valid
        if (!StringUtils.isBlank(homeTeam) && !StringUtils.isBlank(awayTeam)) {
            // if a match with same names was already started, cannot start a new match with same teams
            boolean matchExisted = matches.stream()
                    .anyMatch(match -> Objects.equals(match.getHomeTeam(), homeTeam) && Objects.equals(match.getAwayTeam(), awayTeam));
            if (!matchExisted) {
                matches.add(new Match(homeTeam, awayTeam));
            }
        }
    }

    public void updateScore(String homeTeam, long homeScore, String awayTeam, long awayScore) {
        if (homeScore >= 0 && awayScore >= 0) {
            for (Match match : matches) {
                if (Objects.equals(match.getHomeTeam(), homeTeam) && Objects.equals(match.getAwayTeam(), awayTeam)) {
                    match.setHomeScore(homeScore);
                    match.setAwayScore(awayScore);
                }
            }
        }

    }

    public void finishMatch(String homeTeam, String awayTeam) {
        matches.removeIf(match -> Objects.equals(match.getHomeTeam(), homeTeam) && Objects.equals(match.getAwayTeam(), awayTeam));
    }

    public List<String> getSummary() {
        return matches.stream()
                .sorted(Comparator
                        .comparingLong(Match::getTotalScore)
                        .reversed()
                        .thenComparing(Comparator.comparing(Match::getStartTime).reversed())
                )
                .map(Match::toString).toList();
    }

    public void reset() {
        matches.clear();
    }
}
