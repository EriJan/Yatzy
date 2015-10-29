package enjug.erijan;

import enjug.erijan.games.util.GameDie;

import java.util.List;

/**
 * Created by Janne on 27/10/15.
 */
public interface DiceHandler {
  void rollDice(List<GameDie> activeDice);
  void rollAllDice();
  List<GameDie> getDice();
}
