package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.yatzy.model.GameState;

import java.util.ArrayList;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class SelectiveScoreSelection implements ScoringBehavior {

  private GameState gameState;

  public SelectiveScoreSelection(GameState gameState) {
    this.gameState = gameState;
  }

  @Override
  public void setScore(String boxId) {
    String messageString = gameState.getCurrentPlayer().getName()
        + " put the results on " + boxId;
    gameState.setScore(boxId);
    gameState.clearTempScores();
    gameState.setScoringAllowed(false);
    gameState.setRollingAllowed(true);
    gameState.nextPlayer();
    setAvailableScores();
    gameState.setStateMessage(messageString);
  }

  @Override
  public void setAvailableScores() {
    ArrayList<String> availableScores = new ArrayList<String>();
    for (String id : gameState.getAllScores()) {
      if (!gameState.isScoreSet(id) && !gameState.isDerivedScore(id)) {
        availableScores.add(id);
      }
    }

    gameState.setAvailableScores(availableScores);
  }

  @Override
  public void setTempScores() {
    gameState.setTempScores();
  }
}
