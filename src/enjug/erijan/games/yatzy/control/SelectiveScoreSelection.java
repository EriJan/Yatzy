package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.model.GameState;
import enjug.erijan.games.yatzy.model.Player;
import enjug.erijan.games.yatzy.model.ScoreInterface;

/**
 *
 * The most common variant of score selection gives full flexibility on where
 * to put a dice result. Setting a 0 result is the same as crossing a score box.
 *
 * Interacts with all three parts of the model: score sheet, state and dice.
 *
 * Created by Jan Eriksson on 16/11/15.
 */
public class SelectiveScoreSelection implements ScoringBehavior {

  private GameState gameState;
  private ScoreInterface scoreInterface;
  private DiceHandler diceHandler;

  /**
   * Constructor, takes refrences to all three parts of the model.
   * @param state Reference to a GameState object.
   * @param scoring Reference to a ScoreInterface object.
   * @param dice Refrence to a DiceHandler object.
   */
  public SelectiveScoreSelection(GameState state, ScoreInterface scoring, DiceHandler dice) {
    this.gameState = state;
    this.scoreInterface = scoring;
    this.diceHandler = dice;
  }

  /**
   * Called when player selects a score.
   * Sets score on specified box, clears temporary scores, blocks scoring,
   * unblocks rolling, evaluates if game ends and advances game to next player.
   *
   * @param boxId Id of score to set.
   */
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

  /**
   * Usually called after dice roll to show the different score results of a dice roll.
   */
  @Override
  public void setTempScores() {
    String playerName = gameState.getCurrentPlayer().getName();
    scoreInterface.setTempScores(playerName, diceHandler.getValues());
  }

  /**
   * Checks if the game is ended.
   * Game ends when all scores are set for all players.
   * If game ends, no rolling or scoring is allowed.
   * Winner i set on the model. GUI will handle visual notification
   * of winner.
   */
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
