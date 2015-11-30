package enjug.erijan.games.yatzy.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Jan Eriksson on 30/11/15.
 */
public interface ScoreInterface {
  void addPlayer(String playerName, HashMap scoreColumn);

  void setScore(String playerName, String boxId, int... result);

  void setTempScores(String playerName, List<String> availableScores, int... result);

  void clearTempScores(String playerName);

  int getScore(String playerName, String boxId);

  int getTotal(String playerName);

  int getTempScore(String playerName, String scoreBox);

  boolean isAllScoreSet(String playerName);

  boolean isScoreSet(String playerName, String scoreBox);

  boolean isDerivedScore(String boxId);
}
