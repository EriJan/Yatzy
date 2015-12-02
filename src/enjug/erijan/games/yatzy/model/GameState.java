package enjug.erijan.games.yatzy.model;

import enjug.erijan.games.util.GenericSubject;

import java.util.List;

/**
 * Created by Jan Eriksson on 20/11/15.
 */
public interface GameState extends GenericSubject {

  String getWinner();

  void setWinner(String winner);

  List<Player> getPlayers();

  Player getCurrentPlayer();

  void nextPlayer();

  void setRollingAllowed(boolean rollingAllowed);

  void setScoringAllowed(boolean scoringAllowed);

  boolean isRollingAllowed();

  boolean isScoringAllowed();

  void setGameEnd(boolean gameEnd);

  boolean isGameEnd();

  String getStateMessage();

  void setStateMessage(String stateMessage);


}
