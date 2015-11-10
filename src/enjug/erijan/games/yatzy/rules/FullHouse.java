package enjug.erijan.games.yatzy.rules;

import java.util.stream.IntStream;

/**
 * Created by Janne on 29/10/15.
 */
public class FullHouse implements ScoreRule {
  private final int maxVal;
  private NSameRule nSameRule;

  public FullHouse() {
    this.maxVal = 6;
  }

  @Override
  public int calculateScore(int... result) {


    nSameRule = new NSameRule(3);
    int score = nSameRule.calculateScore(result);
    int firstPairVal = score/3;

    result = IntStream.of(result).filter(val -> val != firstPairVal).toArray();
    nSameRule = new NSameRule(2);
    score = nSameRule.calculateScore(result);
    int secondPairVal = score/2;

    if (firstPairVal*secondPairVal > 0) {
      score = 3*firstPairVal + 2*secondPairVal;
    } else {
      score = 0;
    }

    return score;
  }
}
