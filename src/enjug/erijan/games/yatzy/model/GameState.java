package enjug.erijan.games.yatzy.model;

import enjug.erijan.games.util.GenericSubject;

import java.util.List;

/**
 * GameState interface handles interaction with state part of the model.
 *
 * State is defined as messages, player turn, control over what actions are
 * allowed and also who the winner is. Game state is a subject to parts of
 * the User interface.
 *
 * Methods are exclusivly setters and getters, and are mostly self explaining.
 *
 * Created by Jan Eriksson on 20/11/15.
 */
public interface GameState extends GenericSubject {

  String getStateMessage();

  void setStateMessage(String stateMessage);

  List<Player> getPlayers();

  Player getCurrentPlayer();

  void nextPlayer();

  void setRollingAllowed(boolean rollingAllowed);

  boolean isRollingAllowed();

  void setScoringAllowed(boolean scoringAllowed);

  boolean isScoringAllowed();

  void setGameEnd(boolean gameEnd);

  boolean isGameEnd();

  String getWinner();

  void setWinner(String winner);
}
