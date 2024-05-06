# Live Football Scoreboard

This project implements a simple Live Football Scoreboard library using Java.

## Functionality

The Live Football World Cup Scoreboard supports the following operations:

1. Start a new match with initial score 0-0.
2. Update the score of an ongoing match.
3. Finish a match currently in progress.
4. Get a summary of matches in progress, ordered by total score and start time.

## Assumptions

1. Team names are case-sensitive.
2. Negative scores are not allowed.
3. Match summaries are formatted as "Home Team homeScore - awayScore Away Team".
4. Matches with the same total score are ordered by start time, with the most recently started match appearing first.
5. Not throw exception with invalid values, for example negative scores.


## Edge Cases Covered

1. Updating score with negative values.
2. Finishing a match that doesn't exist.
3. Starting several matches with the same teams.
4. Getting summary when there is no matches
5. Updating score with team orders switched.
6. Updating score with big numbers.
9. Handling score summary with empty team names.

## Running the Tests
To run the tests, simply execute the following command in the project directory. 


Make sure you have gradle installed on your computer. If not, you can follow [this tutorial](https://gradle.org/install/) to install it

```bash
gradle clean test
```