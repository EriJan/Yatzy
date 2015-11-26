package enjug.erijan.games.yatzy.rules;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Jan Eriksson on 13/11/15.
 */
public abstract class ScoreCalculator {

  private ScoreCalculator() {}

  public static int totalSum(int... result) {
    int score = IntStream.of(result).sum();
    return score;
  }

  public static int yatzyBonus(int limit, int bonus, int... result) {
    int score = 0;
    if (totalSum(result) >= limit) {
      score = bonus;
    }
    return score;
  }

  public static int sumOfNs(int targetNumber, int... result) {
    int score = IntStream.of(result)
        .filter(val -> val == targetNumber).sum();
    return score;
  }

  public static int nSame(int nSame, int maxVal, int... result) {

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

  public static int yatzy(int scoreVal, int... result) {
    int score = 0;
    long uniqueValues = IntStream.of(result).distinct().count();
    if (uniqueValues == 1 && result.length > 1) {
      score = scoreVal;
    }
    return score;
  }

  public static int twoPair(int... result) {

    int score = nSame(2, 6, result);
    int firstPairVal = score/2;
    score = nSame(2, firstPairVal - 1, result);
    int secondPairVal = score/2;

    if (firstPairVal*secondPairVal > 0) {
      score = 2*(firstPairVal + secondPairVal);
    } else {
      score = 0;
    }

    return score;
  }

  public static int fullHouse(int... result) {

    int score = nSame(3, 6, result);
    int firstPairVal = score/3;

    result = IntStream.of(result).filter(val -> val != firstPairVal).toArray();
    score = nSame(2, 6, result);
    int secondPairVal = score/2;

    if (firstPairVal*secondPairVal > 0) {
      score = 3*firstPairVal + 2*secondPairVal;
    } else {
      score = 0;
    }

    return score;
  }

  public static int villa(int... result) {

    int score = nSame(3, 6, result);
    int firstPairVal = score/3;

    result = IntStream.of(result).filter(val -> val != firstPairVal).toArray();
    score = nSame(3, 6, result);
    int secondPairVal = score/3;

    if (firstPairVal*secondPairVal > 0) {
      score = 3*firstPairVal + 3*secondPairVal;
    } else {
      score = 0;
    }

    return score;
  }

  public static int tower(int... result) {

    int score = nSame(4, 6, result);
    int firstMatch = score/4;

    result = IntStream.of(result).filter(val -> val != firstMatch).toArray();
    score = nSame(2, 6, result);
    int secondMatch = score/2;

    if (firstMatch*secondMatch > 0) {
      score = 4*firstMatch + 2*secondMatch;
    } else {
      score = 0;
    }

    return score;
  }

  public static int smallStraight(int... result) {

    Arrays.sort(result);

    int[] uniqueValues = IntStream.of(result).distinct().sorted().toArray();
    long noUniqVals =  IntStream.of(uniqueValues).distinct().count();
    int score = 0;
    if (noUniqVals >= 5) {
      if (uniqueValues[0] == 1 && uniqueValues[4] == 5) {
        score = IntStream.of(1, 2, 3, 4, 5).sum();
      }
    }
    return score;
  }

  public static int bigStraight(int... result) {

    Arrays.sort(result);

    int[] uniqueValues = IntStream.of(result).distinct().sorted().toArray();
    long noUniqVals =  IntStream.of(uniqueValues).distinct().count();
    int score = 0;
    if (noUniqVals >= 5) {
      if (uniqueValues[0] == 2 && uniqueValues[4] == 6) {
        score = IntStream.of(2,3,4,5,6).sum();
      }
    }
    return score;
  }

  public static int fullStraight(int... result) {

    Arrays.sort(result);

    int[] uniqueValues = IntStream.of(result).distinct().sorted().toArray();
    long noUniqVals =  IntStream.of(uniqueValues).distinct().count();
    int score = 0;
    if (noUniqVals >= 6) {
      score = IntStream.of(1,2,3,4,5,6).sum();
    }
    return score;
  }

  public static int threePair(int[] result) {
    int score = nSame(2, 6, result);
    int firstPairVal = score/2;
    score = nSame(2, firstPairVal - 1, result);
    int secondPairVal = score/2;
    score = nSame(2, secondPairVal - 1, result);
    int thirdPairVal = score/2;

    if (firstPairVal*secondPairVal*thirdPairVal > 0) {
      score = 2*(firstPairVal + secondPairVal + thirdPairVal);
    } else {
      score = 0;
    }

    return score;
  }
}
