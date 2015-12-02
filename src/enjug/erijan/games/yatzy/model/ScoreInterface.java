package enjug.erijan.games.yatzy.model;

import enjug.erijan.games.util.GenericSubject;

import java.util.HashMap;
import java.util.List;

/**
 * Interface to a yatzy ScoreSheet.
 *
 * The assumption is to have data represented in a matrix, with player names
 * as column keys and strings representing different score boxes on the rows.
 *
 * The term derived scores is used for sum, bonus and total,
 * since they are derived from a set of other scores.
 *
 * Created by Jan Eriksson on 30/11/15.
 */
public interface ScoreInterface extends GenericSubject {
  /**
   * Add a player using the players name and a HashMap with
   * all score boxes.
   *
   * @param playerName Player name as a String.
   * @param scoreColumn ScoreBoxes in a HashMap.
   */
  void addPlayer(String playerName, HashMap scoreColumn);

  void setScore(String playerName, String boxId, int... result);

  int getScore(String playerName, String boxId);

  /**
   * Get all scores still available for a specified player.
   *
   * @param playerName Player name as String.
   * @return Return a list of the scores.
   */
  // TODO this should be set by the controller
  // TODO to allow forced yatzy behavior.
  List<String> getAvailableScores(String playerName);


  /**
   * Get a list of String representing all rows in the score sheet.
   * @return List of Strings.
   */

  List<String> getAllScores();

  void setTempScores(String playerName, int... result);

  int getTempScore(String playerName, String scoreBox);

  void clearTempScores(String playerName);

  /**
   * Get the total score for specified player.
   * @param playerName Player name as String.
   * @return Total score as int.
   */
  int getTotal(String playerName);

  boolean isAllScoreSet(String playerName);

  boolean isScoreSet(String playerName, String scoreBox);

  boolean isDerivedScore(String boxId);

  /**
   * Calculate and return name of the winner.
   * @return Winning player name as String.
   */
  String getWinner();
}
