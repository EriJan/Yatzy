package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;

/**
 * Created by Janne on 27/10/15.
 */
public abstract class AbstractYatzyAgent implements ScoreObserver {
  public abstract void executeTurn(DiceHandler dice);
}
