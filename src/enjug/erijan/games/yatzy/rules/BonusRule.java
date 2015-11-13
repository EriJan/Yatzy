package enjug.erijan.games.yatzy.rules;

/**
 * Created by Jan Eriksson on 29/10/15.
 */
public class BonusRule implements ScoreRule {

  private final int limit;
  private final int bonus;
  BonusRule(int limit, int bonus) {
    this.limit = limit;
    this.bonus = bonus;
  }

  @Override
  public int calculateScore(int... result) {
    int score = 0;
    TotalSumRule helpRule = new TotalSumRule();
    if (helpRule.calculateScore(result) >= limit) {
      score = bonus;
    }
    return score;
  }
}
