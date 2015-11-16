package enjug.erijan.games.yatzy.rules;

/**
 * Created by Jan Eriksson on 27/10/15.
 *
 */

public enum YatzyBoxTypes implements ScoreRule,ScoreBoxFactory {
  ONES (result -> YatzyRuleBook.sumOfNs(1,result)),
  TWOS (result -> YatzyRuleBook.sumOfNs(2, result)),
  THREES (result -> YatzyRuleBook.sumOfNs(3, result)),
  FOURS (result -> YatzyRuleBook.sumOfNs(4, result)),
  FIVES (result -> YatzyRuleBook.sumOfNs(5, result)),
  SIXES (result -> YatzyRuleBook.sumOfNs(6, result)),
  SUM (result -> YatzyRuleBook.totalSum(result)),
  BONUS (result -> YatzyRuleBook.yatzyBonus(63, 50, result)),
  ONE_PAIR (result -> YatzyRuleBook.nSame(2, 6, result)),
  TWO_PAIR (result -> YatzyRuleBook.twoPair(result)),
  THREE_OF_SAME (result -> YatzyRuleBook.nSame(3, 6, result)),
  FOUR_OF_SAME (result -> YatzyRuleBook.nSame(4, 6, result)),
  FULL_HOUSE (result -> YatzyRuleBook.fullHouse(result)),
  SMALL_STRAIGHT (result -> YatzyRuleBook.smallStraight(result)),
  BIG_STRAIGHT (result -> YatzyRuleBook.bigStraight(result)),
  CHANCE (result -> YatzyRuleBook.totalSum(result)),
  YATZY (result -> YatzyRuleBook.nSame(5, 6, result)),
  TOTAL (result -> YatzyRuleBook.totalSum(result));

  private static final YatzyVariants yatzyVariant = YatzyVariants.YATZY;
  private final ScoreRule scoreRule;

  private YatzyBoxTypes(ScoreRule scoreRule) {
    this.scoreRule = scoreRule;
  }

  public static YatzyVariants getYatzyVariant() {
    return yatzyVariant;
  }

  @Override
  public int calculateScore(int... result) {
    return scoreRule.calculateScore(result);
  }

  @Override
  public ScoreBox createScoreBox() {
    return new ScoreBox(this);
  }
}
