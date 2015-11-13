package enjug.erijan.games.yatzy.rules;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Created by Jan Eriksson on 29/10/15.
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
    //int highest = 0;
    int score = 0;

    int[] uniqueValues = IntStream.of(result).distinct().sorted().toArray();
    int i = uniqueValues.length - 1;
    boolean matchFound = false;
    while (i >= 0 && !matchFound) {
      int uniq = uniqueValues[i];
      if (uniqueValues[i] <= maxVal) {
        long noHits = IntStream.of(result).filter(val -> val == uniq).count();
        if (noHits >= nSame) {
          score = IntStream.of(result).filter(val -> val == uniq).sum();
          score = uniq*nSame;
          matchFound = true;
        }
      }
      i--;
    }
    return score;
  }
}
