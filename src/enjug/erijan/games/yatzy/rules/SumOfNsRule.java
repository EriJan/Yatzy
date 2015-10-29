package enjug.erijan.games.yatzy.rules;

/**
 * Created by Janne on 29/10/15.
 */
public class SumOfNsRule implements ScoreRule {

  private final int targetNumber;

  public SumOfNsRule(int targetNumber) {
    this.targetNumber = targetNumber;
  }

  @Override
  public int calculateScore(int... result) {
    int score = 0;
    for(int i : result) {
      if (i == targetNumber) {
        score += i;
      }
    }
    return score;
  }
}
