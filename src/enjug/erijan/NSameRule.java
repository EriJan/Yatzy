package enjug.erijan;
/**
 * Created by Janne on 29/10/15.
 */

public class NSameRule implements ScoreRule {
  private final int nSame;
  private final int maxVal;
  public NSameRule(int nSame) {
    this.nSame = nSame;
    this.maxVal = 6;
  }

  public NSameRule(int nSame, int maxVal) {
    this.nSame = nSame;
    this.maxVal = maxVal;
  }

  @Override
  public int calculateScore(int... result) {
    int highest = 0;
    for (int i = maxVal; i >= 1; i--) {
      int count = 0;
      for (int j : result) {
        if (j == i) {
          count++;
        }
      }
      if (count >= nSame || highest < i) {
        highest = i;
      }
    }

    int score = nSame*highest;
    return score;
  }
}
