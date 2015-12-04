package enjug.erijan.games.yatzy.rules;

/**
 * Factory implementation for Yahtzee.
 *
 * Defines all Yahtzee ScoreRules as enum values.
 * Every enum value is a ScoreRule, defined with the use of lambda expressions
 * in the constructor. When getScoreBox is called, a new ScoreBox with
 * the corresponding ScoreRule will be created.
 *
 * For example the rule for ONES will always be sum of ones etc.
 *
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
  YAHTZEE (result -> ScoreCalculator.nSame(5, 6, result)),
  TOTAL (result -> ScoreCalculator.totalSum(result));

  private final ScoreRule scoreRule;

  YahtzeeBoxes(ScoreRule scoreRule) {
    this.scoreRule = scoreRule;
  }

  @Override
  public int calculateScore(int... result) {
    return scoreRule.calculateScore(result);
  }

  @Override
  public ScoreBox getScoreBox() {
    return new ScoreBox(this);
  }

  @Override
  public String toString() {
    String string;

    switch (this) {
      case ONES : string = "Ones";
        break;
      case TWOS : string = "Twos";
        break;
      case THREES : string = "Threes";
        break;
      case FOURS : string = "Fours";
        break;
      case FIVES : string = "Fives";
        break;
      case SIXES : string = "Sixes";
        break;
      case SUM : string = "Sum";
        break;
      case BONUS : string = "Bonus";
        break;
      case THREE_OF_SAME : string = "Three of a Kind";
        break;
      case FOUR_OF_SAME : string = "Four of a kind";
        break;
      case FULL_HOUSE : string = "Full house";
        break;
      case SMALL_STRAIGHT : string = "Small Straight";
        break;
      case BIG_STRAIGHT : string = "Big Straight";
        break;
      case CHANCE : string = "Chance";
        break;
      case YAHTZEE : string = "Yahtzee";
        break;
      case TOTAL : string = "Total";
        break;
      default: string = this.name();
        break;
    }
    return string;
  }

}
