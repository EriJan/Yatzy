package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.GameDie;

/**
 * Takes actions from GUI and modifies model accordingdly.
 *
 * Game controller interface for any kind of Yatzy game.
 * It is a spider in the web functionality. UI input triggers actions
 * in the controller which modifies the model.
 *
 * Created by Jan Eriksson on 30/11/15.
 */
public interface GameControl {
  /**
   * Used to set the behaviour for score selection.
   *
   * @param scoringBehavior A reference to a object implementing the ScoringBehaviour interface.
   */
  void setScoringBehavior(ScoringBehavior scoringBehavior);

  /**
   * Setter for the behavior for dice rolling.
   *
   * @param rollBehavior A reference to an object implementing the RollBehaviour interface.
   */
  void setRollBehavior(RollBehavior rollBehavior);

  /**
   * Select or unselelect dice to be saved between die rolls.
   *
    * @param die The die to be selected/unselected.
   */
  void toggleActiveDie(GameDie die);

  /**
   * Method called when player wants to select score.
   *
   * @param boxId The specific id for the score box to assign result to.
   */
  void setScore(String boxId);

  /**
   * Roll the dice flagged as active.
   */

  void rollActiveDice();
}
