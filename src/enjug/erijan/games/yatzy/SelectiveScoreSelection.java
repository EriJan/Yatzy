package enjug.erijan.games.yatzy;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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

    // Remove from selectable scores
    List availableScores = gameState.getAvailableScores();
    ListIterator<String> availIter = availableScores.listIterator();
    while (availIter.hasNext()) {
      String nextBoxId = availIter.next();
      if (nextBoxId.equals(boxId)) {
        availIter.remove();
      }
    }

    gameState.nextPlayer();

    //TODO hilite
  }

  @Override
  public void setTempScores() {
    gameState.setTempScores();
  }
}
