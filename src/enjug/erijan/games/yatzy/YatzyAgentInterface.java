package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Janne on 27/10/15.
 */
public interface YatzyAgentInterface {

public void rollActiveDice();
  void toggleActiveDie(GameDie die);
  void setScore(Enum targetBox);
  int getNoOfRoll();
  void yourTurn(DiceHandler dice);
  ScoreModel getActiveScoreColumn();
  Iterator getScoreColumns();
}
