package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;

/**
 * Created by Janne on 27/10/15.
 */
public interface YatzyAgentInterface {
  public void rollActiveDice();
  public void toggleActiveDie(GameDie die);
  public void setScore(Enum targetBox);
  public int getNoOfRoll();
  public void yourTurn(DiceHandler dice);
  public ScoreModel getScoreColumn();
}
