package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.GameDie;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public interface RollBehavior {
  void rollActiveDice();
  void resetDice();
  void toggleActiveDie(GameDie die);
}
