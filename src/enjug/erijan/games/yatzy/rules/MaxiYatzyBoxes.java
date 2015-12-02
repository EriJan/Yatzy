package enjug.erijan.games.yatzy.rules;

/**
 * Defines all Maxi Yatzy ScoreRules as enum values.
 * Every enum value is a ScoreRule, defined with the use of lambda expressions
 * in the constructor. When getScoreBox is called, a new ScoreBox with
 * the corresponding ScoreRule will be created.
 *
 * For example the rule for ONES will always be sum of ones etc.
 *
 * Created by Jan Eriksson on 27/10/15.
 */
public enum MaxiYatzyBoxes implements ScoreRule, ScoreBoxFactory {
  ONES (result -> ScoreCalculator.sumOfNs(1, result)),
  TWOS (result -> ScoreCalculator.sumOfNs(2, result)),
  THREES (result -> ScoreCalculator.sumOfNs(3, result)),
  FOURS (result -> ScoreCalculator.sumOfNs(4, result)),
  FIVES (result -> ScoreCalculator.sumOfNs(5, result)),
  SIXES (result -> ScoreCalculator.sumOfNs(6, result)),
  SUM (result -> ScoreCalculator.totalSum(result)),
  BONUS (result -> ScoreCalculator.yatzyBonus(84, 100, result)),
  ONE_PAIR (result -> ScoreCalculator.nSame(2, 6, result)),
  TWO_PAIR (result -> ScoreCalculator.twoPair(result)),
  THREE_PAIR (result -> ScoreCalculator.threePair(result)),
  THREE_OF_SAME (result -> ScoreCalculator.nSame(3, 6, result)),
  FOUR_OF_SAME (result -> ScoreCalculator.nSame(4, 6, result)),
  FIVE_OF_SAME (result -> ScoreCalculator.nSame(5, 6, result)),
  FULL_HOUSE (result -> ScoreCalculator.fullHouse(result)),
  TOWER (result -> ScoreCalculator.tower(result)),
  VILLA (result -> ScoreCalculator.villa(result)),
  SMALL_STRAIGHT (result -> ScoreCalculator.smallStraight(result)),
  BIG_STRAIGHT (result -> ScoreCalculator.bigStraight(result)),
  FULL_STRAIGHT (result -> ScoreCalculator.fullStraight(result)),
  CHANCE (result -> ScoreCalculator.totalSum(result)),
  YATZY (result -> ScoreCalculator.yatzy(100,result)),
  TOTAL (result -> ScoreCalculator.totalSum(result));

  private final ScoreRule scoreRule;

  MaxiYatzyBoxes(ScoreRule scoreRule) {
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
      case SIXES : string = "TwSixesos";
        break;
      case SUM : string = "Sum";
        break;
      case BONUS : string = "Bonus";
        break;
      case ONE_PAIR : string = "One Pair";
        break;
      case TWO_PAIR : string = "Two Pairs";
        break;
      case THREE_PAIR : string = "Three Pairs";
        break;
      case THREE_OF_SAME : string = "Three of a Kind";
        break;
      case FOUR_OF_SAME : string = "Four of a kind";
        break;
      case FIVE_OF_SAME : string = "Five of a kind";
        break;
      case FULL_HOUSE : string = "Full house";
        break;
      case TOWER : string = "Tower";
        break;
      case VILLA : string = "Villa";
        break;
      case SMALL_STRAIGHT : string = "Small Straight";
        break;
      case BIG_STRAIGHT : string = "Big Straight";
        break;
      case FULL_STRAIGHT : string = "Full Straight";
        break;
      case CHANCE : string = "Chance";
        break;
      case YATZY : string = "Yatzy";
        break;
      case TOTAL : string = "Total";
        break;
      default: string = this.name();
        break;
    }

    return string;
  }
}
