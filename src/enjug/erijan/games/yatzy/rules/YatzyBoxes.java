package enjug.erijan.games.yatzy.rules;

import enjug.erijan.games.yatzy.RulesetFactory;

/**
 * Created by Jan Eriksson on 27/10/15.
 *
 */

public enum YatzyBoxes implements ScoreRule, ScoreBoxFactory {
  ONES (result -> ScoreCalculator.sumOfNs(1, result)),
  TWOS (result -> ScoreCalculator.sumOfNs(2, result)),
  THREES (result -> ScoreCalculator.sumOfNs(3, result)),
  FOURS (result -> ScoreCalculator.sumOfNs(4, result)),
  FIVES (result -> ScoreCalculator.sumOfNs(5, result)),
  SIXES (result -> ScoreCalculator.sumOfNs(6, result)),
  SUM (result -> ScoreCalculator.totalSum(result)),
  BONUS (result -> ScoreCalculator.yatzyBonus(63, 50, result)),
  ONE_PAIR (result -> ScoreCalculator.nSame(2, 6, result)),
  TWO_PAIR (result -> ScoreCalculator.twoPair(result)),
  THREE_OF_SAME (result -> ScoreCalculator.nSame(3, 6, result)),
  FOUR_OF_SAME (result -> ScoreCalculator.nSame(4, 6, result)),
  FULL_HOUSE (result -> ScoreCalculator.fullHouse(result)),
  SMALL_STRAIGHT (result -> ScoreCalculator.smallStraight(result)),
  BIG_STRAIGHT (result -> ScoreCalculator.bigStraight(result)),
  CHANCE (result -> ScoreCalculator.totalSum(result)),
  YATZY (result -> ScoreCalculator.nSame(5, 6, result)),
  TOTAL (result -> ScoreCalculator.totalSum(result));

  private static final RulesetFactory yatzyVariant = RulesetFactory.YATZY;

  private final ScoreRule scoreRule;

  YatzyBoxes(ScoreRule scoreRule) {
    this.scoreRule = scoreRule;
  }

  public static RulesetFactory getYatzyVariant() {
    return yatzyVariant;
  }

  @Override
  public int calculateScore(int... result) {
    return scoreRule.calculateScore(result);
  }

  @Override
  public ScoreBox getScoreBox() {
    return new ScoreBox(this.name(),this);
  }


}
