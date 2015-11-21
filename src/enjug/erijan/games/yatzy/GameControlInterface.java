package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Jan Eriksson on 27/10/15.
 */
public interface GameControlInterface {

  void newGame();
  void newGame(JFrame oldFrame);
  public String rollActiveDice();
  void toggleActiveDie(GameDie die);
  String setScore(Enum targetBox);
  int rollsLeft();
  void yourTurn(DiceHandler dice);
  ScoreModel getActiveScoreColumn();
  Iterator getScoreColumns();
  void setTempScore();
  String getWinner();

}
