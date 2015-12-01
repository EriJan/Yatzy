package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.model.GameState;
import enjug.erijan.games.yatzy.model.ScoreInterface;

import java.util.ArrayList;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class SelectiveScoreSelection implements ScoringBehavior {

  private GameState gameState;
  private ScoreInterface scoreInterface;
  private DiceHandler diceHandler;

  public SelectiveScoreSelection(GameState gameState,
                                 ScoreInterface scoreInterface,
                                 DiceHandler diceHandler) {
    this.gameState = gameState;
    this.scoreInterface = scoreInterface;
    this.diceHandler = diceHandler;
  }

  @Override
  public void setScore(String boxId) {
    String playerName = gameState.getCurrentPlayer().getName();
    String messageString = playerName + " put the results on " + boxId;
    scoreInterface.setScore(playerName,boxId,diceHandler.getValues());
    scoreInterface.clearTempScores(playerName);
//    gameState.setScore(boxId);
//    gameState.clearTempScores();
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
