package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;

/**
 * Created by Janne on 29/10/15.
 */
public class ScorColTest {
  public static void main(String[] args) {
    YatzyDice dice = new YatzyDice();
    //YatzyBoxTypes boxTypes = YatzyBoxTypes.ONES;

    ScoreColumn tstCol = new ScoreColumn();

    for(YatzyBoxTypes box : YatzyBoxTypes.values()) {
      dice.rollAllDice();
      System.out.println(dice.toString());
      tstCol.enterResult(box,dice.getValues());
    }
    System.out.println(tstCol.toString());
  }
}
