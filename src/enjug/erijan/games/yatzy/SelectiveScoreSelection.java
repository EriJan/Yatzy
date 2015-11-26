package enjug.erijan.games.yatzy;

import java.util.ArrayList;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class SelectiveScoreSelection implements Scoring {

  private GameState gameState;

  public SelectiveScoreSelection(GameState gameState) {
    this.gameState = gameState;
  }

  @Override
  public void setScore(String boxId) {
    String messageString = "";
    gameState.setScore(boxId);
    gameState.setScoringAllowed(false);
    gameState.setRollingAllowed(true);
    gameState.clearTempScores();
    gameState.nextPlayer();

    setAvailableScores();
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
