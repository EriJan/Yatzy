package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.DieTypes;
import enjug.erijan.games.util.GameDie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Janne on 27/10/15.
 */

public class YatzyDice implements DiceHandler {

  List<GameDie> dice;
  DieTypes dieType;

  public YatzyDice() {
    dice = new ArrayList<GameDie>();
    dieType = DieTypes.D6;
    for (int i = 0; i < 5; i++) {
      //dice.add(DieTypes.D6.create());
      dice.add(dieType.create());
    }
  }

  @Override
  public void rollDice(List<GameDie> activeDice) {
    for (GameDie d : activeDice) {
      d.rollDie();
    }
  }

  @Override
  public void rollAllDice() {
    for (GameDie d : dice) {
      d.rollDie();
    }
  }

  @Override
  public String toString() {
    String tmpStr = "";
    for (GameDie d : dice) {
      tmpStr += d.getSideUp() + " ";
    }
    return tmpStr;
  }

  @Override
  public List<GameDie> getDice() {

    return dice;
  }
}
