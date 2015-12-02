package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.model.GameState;
import enjug.erijan.games.yatzy.model.Player;
import enjug.erijan.games.yatzy.model.ScoreInterface;

/**
 *
 * The most common variant of score selection gives full flexibility on where
 * to put a dice result.
 *
 * Created by Jan Eriksson on 16/11/15.
 */
public class SelectiveScoreSelection implements ScoringBehavior {

  private GameState gameState;
  private ScoreInterface scoreInterface;
  private DiceHandler diceHandler;

  public SelectiveScoreSelection(GameState state, ScoreInterface scoring, DiceHandler dice) {
    this.gameState = state;
    this.scoreInterface = scoring;
    this.diceHandler = dice;
  }

  @Override
  public void setScore(String boxId) {
    String playerName = gameState.getCurrentPlayer().getName();
    String messageString = playerName + " put the results on " + boxId;
    scoreInterface.setScore(playerName, boxId, diceHandler.getValues());
    scoreInterface.clearTempScores(playerName);

    gameState.setScoringAllowed(false);
    gameState.setRollingAllowed(true);
    gameState.nextPlayer();
    evaluateGameEnd();
    gameState.setChanged();
    gameState.setStateMessage(messageString);

  }

  @Override
  public void setTempScores() {
    String playerName = gameState.getCurrentPlayer().getName();
    scoreInterface.setTempScores(playerName, diceHandler.getValues());
  }

  @Override
  public void evaluateGameEnd() {
    boolean allScoresSet = false;
    for (Player player : gameState.getPlayers()) {
      String playerName = player.getName();
      allScoresSet = scoreInterface.isAllScoreSet(playerName);
    }
    if (allScoresSet) {
      String winner = scoreInterface.getWinner();
      gameState.setWinner(winner);
      gameState.setGameEnd(allScoresSet);
      gameState.setRollingAllowed(false);
      gameState.setScoringAllowed(false);
    }
  }
}
