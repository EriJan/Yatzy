package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.GameDie;

/**
 * Roll behavior interface for use eith Yatzy and variants.
 *
 * Created by Jan Eriksson on 16/11/15.
 */
public interface RollBehavior {
  /**
   * Roll dice not set to be saved by the player.
   */
  void rollActiveDice();

  /**
   * Prepare dice for next player.
   */
  void resetDice();

  /**
   * Saves/unsaves dice for the player for next roll.
   * @param die Specific die to activate/deactivate.
   */
  void toggleActiveDie(GameDie die);
}
