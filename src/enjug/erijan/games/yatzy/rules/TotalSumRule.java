package enjug.erijan.games.yatzy.rules;

import java.util.stream.IntStream;

/**
 * Created by Janne on 29/10/15.
 */
public class TotalSumRule implements ScoreRule {
  @Override
  public int calculateScore(int... result) {
    int score = IntStream.of(result).sum();
    return score;
  }
}
