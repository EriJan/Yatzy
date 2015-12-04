package enjug.erijan.games.yatzy.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * This is a non-instantiable, non-inheritable utility class for ScoreBox calculations.
 *
 * All methods here are written in a way that with added constants, they will
 * match the ScoreRule functional interface. This makes it possible to use
 * lambda expressions to define all ScoreBoxes.
 *
 * The class is abstract, so no instantiation.
 *
 * Created by Jan Eriksson on 13/11/15.
 */
public abstract class ScoreCalculator {

  /**
   * Private constructor to avoid inheritance.
   */
  private ScoreCalculator() {}

  /**
   * Summmarize all input values. Uses IntStream to make the code compact.
   *
   * @param result The int result array.
   * @return The calculated score based on result.
   */
  public static int totalSum(int... result) {
    int score = IntStream.of(result).sum();
    return score;
  }

  /**
   *
   * If the sum of result is equal or greater than limit, score is set to bonus.
   *
   * @param limit Required sum to get bonus.
   * @param bonus The score value of the bonus,
   * @param result Result array.
   * @return Calculated score.
   */
  public static int yatzyBonus(int limit, int bonus, int... result) {
    int score = 0;
    if (totalSum(result) >= limit) {
      score = bonus;
    }
    return score;
  }

  /**
   *
   * Sum of all results of value == target number.
   *
   * @param targetNumber Value to summarize.
   * @param result Result array.
   * @return Calculated Score.
   */
  public static int sumOfNs(int targetNumber, int... result) {
    int score = IntStream.of(result).filter(val -> val == targetNumber).sum();
    return score;
  }

  /**
   *
   * This method looks for a number of equal values.
   * For convineient use with other ScoreCalculator methods, there is
   * a maxVal to limit the search.
   *
   * In Yahtzee all dice are summarized
   *
   * @param nSame The desired number of equal values.
   * @param maxVal The highest value to look for.
   * @param result Result array.
   * @return Calculated score.
   */
  public static int nSameYahtzee(int nSame, int maxVal, int... result) {

    int score = 0;

    int[] uniqueValues = IntStream.of(result).distinct().sorted().toArray();
    int i = uniqueValues.length - 1;
    boolean matchFound = false;
    while (i >= 0 && !matchFound) {
      int uniq = uniqueValues[i];
      if (uniqueValues[i] <= maxVal) {
        long noHits = IntStream.of(result).filter(val -> val == uniq).count();
        if (noHits >= nSame) {
          score = totalSum(result);
          matchFound = true;
        }
      }
      i--;
    }
    return score;
  }

  /**
   *
   * This method looks for a number of equal values.
   * For convineient use with other ScoreCalculator methods, there is
   * a maxVal to limit the search.
   *
   * @param nSame The desired number of equal values.
   * @param maxVal The highest value to look for.
   * @param result Result array.
   * @return Calculated score.
   */
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

  /**
   *
   * If all values are the same, return scoreVal as score.
   *
   * @param scoreVal The score value for a yatzy.
   * @param result Result array.
   * @return Calculated score.
   */
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

  /**
   * Full house in Yahtzee is always worth 25 points.
   *
   * @param result Result array.
   * @return Calculated score.
   */
  public static int fullHouseYahtzee(int... result) {

    int score = nSame(3, 6, result);
    int firstPairVal = score/3;

    result = IntStream.of(result).filter(val -> val != firstPairVal).toArray();
    score = nSame(2, 6, result);
    int secondPairVal = score/2;

    if (firstPairVal*secondPairVal > 0) {
      score = 25;
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

  /**
   * Villa is two times three of same.
   *
   * @param result Result array.
   * @return Calculated score.
   */

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

  /**
   * Tower is four of same and a pair.
   *
   * @param result Result array.
   * @return Result array.
   */
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

  /**
   * Small straight in Yahtzee is four in a row, worth 30 points.
   *
   * @param result Result array.
   * @return Calculated score.
   */
  public static int smallStraightYahtzee(int... result) {

    Arrays.sort(result);

    int[] uniqueValues = IntStream.of(result).distinct().sorted().toArray();
    long noUniqVals =  IntStream.of(uniqueValues).distinct().count();

    int score = 0;

    if (noUniqVals >= 4) {

      ArrayList<int[]> matchArrays = new ArrayList<>();
      matchArrays.add(new int[] {1, 2, 3, 4});
      matchArrays.add(new int[] {2, 3, 4, 5});
      matchArrays.add(new int[] {3, 4, 5, 6});

      ArrayList<int[]> resultSubArrays = new ArrayList<>();
      for (int i = 0; i < noUniqVals - 3; i++) {
        int[] testArr = Arrays.copyOfRange(uniqueValues, i, i + 4);
        resultSubArrays.add(testArr);
      }

      for (int[] arr : resultSubArrays) {
        for (int[] match : matchArrays) {
          if (Arrays.equals(arr, match)) {
            score = 30;
          }
        }
      }
    }
    return score;
  }

  /**
   * Big stright in Yahtzee is five in any sequence. Always yields a score of 40 points.
   *
   * @param result Result array.
   * @return Calculated score.
   */

  public static int bigStraightYahtzee(int... result) {

    Arrays.sort(result);

    int[] uniqueValues = IntStream.of(result).distinct().sorted().toArray();
    long noUniqVals =  IntStream.of(uniqueValues).distinct().count();
    int score = 0;

    if (noUniqVals >= 5) {
      ArrayList<int[]> matchArrays = new ArrayList<>();
      matchArrays.add(new int[]{1, 2, 3, 4, 5});
      matchArrays.add(new int[]{2, 3, 4, 5, 6});

      ArrayList<int[]> resultSubArrays = new ArrayList<>();
      for (int i = 0; i < noUniqVals - 4; i++) {
        resultSubArrays.add(Arrays.copyOfRange(uniqueValues, i, i + 5));
      }

      for (int[] arr : resultSubArrays) {
        for (int[] match : matchArrays) {
          if (Arrays.equals(arr, match)) {
            score = 40;
          }

        }
      }
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
