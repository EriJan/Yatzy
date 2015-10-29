package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.ScoreBox;
import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Janne on 27/10/15.
 */

public class ScoreColumn implements ScoreKeeper {

  Map scoreBoxMap;
  private YatzyBoxTypes[] sumRange;
  private YatzyBoxTypes[] totalRange;


  ScoreColumn() {
    scoreBoxMap = new EnumMap<YatzyBoxTypes,ScoreBox>(YatzyBoxTypes.class);
    for(YatzyBoxTypes box : YatzyBoxTypes.values()) {
      scoreBoxMap.put(box,box.createScoreBox());
    }

    sumRange = new YatzyBoxTypes[] {
        YatzyBoxTypes.ONES,
        YatzyBoxTypes.TWOS,
        YatzyBoxTypes.THREES,
        YatzyBoxTypes.FOURS,
        YatzyBoxTypes.FIVES,
        YatzyBoxTypes.SIXES};

    totalRange = new YatzyBoxTypes[] {
        YatzyBoxTypes.SUM,
        YatzyBoxTypes.ONE_PAIR,
        YatzyBoxTypes.TWO_PAIR,
        YatzyBoxTypes.THREE_OF_SAME,
        YatzyBoxTypes.FOUR_OF_SAME,
        YatzyBoxTypes.FULL_HOUSE,
        YatzyBoxTypes.SMALL_STRAIGHT
        YatzyBoxTypes.BIG_STRAIGHT,
        YatzyBoxTypes.YATZY,
        YatzyBoxTypes.CHANCE
    };
  }

  @Override
  public void enterResult(Enum scoreBox, int... result) {
    //YatzyBoxTypes boxKey = (YatzyBoxTypes) scoreBox;
    ScoreBox localBox = (ScoreBox) scoreBoxMap.get(scoreBox);
    localBox.setScore(result);
  }

  private void setSum() {

  }

  @Override
  public int getScore(Enum scoreBox) {
    ScoreBox localBox = (ScoreBox) scoreBoxMap.get(scoreBox);
    return localBox.getScore();
  }

  @Override
  public String toString() {
    String retString = "";

    for(YatzyBoxTypes box : YatzyBoxTypes.values()) {
      ScoreBox localBox = (ScoreBox) scoreBoxMap.get(box);

      retString += box.toString() + " " + localBox.getScore() + " \n";

    }
    return retString;
  }
}
