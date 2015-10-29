package enjug.erijan;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Janne on 29/10/15.
 */
public class BigStraightRule implements ScoreRule {

  private final int bigStraightScore;

  public BigStraightRule() {
    bigStraightScore = IntStream.of(2, 3, 4, 5,6).sum();
  }

  @Override
  public int calculateScore(int... result) {
    Arrays.sort(result);

    int score = IntStream.of(result).sum();
    if (result[0] == 2 && score == bigStraightScore) {
      score = bigStraightScore;
    } else {
      score = 0;
    }
    return score;
  }

}
