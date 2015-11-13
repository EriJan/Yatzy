package enjug.erijan.games.yatzy.rules;

import java.util.Arrays;
import java.util.stream.IntStream;

/** Big straight rule.
 * Created by Jan Eriksson on 29/10/15.
 */
public class BigStraightRule implements ScoreRule {

  private final int bigStraightScore;

  public BigStraightRule() {
    bigStraightScore = IntStream.of(2, 3, 4, 5,6).sum();
  }

  @Override
  public int calculateScore(int... result) {
    Arrays.sort(result);
    long uniqueValues = IntStream.of(result).distinct().sorted().count();
    int score;
    if (result[0] == 2 && uniqueValues == 5) {
      score = bigStraightScore;
    } else {
      score = 0;
    }
    return score;
  }

}
