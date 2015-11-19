package enjug.erijan.games.yatzy.rules;

/** Holds score calculated from a rule.
 * ScoreBox class has a scoreRule which is called when
 * score is set. There is also a temp score for intermediate result
 * analysis. The score Rule is set on construction of the box object.
 * The object is aware if its score is set.
 * Created by Jan Eriksson on 27/10/15.
 */

public class ScoreBox {

  private ScoreRule scoreRule;
  private int score;
  private int tempScore;
  private boolean scoreSet;
  private boolean derivedScore;
  /**
   *
   * @param rule The rule defines the score calculation.
   */
  public ScoreBox (ScoreRule rule) {
    scoreRule = rule;
    score = 0;
    tempScore = 0;
    scoreSet = false;
    derivedScore = false;
  }

  /** Calculate score from result.
   *
   * Take a result in the form of an varibale int array
   * calculate score from scoreRule.
   *
   * @param result Result input as varargs int.
   */
  public void setScore(int... result) {
    scoreSet = true;
    score = scoreRule.calculateScore(result);
  }

  public int getScore() {
    return score;
  }

  /** Unset the scoreSet boolean.
   *
   */
  public void clearScore() {
    scoreSet = false;
    score = 0;
  }

  public void setTempScore(int... result) {
    tempScore = scoreRule.calculateScore(result);
  }

  public boolean isScoreSet() {
    return scoreSet;
  }

  public int getTempScore() {
    return tempScore;
  }


  public boolean isDerivedScore() {
    return derivedScore;
  }

  public void setDerivedScore(boolean derivedScore) {
    this.derivedScore = derivedScore;
  }
}
