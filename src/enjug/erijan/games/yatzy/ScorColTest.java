package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;

/**
 * Created by Janne on 29/10/15.
 */
public class ScorColTest {
  public static void main(String[] args) {
    YatzyBoxTypes boxTypes = YatzyBoxTypes.ONES;
    ScoreColumn tstCol = new ScoreColumn();
    tstCol.setupScoring(boxTypes);
    System.out.println(tstCol.toString());
  }
}
