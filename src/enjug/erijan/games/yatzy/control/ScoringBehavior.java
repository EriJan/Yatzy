package enjug.erijan.games.yatzy.control;

/**
 * Score selection behavior for use with Yatzy games.
 *
 * Created by Jan Eriksson on 16/11/15.
 */
public interface ScoringBehavior {
  /**
   * Set the score and trigger related actions for a given
   * score box.
   *
   * @param boxId Score box to set
   */
  void setScore(String boxId);

  /**
   * Usuallay triggerd after dice roll to show player what results from a specifc dice combination.
   *
   */
  void setTempScores();

  /**
   * Evaluate and take actions if the game has ended.
   */
  void evaluateGameEnd();
}