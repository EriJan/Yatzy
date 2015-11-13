package enjug.erijan.games.yatzy.rules;

import java.util.stream.IntStream;

/**
 * Created by Jan Eriksson on 13/11/15.
 */
public abstract class YatzyRuleBook {

  public static int TotalSum(int... result) {
    int score = IntStream.of(result).sum();
    return score;
  }

  public static int YatzyBonusRule(int... result) {
    int limit = 63;
    int bonus = 50;
    int score = 0;
    if (TotalSum(result) >= limit) {
      score = bonus;
    }
    return score;
  }

  public static int YatzyBonusRule(int limit, int bonus, int... result) {
    int score = 0;
    if (TotalSum(result) >= limit) {
      score = bonus;
    }
    return score;
  }
}
