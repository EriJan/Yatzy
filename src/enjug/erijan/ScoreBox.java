package enjug.erijan;

/**
 * Created by Janne on 27/10/15.
 */
public class ScoreBox {
  private ScoreRule scoreRule;
  private int score;
  public ScoreBox (ScoreRule rule) {
    scoreRule = rule;
    score = 0;
  }


  public void calculateScore(int... result) {
    score = scoreRule.calculateScore(result);
  }


  public int getScore() {
    return score;
  }
}
