package enjug.erijan.games.yatzy.model;

import java.util.*;

/**
 * Created by Jan Eriksson on 20/11/15.
 */
public class GameState implements StateInfo {

  private List<StateInfoObserver> observerList;
  private StringBuffer stateMessage;

  private List<String> allScores;
  private List<String> availableScores;

  private List<Player> players;
  private Player currentPlayer;
  private ListIterator<Player> playerListIterator;

  private int[] currentDiceValue;

  private boolean rollingAllowed;
  private boolean scoringAllowed;
  private boolean gameEnd;

  ScoreInterface scoreInterface;

  public GameState(ScoreInterface scoreInterface, List players, List allScores) {
    this.players = players;
    playerListIterator = players.listIterator();
    currentPlayer = playerListIterator.next();

    this.scoreInterface = scoreInterface;
    this.allScores = allScores;
    availableScores = new ArrayList<>(allScores);

    // Remove derived scores from available scores
    ListIterator<String> availIter = availableScores.listIterator();
    while (availIter.hasNext()) {
      String nextBoxId = availIter.next();
      if (isDerivedScore(nextBoxId)) {
        availIter.remove();
      }
    }

    stateMessage = new StringBuffer();
    observerList = new ArrayList<StateInfoObserver>();
    rollingAllowed = true;
    scoringAllowed = true;
    gameEnd = false;
  }

  public void setScore(String boxId) {
    scoreInterface.setScore(currentPlayer.getName(), boxId, currentDiceValue);
  }

  public void setTempScores() {
    scoreInterface.setTempScores(currentPlayer.getName(), availableScores,
        currentDiceValue);
    notifyObservers();
  }

  public void clearTempScores() {
    scoreInterface.clearTempScores(currentPlayer.getName());
    notifyObservers();
  }

  public ScoreInterface getScoreInterface() {
    return scoreInterface;
  }

  public boolean isScoreSet(String boxId) {
    return scoreInterface.isScoreSet(currentPlayer.getName(), boxId);
  }

  public boolean isDerivedScore(String boxId) {
    return scoreInterface.isDerivedScore(boxId);
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setCurrentDiceValue(int[] currentDiceValue) {
    this.currentDiceValue = currentDiceValue;
  }

  public void nextPlayer() {
    if (!playerListIterator.hasNext()) {
      gameEnd = scoreInterface.isAllScoreSet(currentPlayer.getName());
      rollingAllowed = !gameEnd;
      playerListIterator = players.listIterator();
    }

    //TODO check for game end
    currentPlayer = playerListIterator.next();

    notifyObservers();
  }

  @Override
  public String getWinner() {
    String winner = "";
    int highScore = 0;
    for (Player player : players) {
      int totScore = scoreInterface.getTotal(player.getName());
      if (totScore > highScore) {
        highScore = totScore;
        winner = player.getName();
      }
    }
    return winner;
  }

  public void setRollingAllowed(boolean rollingAllowed) {
    this.rollingAllowed = rollingAllowed;
    notifyObservers();
  }

  public void setScoringAllowed(boolean scoringAllowed) {
    this.scoringAllowed = scoringAllowed;
    notifyObservers();
  }

  public void setAvailableScores(List<String> availableScores) {
    this.availableScores = availableScores;
  }

  @Override
  public boolean isGameEnd() {
    return gameEnd;
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
  public List<String> getAvailableScores() {
    return availableScores;
  }

  @Override
  public int getScore(String boxId) {
    return scoreInterface.getScore(currentPlayer.getName(),boxId);
  }

  @Override
  public int getTempScore(String boxId) {
    return scoreInterface.getTempScore(currentPlayer.getName(),boxId);
  }

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
