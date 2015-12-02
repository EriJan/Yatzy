package enjug.erijan.games.yatzy.model;

import enjug.erijan.games.util.GenericObserver;

import java.util.*;

/**
 * Created by Jan Eriksson on 20/11/15.
 */
public class GameStateImpl implements GameState {

  private List<GenericObserver> genericObserverList;
  private boolean hasChanged;
  private StringBuffer stateMessage;

  private List<Player> players;
  private Player currentPlayer;
  private ListIterator<Player> playerListIterator;

  private boolean rollingAllowed;
  private boolean scoringAllowed;
  private boolean gameEnd;

  private String winner;

  public GameStateImpl(List players) {
    winner = "";

    this.players = players;
    playerListIterator = players.listIterator();
    currentPlayer = playerListIterator.next();

    stateMessage = new StringBuffer();
    genericObserverList = new ArrayList<>();
    hasChanged = false;

    rollingAllowed = true;
    scoringAllowed = true;
    gameEnd = false;
  }


  @Override
  public List<Player> getPlayers() {
    return players;
  }

  @Override
  public void nextPlayer() {
    if (!playerListIterator.hasNext()) {
      playerListIterator = players.listIterator();
    }
    currentPlayer = playerListIterator.next();
    notifyObservers();
  }

  @Override
  public void setRollingAllowed(boolean rollingAllowed) {
    this.rollingAllowed = rollingAllowed;
    notifyObservers();
  }

  @Override
  public void setScoringAllowed(boolean scoringAllowed) {
    this.scoringAllowed = scoringAllowed;
    notifyObservers();
  }

   @Override
  public boolean isRollingAllowed() {
    return rollingAllowed;
  }

  @Override
  public boolean isScoringAllowed() {
    return scoringAllowed;
  }

  @Override
  public String getStateMessage() {
    return stateMessage.toString();

  }

  @Override
  public void setStateMessage(String stateMessage) {
    this.stateMessage = new StringBuffer(stateMessage);
    notifyObservers();
  }

  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public void setGameEnd(boolean gameEnd) {
    this.gameEnd = gameEnd;
    notifyObservers();
  }

  @Override
  public boolean isGameEnd() {
    return gameEnd;
  }

  @Override
  public void setWinner(String winner) {
    this.winner = winner;
    notifyObservers();
  }

  @Override
  public String getWinner() {
    return winner;
  }

  // GenericObserver methods

  @Override
  public void registerObserver(GenericObserver o) {
    genericObserverList.add(o);
  }

  @Override
  public void removeObserver(GenericObserver o) {
    genericObserverList.remove(o);
  }

  @Override
  public void notifyObservers() {
    if (hasChanged) {
      for (GenericObserver o : genericObserverList) {
        o.update(this);
      }
    }
    clearChanged();
  }

  @Override
  public void setChanged() {
    this.hasChanged = true;
  }

  @Override
  public void clearChanged() {
    hasChanged = false;
  }

}
