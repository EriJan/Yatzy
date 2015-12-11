package enjug.erijan.games.yatzy.rules;

import enjug.erijan.games.yatzy.model.ScoreInterface;

/**
 * @author Jan Eriksson
 * @Version 1.0
 * @since 07/12/15
 */
public enum CommonBoxes implements ScoreBoxFactory, ScoreRule {
  ONES (result -> ScoreCalculator.sumOfNs(1, result)),
  TWOS (result -> ScoreCalculator.sumOfNs(2, result)),
  THREES (result -> ScoreCalculator.sumOfNs(3, result)),
  FOURS (result -> ScoreCalculator.sumOfNs(4, result)),
  FIVES (result -> ScoreCalculator.sumOfNs(5, result)),
  SIXES (result -> ScoreCalculator.sumOfNs(6, result)),
  SUM (result -> ScoreCalculator.totalSum(result));

  private final ScoreRule scoreRule;

  CommonBoxes(ScoreRule scoreRule) {
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
      default: string = this.name();
        break;
    }
    return string;
  }


}
