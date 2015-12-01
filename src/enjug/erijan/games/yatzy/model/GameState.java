package enjug.erijan.games.yatzy.model;

import java.util.*;

/**
 * Created by Jan Eriksson on 20/11/15.
 */
public class GameState implements StateInfo {

  private List<StateInfoObserver> observerList;
  private StringBuffer stateMessage;

  private List<Player> players;
  private Player currentPlayer;
  private ListIterator<Player> playerListIterator;

  private boolean rollingAllowed;
  private boolean scoringAllowed;
  private boolean gameEnd;

  private String winner;

//  ScoreInterface scoreInterface;


  public GameState(ScoreInterface scoreInterface, List players, List allScores) {
    winner = "";

    this.players = players;
    playerListIterator = players.listIterator();
    currentPlayer = playerListIterator.next();

    stateMessage = new StringBuffer();
    observerList = new ArrayList<>();
    rollingAllowed = true;
    scoringAllowed = true;
    gameEnd = false;
  }

  public GameState(List players) {
    winner = "";

    this.players = players;
    playerListIterator = players.listIterator();
    currentPlayer = playerListIterator.next();

    stateMessage = new StringBuffer();
    observerList = new ArrayList<>();
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

    //TODO check for game end
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
  }

  @Override
  public boolean isGameEnd() {
    return gameEnd;
  }

  @Override
  public void setWinner(String winner) {
    this.winner = winner;
  }

  @Override
  public String getWinner() {
    return winner;
  }

  // Observer methods

  @Override
  public void registerObserver(StateInfoObserver o) {
    observerList.add(o);
  }

  @Override
  public void removeObserver(StateInfoObserver o) {
    observerList.remove(o);
  }

  @Override
  public void notifyObservers() {
    for(StateInfoObserver o : observerList) {
      o.update(this);
    }
  }
}
