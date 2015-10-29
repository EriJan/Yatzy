package enjug.erijan;

/**
 * Created by Janne on 29/10/15.
 */
public class YatzyRule implements ScoreRule {
  private final int yatzyScore;

  public YatzyRule() {
    yatzyScore = 50;
  }

  @Override
  public int calculateScore(int... result) {
    int matchVal = result[0];
    int count = 0;
    for (int j : result) {
      if (j == matchVal) {
        count++;
      }
    }
    int score = 0;
    if (count >= result.length) {
      score = yatzyScore;
    }
    return score;
  }
}
