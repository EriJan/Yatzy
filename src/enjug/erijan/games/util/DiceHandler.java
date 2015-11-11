package enjug.erijan.games.util;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Janne on 27/10/15.
 */

public interface DiceHandler extends DiceObservable {
  void rollActiveDice();
  void toggleActiveDie(GameDie die);
  void setAllDiceActive();
  boolean isActiveDie(GameDie die);
  void rollAllDice();
  Iterator<GameDie> getDice();
  int[] getValues();
}
