package enjug.erijan;

/**
 * Created by Janne on 29/10/15.
 */

public class TwoPairRule implements ScoreRule {
  private final int maxVal;
  private NSameRule nSameRule;

  public TwoPairRule(int nSame) {
    this.maxVal = 6;
  }

  @Override
  public int calculateScore(int... result) {

    nSameRule = new NSameRule(2);
    int score = nSameRule.calculateScore(result);
    int firstPairVal = score/2;

    nSameRule = new NSameRule(2,firstPairVal-1);
    score = nSameRule.calculateScore(result);
    int secondPairVal = score/2;

    if (firstPairVal*secondPairVal > 0) {
      score = 2*(firstPairVal + secondPairVal);
    } else {
      score = 0;
    }

    return score;
  }
}
