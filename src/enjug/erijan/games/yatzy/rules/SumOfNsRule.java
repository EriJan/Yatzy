package enjug.erijan.games.yatzy.rules;

import java.util.stream.IntStream;

/**
 * Created by Janne on 29/10/15.
 */
public class SumOfNsRule implements ScoreRule {

  private final int targetNumber;

  public SumOfNsRule(int targetNumber) {
    this.targetNumber = targetNumber;
  }

  @Override
  public int calculateScore(int... result) {
    int score = IntStream.of(result)
        .filter(val -> val == targetNumber).sum();
    return score;
  }
}
