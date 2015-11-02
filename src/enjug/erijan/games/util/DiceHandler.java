package enjug.erijan.games.util;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Janne on 27/10/15.
 */
public interface DiceHandler {
  void rollDice(List<GameDie> activeDice);
  void rollAllDice();
  Iterator<GameDie> getDice();
}
