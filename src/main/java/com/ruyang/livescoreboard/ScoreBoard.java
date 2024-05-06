package com.ruyang.livescoreboard;

import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Getter
public class ScoreBoard {
    @Getter(AccessLevel.NONE)
    private final Log logger = LogFactory.getLog(this.getClass());
    private final List<Match> matches = new ArrayList<>();

    public void startNewMatch(String homeTeam, String awayTeam) {
        // the home team and away team names must be valid
        if (!StringUtils.isBlank(homeTeam) && !StringUtils.isBlank(awayTeam)) {
            // if a match with same names was already started, cannot start a new match with same teams
            boolean matchExist = matches.stream()
                    .anyMatch(match -> Objects.equals(match.getHomeTeam(), homeTeam) && Objects.equals(match.getAwayTeam(), awayTeam));
            if (!matchExist) {
                matches.add(new Match(homeTeam, awayTeam));
            } else {
                logger.error(String.format("Match with home team-%s and away team-%s already started.", homeTeam, awayTeam));
            }
        } else {
            logger.error(String.format("Team names are invalid with %s and %s, please check the team names again", homeTeam, awayTeam));
        }
    }

    public void updateScore(String homeTeam, long homeScore, String awayTeam, long awayScore) {
        if (homeScore >= 0 && awayScore >= 0) {
            boolean matchExist = false;
            for (Match match : matches) {
                if (Objects.equals(match.getHomeTeam(), homeTeam) && Objects.equals(match.getAwayTeam(), awayTeam)) {
                    matchExist = true;
                    match.setHomeScore(homeScore);
                    match.setAwayScore(awayScore);
                    break;
                }
            }
            if(!matchExist){
                logger.error(String.format("The match with home team-%s and away team-%s doesn't exist.", homeTeam, awayTeam));
            }
        } else {
            logger.error(String.format("Home team score(%s) or away team score(%s) are negative, please provide the right score", homeScore, awayScore));
        }

    }

    public void finishMatch(String homeTeam, String awayTeam) {
        boolean matchExist = matches.removeIf(match -> Objects.equals(match.getHomeTeam(), homeTeam) && Objects.equals(match.getAwayTeam(), awayTeam));
        if(!matchExist){
            logger.error(String.format("The match with home team - %s and away team - %s doesn't exist.", homeTeam, awayTeam));
        }
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
