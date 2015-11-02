package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;

/**
 * Created by Janne on 29/10/15.
 */
public class ScorColTest {
  public static void main(String[] args) {
    YatzyDice dice = new YatzyDice();
    //YatzyBoxTypes boxTypes = YatzyBoxTypes.ONES;

    YatzyScoreModel tstCol = new YatzyScoreModel();
    for (int i = 0; i < 10; i++) {
      dice.rollAllDice();
      System.out.println(dice.toString());
      for(YatzyBoxTypes box : YatzyBoxTypes.values()) {
        tstCol.enterResult(box,dice.getValues());
      }
      System.out.println(tstCol.toString());
    }
  }
}