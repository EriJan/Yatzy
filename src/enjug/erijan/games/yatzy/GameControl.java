package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.GameDie;

/**
 * Created by Jan Eriksson on 30/11/15.
 */
public interface GameControl {
  void toggleActiveDie(GameDie die);

  void setScore(String boxId);

  void rollActiveDice();
}
