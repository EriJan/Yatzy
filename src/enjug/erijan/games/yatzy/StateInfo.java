package enjug.erijan.games.yatzy;

import java.util.List;

/**
 * Created by Jan Eriksson on 20/11/15.
 */
public interface StateInfo extends StateInfoSubject {
  boolean isRollingAllowed();

  boolean isScoringAllowed();

  String getStateMessage();
  void setStateMessage(String stateMessage);
  Player getCurrentPlayer();

  List<String> getAvailableScores();

  int getScore(String boxId);

  int getTempScore(String boxId);
}
