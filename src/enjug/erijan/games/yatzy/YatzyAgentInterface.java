package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;

/**
 * Created by Janne on 27/10/15.
 */
public interface YatzyAgentInterface {
  public void rollDice();
  public void setScore(Enum targetBox, int... result);
  public int getNoOfRoll();
  public void executeTurn();
}
