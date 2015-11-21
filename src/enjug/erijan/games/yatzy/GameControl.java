package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;

import javax.swing.*;
import java.util.Iterator;

/**
 * Created by Jan Eriksson on 27/10/15.
 */
public interface GameControl {

  void newGame();
  void newGame(JFrame oldFrame);
  public String rollActiveDice();
  void toggleActiveDie(GameDie die);
  String setScore(Enum targetBox);
  int rollsLeft();
  void yourTurn(DiceHandler dice);
  ScoreColumn getActiveScoreColumn();
  Iterator getScoreColumns();
  void setTempScore();
  String getWinner();

}
