package enjug.erijan.games.yatzy.rules;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Jan Eriksson on 13/11/15.
 */
public abstract class YatzyRuleBook {

  public static int TotalSum(int... result) {
    int score = IntStream.of(result).sum();
    return score;
  }

  public static int YatzyBonus(int limit, int bonus, int... result) {
    int score = 0;
    if (TotalSum(result) >= limit) {
      score = bonus;
    }
    return score;
  }

  public static int SumOfNs(int targetNumber, int... result) {
    int score = IntStream.of(result)
        .filter(val -> val == targetNumber).sum();
    return score;
  }

  public static int NSame(int nSame, int maxVal, int... result) {

    int score = 0;

    int[] uniqueValues = IntStream.of(result).distinct().sorted().toArray();
    int i = uniqueValues.length - 1;
    boolean matchFound = false;
    while (i >= 0 && !matchFound) {
      int uniq = uniqueValues[i];
      if (uniqueValues[i] <= maxVal) {
        long noHits = IntStream.of(result).filter(val -> val == uniq).count();
        if (noHits >= nSame) {
          score = uniq*nSame;
          matchFound = true;
        }
      }
      i--;
    }
    return score;
  }

  public static int TwoPair(int... result) {

    int score = NSame(2, 6, result);
    int firstPairVal = score/2;
    score = NSame(2, firstPairVal = 1, result);
    int secondPairVal = score/2;

    if (firstPairVal*secondPairVal > 0) {
      score = 2*(firstPairVal + secondPairVal);
    } else {
      score = 0;
    }

    return score;
  }

  public static int FullHouse(int... result) {

    int score = NSame(3, 6, result);
    int firstPairVal = score/3;

    result = IntStream.of(result).filter(val -> val != firstPairVal).toArray();
    score = NSame(2, 6, result);
    int secondPairVal = score/2;

    if (firstPairVal*secondPairVal > 0) {
      score = 3*firstPairVal + 2*secondPairVal;
    } else {
      score = 0;
    }

    return score;
  }

  public static int SmallStraight(int... result) {

    Arrays.sort(result);

    int[] uniqueValues = IntStream.of(result).distinct().sorted().toArray();
    long noUniqVals =  IntStream.of(uniqueValues).distinct().count();
    int score;
    if (uniqueValues[0] == 1 && uniqueValues[4] == 5 && noUniqVals >= 5) {
      score = IntStream.of(1,2,3,4,5).sum();
    } else {
      score = 0;
    }
    return score;
  }
  public static int BigStraight(int... result) {

    Arrays.sort(result);

    int[] uniqueValues = IntStream.of(result).distinct().sorted().toArray();
    long noUniqVals =  IntStream.of(uniqueValues).distinct().count();
    int score;
    if (uniqueValues[0] == 2 && uniqueValues[4] == 6 && noUniqVals >= 5) {
      score = IntStream.of(2,3,4,5,6).sum();
    } else {
      score = 0;
    }
    return score;
  }
}
