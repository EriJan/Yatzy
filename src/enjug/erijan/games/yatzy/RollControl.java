package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.GameDie;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public interface RollControl {
  void rollActiveDice(String playerName);

  void resetDice();

  int rollsLeft();
  void toggleActiveDie(GameDie die);
}
