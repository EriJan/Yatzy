package enjug.erijan.games.util;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Janne on 27/10/15.
 * This interface is supposed to handle
 * a collection of gamedice, this is both a controller and a model
 *
 */

public interface DiceHandler extends DiceObservable {

  /**
   * Roll a subset of the dice.
   */
  void rollActiveDice();

  void toggleActiveDie(GameDie die);

  /**
   * Set alla dice to be active
   */
  void setAllDiceActive();


  /**
   * Query of a specific die references is active.
   * @param die A GameDie reference in the
   * @return
   */
  boolean isActiveDie(GameDie die);


  /**
   *
   */
  void rollAllDice();


  /**
   * @return
   */
  Iterator<GameDie> getDice();


  /**
   * @return
   */
  int[] getValues();
}
