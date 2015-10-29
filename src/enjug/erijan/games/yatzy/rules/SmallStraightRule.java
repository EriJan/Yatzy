package enjug.erijan.games.yatzy.rules;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Janne on 29/10/15.
 */
public class SmallStraightRule implements ScoreRule {

  private final int smallStraightScore;

  public SmallStraightRule() {
    smallStraightScore = IntStream.of(1,2,3,4,5).sum();
  }

  @Override
  public int calculateScore(int... result) {
    Arrays.sort(result);

    int score = IntStream.of(result).sum();
    if (result[0] == 1 && score == smallStraightScore) {
      score = smallStraightScore;
    } else {
      score = 0;
    }
    return score;
  }
}
