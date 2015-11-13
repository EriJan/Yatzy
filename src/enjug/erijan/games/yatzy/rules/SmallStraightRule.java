package enjug.erijan.games.yatzy.rules;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Jan Eriksson on 29/10/15.
 */
public class SmallStraightRule implements ScoreRule {

  private final int smallStraightScore;

  public SmallStraightRule() {
    smallStraightScore = IntStream.of(1,2,3,4,5).sum();
  }

  @Override
  public int calculateScore(int... result) {
    Arrays.sort(result);
    long uniqueValues = IntStream.of(result).distinct().sorted().count();
    int score;
    if (result[0] == 1 && uniqueValues == 5) {
      score = smallStraightScore;
    } else {
      score = 0;
    }
    return score;
  }
}
