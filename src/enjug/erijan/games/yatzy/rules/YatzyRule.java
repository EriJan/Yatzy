package enjug.erijan.games.yatzy.rules;

import java.util.stream.IntStream;

/**
 * Created by Janne on 29/10/15.
 */

public class YatzyRule implements ScoreRule {
  private final int yatzyScore;

  public YatzyRule() {
    yatzyScore = 50;
  }

  @Override
  public int calculateScore(int... result) {

    long uniqueValues = IntStream.of(result).distinct().sorted().count();
    int score = 0;
    if (uniqueValues == 1 && result.length > 1) {
      score = 50;
    }

    return score;
  }
}
