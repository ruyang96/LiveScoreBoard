package com.ruyang.livescoreboard;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreBoardTest {
    ScoreBoard scoreBoard = new ScoreBoard();

    @BeforeEach
    void reset() {
        scoreBoard.reset();
    }

    @Test
    void testStartNewMatch() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        assertEquals(1, scoreBoard.getMatches().size());
        Match match = scoreBoard.getMatches().get(0);
        assertEquals(homeTeam, match.getHomeTeam());
        assertEquals(0, match.getHomeScore());
        assertEquals(awayTeam, match.getAwayTeam());
        assertEquals(0, match.getAwayScore());
    }

    // if trying to start multiple matches with the same teams
    // only the first match started shall be valid
    @Test
    void testStartNewMatchWithSameTeams() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        assertEquals(1, scoreBoard.getMatches().size());
        Match match = scoreBoard.getMatches().get(0);
    }

    // if trying to start matches with invalid names
    // the match would not be added to score board
    @Test
    void testStartNewMatchWithNullTeamNames() {
        String homeTeam = "";
        String awayTeam = null;
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        assertEquals(0, scoreBoard.getMatches().size());
    }

    // if trying to start multiple matches with the same teams
    // only the first match started shall be valid
    @Test
    void testStartNewMatchWithSameTeamsAfterUpdatingScore() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        scoreBoard.updateScore(homeTeam, 1, awayTeam, 2);
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        assertEquals(1, scoreBoard.getMatches().size());
        Match match = scoreBoard.getMatches().get(0);
        assertEquals(1, match.getHomeScore());
        assertEquals(2, match.getAwayScore());
    }

    @Test
    void testUpdateScore() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        scoreBoard.updateScore(homeTeam, 1, awayTeam, 2);
        Match match = scoreBoard.getMatches().get(0);
        assertEquals(1, match.getHomeScore());
        assertEquals(2, match.getAwayScore());
    }

    // updating score with nagative values is not allowed
    // here the updating operation would be invalid
    // and the score remains same as before
    @Test
    void testUpdateScoreWithNegativeValues() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        scoreBoard.updateScore(homeTeam, 6, awayTeam, 6);
        scoreBoard.updateScore(homeTeam, -2, awayTeam, 0);
        Match match = scoreBoard.getMatches().get(0);
        assertEquals(6, match.getHomeScore());
        assertEquals(6, match.getAwayScore());
    }

    // updating score with teams that don't exist
    // here the updating operation would be invalid
    // and the score remains same as before
    @Test
    void testUpdateScoreWhenTeamNotExist() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        scoreBoard.updateScore(homeTeam, 6, awayTeam, 6);
        scoreBoard.updateScore("testTeam", 1, awayTeam, 2);
        Match match = scoreBoard.getMatches().get(0);
        assertEquals(6, match.getHomeScore());
        assertEquals(6, match.getAwayScore());
    }

    // updating score with wrong team order
    // here the updating operation would be invalid
    // and the score remains same as before
    @Test
    void testUpdateScoreWithWrongTeamOrder() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        scoreBoard.updateScore(homeTeam, 6, awayTeam, 6);
        scoreBoard.updateScore(awayTeam, 1, homeTeam, 1);
        Match match = scoreBoard.getMatches().get(0);
        assertEquals(6, match.getHomeScore());
        assertEquals(6, match.getAwayScore());
    }

    @Test
    void testFinishMatch() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        scoreBoard.finishMatch(homeTeam, awayTeam);
        assertEquals(0, scoreBoard.getMatches().size());
    }

    // if the match was not found, finishing operation would be invalid
    // , and no changes applied to the current score board
    @Test
    void testFinishMatchThatNotExist() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        scoreBoard.finishMatch("testTeam", awayTeam);
        assertEquals(1, scoreBoard.getMatches().size());
    }

    // if the match team order was wrong, finishing operation would be invalid
    // and no changes should be applied to the current score board
    @Test
    void testFinishMatchWithWrongTeamOrder() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        scoreBoard.finishMatch(awayTeam, homeTeam);
        assertEquals(1, scoreBoard.getMatches().size());
    }

    // if trying to finish a match that already finished before
    // no changes should be applied to the current score board
    @Test
    void testFinishMatchAlreadyFinished() {
        String homeTeam1 = "Mexico";
        String awayTeam1 = "Canada";
        String homeTeam2 = "Spain";
        String awayTeam2 = "Brazil";
        scoreBoard.startNewMatch(homeTeam1, awayTeam1);
        scoreBoard.startNewMatch(homeTeam2, awayTeam2);
        scoreBoard.finishMatch(homeTeam1, awayTeam1);
        assertEquals(1, scoreBoard.getMatches().size());
        scoreBoard.finishMatch(homeTeam1, awayTeam1);
        assertEquals(1, scoreBoard.getMatches().size());
    }

    // updating score after the match is finished
    // here the updating operation would be invalid
    // no changes should be applied to the current score board
    @Test
    void testUpdateScoreAfterMatchFinished() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        scoreBoard.startNewMatch(homeTeam, awayTeam);
        scoreBoard.updateScore(homeTeam, 6, awayTeam, 6);
        scoreBoard.finishMatch(homeTeam, awayTeam);
        scoreBoard.updateScore(awayTeam, 1, homeTeam, 1);
        assertEquals(0, scoreBoard.getMatches().size());
    }

    @Test
    void testGetSummary() {
        String homeTeam1 = "Mexico";
        String awayTeam1 = "Canada";
        String homeTeam2 = "Spain";
        String awayTeam2 = "Brazil";
        String homeTeam3 = "Germany";
        String awayTeam3 = "France";
        String homeTeam4 = "Uruguay";
        String awayTeam4 = "Italy";
        scoreBoard.startNewMatch(homeTeam1, awayTeam1);
        scoreBoard.startNewMatch(homeTeam2, awayTeam2);
        scoreBoard.startNewMatch(homeTeam3, awayTeam3);
        scoreBoard.startNewMatch(homeTeam4, awayTeam4);
        scoreBoard.updateScore(homeTeam1, 0, awayTeam1, 5);
        scoreBoard.updateScore(homeTeam2, 10, awayTeam2, 2);
        scoreBoard.updateScore(homeTeam3, 2, awayTeam3, 2);
        scoreBoard.updateScore(homeTeam4, 6, awayTeam4, 6);
        List<String> matchesSummary = scoreBoard.getSummary();
        assertEquals(4, matchesSummary.size());

        assertEquals("Uruguay 6 - Italy 6", matchesSummary.get(0));
        assertEquals("Spain 10 - Brazil 2", matchesSummary.get(1));
        assertEquals("Mexico 0 - Canada 5", matchesSummary.get(2));
        assertEquals("Germany 2 - France 2", matchesSummary.get(3));
    }

    @Test
    void testGetSummaryWithDifferentStartingTimeForTeam2And4() {
        String homeTeam1 = "Mexico";
        String awayTeam1 = "Canada";
        String homeTeam2 = "Spain";
        String awayTeam2 = "Brazil";
        String homeTeam3 = "Germany";
        String awayTeam3 = "France";
        String homeTeam4 = "Uruguay";
        String awayTeam4 = "Italy";
        scoreBoard.startNewMatch(homeTeam1, awayTeam1);
        scoreBoard.startNewMatch(homeTeam4, awayTeam4);
        scoreBoard.startNewMatch(homeTeam2, awayTeam2);
        scoreBoard.startNewMatch(homeTeam3, awayTeam3);
        scoreBoard.updateScore(homeTeam1, 0, awayTeam1, 5);
        scoreBoard.updateScore(homeTeam2, 10, awayTeam2, 2);
        scoreBoard.updateScore(homeTeam3, 2, awayTeam3, 2);
        scoreBoard.updateScore(homeTeam4, 6, awayTeam4, 6);
        List<String> matchesSummary = scoreBoard.getSummary();
        assertEquals(4, matchesSummary.size());

        assertEquals("Spain 10 - Brazil 2", matchesSummary.get(0));
        assertEquals("Uruguay 6 - Italy 6", matchesSummary.get(1));
        assertEquals("Mexico 0 - Canada 5", matchesSummary.get(2));
        assertEquals("Germany 2 - France 2", matchesSummary.get(3));
    }

    @Test
    void testGetSummaryShouldNotIncludeFinishedMatch() {
        String homeTeam1 = "Mexico";
        String awayTeam1 = "Canada";
        String homeTeam2 = "Spain";
        String awayTeam2 = "Brazil";
        String homeTeam3 = "Germany";
        String awayTeam3 = "France";
        String homeTeam4 = "Uruguay";
        String awayTeam4 = "Italy";
        scoreBoard.startNewMatch(homeTeam1, awayTeam1);
        scoreBoard.startNewMatch(homeTeam2, awayTeam2);
        scoreBoard.startNewMatch(homeTeam3, awayTeam3);
        scoreBoard.startNewMatch(homeTeam4, awayTeam4);
        scoreBoard.updateScore(homeTeam1, 0, awayTeam1, 5);
        scoreBoard.updateScore(homeTeam2, 10, awayTeam2, 2);
        scoreBoard.updateScore(homeTeam3, 2, awayTeam3, 2);
        scoreBoard.updateScore(homeTeam4, 6, awayTeam4, 6);

        // finish the match between Germany and France
        scoreBoard.finishMatch(homeTeam3, awayTeam3);

        List<String> matchesSummary = scoreBoard.getSummary();
        assertEquals(3, matchesSummary.size());

        assertEquals("Uruguay 6 - Italy 6", matchesSummary.get(0));
        assertEquals("Spain 10 - Brazil 2", matchesSummary.get(1));
        assertEquals("Mexico 0 - Canada 5", matchesSummary.get(2));
    }

    @Test
    void testGetSummaryWhenThereIsNoMathces() {
        List<String> matchesSummary = scoreBoard.getSummary();
        assertEquals(0, matchesSummary.size());
    }
}
