package enjug.erijan.games.yatzy.rules;

/**
 * Created by Janne on 27/10/15.
 */
public class ScoreBox {
  private ScoreRule scoreRule;
  private int score;
  private int tempScore;
  private boolean scoreSet;

  public ScoreBox (ScoreRule rule) {
    scoreRule = rule;
    score = 0;
    tempScore = 0;
    scoreSet = false;
  }

  public void setScore(int... result) {
    scoreSet = true;
    score = scoreRule.calculateScore(result);
  }

  public void setTempScore(int... result) {
    tempScore = scoreRule.calculateScore(result);
  }

  public boolean isScoreSet() {
    return scoreSet;
  }

  public int getScore() {
    return score;
  }

  public int getTempScore() {
    return tempScore;
  }
}
