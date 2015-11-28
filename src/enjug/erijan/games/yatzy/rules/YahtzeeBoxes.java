package enjug.erijan.games.yatzy.rules;

import enjug.erijan.games.yatzy.RulesetFactory;

/**
 * Created by Jan Eriksson on 27/10/15.
 *
 */

public enum YahtzeeBoxes implements ScoreRule, ScoreBoxFactory {
  ONES (result -> ScoreCalculator.sumOfNs(1, result)),
  TWOS (result -> ScoreCalculator.sumOfNs(2, result)),
  THREES (result -> ScoreCalculator.sumOfNs(3, result)),
  FOURS (result -> ScoreCalculator.sumOfNs(4, result)),
  FIVES (result -> ScoreCalculator.sumOfNs(5, result)),
  SIXES (result -> ScoreCalculator.sumOfNs(6, result)),
  SUM (result -> ScoreCalculator.totalSum(result)),
  BONUS (result -> ScoreCalculator.yatzyBonus(63, 35, result)),
  THREE_OF_SAME (result -> ScoreCalculator.nSameYahtzee(3, 6, result)),
  FOUR_OF_SAME (result -> ScoreCalculator.nSameYahtzee(4, 6, result)),
  FULL_HOUSE (result -> ScoreCalculator.fullHouseYahtzee(result)),
  SMALL_STRAIGHT (result -> ScoreCalculator.smallStraightYahtzee(result)),
  BIG_STRAIGHT (result -> ScoreCalculator.bigStraightYahtzee(result)),
  CHANCE (result -> ScoreCalculator.totalSum(result)),
  YATZY (result -> ScoreCalculator.nSame(5, 6, result)),
  TOTAL (result -> ScoreCalculator.totalSum(result));

  private static final RulesetFactory yatzyVariant = RulesetFactory.YATZY;

  private final ScoreRule scoreRule;

  YahtzeeBoxes(ScoreRule scoreRule) {
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
