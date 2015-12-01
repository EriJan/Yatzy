package enjug.erijan.games.yatzy.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Jan Eriksson on 30/11/15.
 */
public interface ScoreInterface extends ScoreSubject {
  void addPlayer(String playerName, HashMap scoreColumn);

  void setScore(String playerName, String boxId, int... result);

  int getScore(String playerName, String boxId);

  List<String> getAvailableScores(String playerName);

  List<String> getAllScores();

  void setTempScores(String playerName, int... result);

  int getTempScore(String playerName, String scoreBox);

  void clearTempScores(String playerName);

  int getTotal(String playerName);

  boolean isAllScoreSet(String playerName);

  boolean isScoreSet(String playerName, String scoreBox);

  boolean isDerivedScore(String boxId);

  String getWinner();
}
