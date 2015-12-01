package enjug.erijan.games.util;

import java.util.Iterator;

/**
 *
 * This interface handle a collection of GameDice,
 * this is both a controller and a model.
 * DiceSubject interface is extended for use with DiceObserver.
 *
 * Created by Jan Eriksson on 27/10/15.
 */

public interface DiceHandler extends DiceSubject {

  /**
   * Roll a subset of the dice.
   */
  void rollActiveDice();


  /**
   * Toggle the active state of a die.
   * @param die A reference to a GameDie object that exist in the
   *            model.
   */
  void toggleActiveDie(GameDie die);

  /**
   * Set alla dice to be active
   */
  void setAllDiceActive();


  /**
   * Query of a specific die references is active.
   * @param die A GameDie reference from the die obejects handled in
   *            the DiceHandler.
   * @return Returns true if die is handled as active die.
   */
  boolean isActiveDie(GameDie die);


  /**
   * Roll all dice regardless of any active state.
   */
  void rollAllDice();


  /**
   * @return Returns an iterator over all GameDie objects.
   */
  Iterator<GameDie> getDice();


  /**
   * @return Returns the face values of all GameDie objects as a list of int.
   */
  int[] getValues();

  void deActivateAllDice();
}
