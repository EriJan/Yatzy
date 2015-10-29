package enjug.erijan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Janne on 27/10/15.
 */
public class FiveD6Handler implements DiceHandler {

  List<GameDie> dice;
  DieTypes dieType;

  public FiveD6Handler() {
    dice = new ArrayList<GameDie>();
    dieType = DieTypes.D8;
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
