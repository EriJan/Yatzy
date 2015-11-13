package enjug.erijan.games.yatzy.rules;

import enjug.erijan.games.yatzy.ScoreModel;

/**
 * Created by Jan Eriksson on 09/11/15.
 */
public class ThreePairRule implements ScoreRule {
  private final int maxVal;
  private NSameRule nSameRule;

  public ThreePairRule() {
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

    nSameRule = new NSameRule(2,secondPairVal-1);
    score = nSameRule.calculateScore(result);
    int thirdPairVal = score/2;

    if (firstPairVal*secondPairVal*thirdPairVal > 0) {
      score = 2*(firstPairVal + secondPairVal + thirdPairVal);
    } else {
      score = 0;
    }

    return score;
  }
}
