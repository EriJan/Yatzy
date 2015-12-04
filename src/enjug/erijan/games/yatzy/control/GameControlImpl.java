package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.GameDie;

/**
 * Main control for a Yatzy game.
 * Implementation of the GameControl interface.
 *
 * Object created by calling a static facotry method to get
 * an empty object. The empty object need to be populated with
 * Scoring and Roll behavior before use.
 *
 * Created by Jan Eriksson on 16/11/15.
 */

public class GameControlImpl implements GameControl {

  private ScoringBehavior scoringBehavior;
  private RollBehavior rollBehavior;

  /**
   * Private constructor only accessible from getEmptyGameControl
   */
  private GameControlImpl() {};

  /**
   * Static factory method. Creates an empty GameControl.
   *
   * @return Retruns an empty GameControlImpl object.
   */
  public static GameControl getEmptyGameControl() {
    return new GameControlImpl();
  }


  @Override
  public void setScoringBehavior(ScoringBehavior scoringBehavior) {
    this.scoringBehavior = scoringBehavior;
  }

  @Override
  public void setRollBehavior(RollBehavior rollBehavior) {
    this.rollBehavior = rollBehavior;
  }

  /**
   * Passes a toggle command to rollBehavior.
   * @param die The die to be selected/unselected.
   */
  @Override
  public void toggleActiveDie(GameDie die) {
    rollBehavior.toggleActiveDie(die);
  }

  /**
   * First set score through scoringBehavior, then resetDice.
   *
   * @param boxId The specific id for the score box to assign result to.
   */
  @Override
  public void setScore(String boxId) {
    scoringBehavior.setScore(boxId);
    rollBehavior.resetDice();
  }

  /**
   * Roll dice and set temporary scores for the user to evaluate result of dice roll.
   */
  @Override
  public void rollActiveDice() {
    rollBehavior.rollActiveDice();
    scoringBehavior.setTempScores();
  }


}
