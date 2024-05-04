package com.ruyang.livescoreboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatchTest {
    @Test
    void testGetSumScore(){
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        var match = new Match(homeTeam, awayTeam);
        match.setHomeScore(3);
        match.setAwayScore(2);
        assertEquals(5, match.getTotalScore());
    }

    @Test
    void testToString(){
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        var match = new Match(homeTeam, awayTeam);
        match.setHomeScore(3);
        match.setAwayScore(2);
        assertEquals("Mexico 3 - Canada 2", match.toString());
    }

    // if setting the score with negative values,
    // the score setting operation would be invalid
    @Test
    void testGetSumScoreWithNegativeScores() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        var match = new Match(homeTeam, awayTeam);
        match.setHomeScore(-5);
        match.setAwayScore(-6);
        assertEquals(0, match.getTotalScore());
    }

    @Test
    void testGetGetSumScoreWithLargeScores() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        var match = new Match(homeTeam, awayTeam);
        match.setHomeScore(Integer.MAX_VALUE);
        match.setAwayScore(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE * 2L, match.getTotalScore());
    }

    // make sure toString method is able to handle null or empty team names
    @Test
    void testStartMatchWithInvalidNames() {
        var match = new Match("", null);
        match.setHomeScore(2);
        match.setAwayScore(1);
        assertEquals(" 2 - null 1", match.toString());
    }
}
