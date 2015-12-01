package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.model.GameState;
import enjug.erijan.games.yatzy.model.Player;
import enjug.erijan.games.yatzy.model.ScoreInterface;
import enjug.erijan.games.yatzy.model.StateInfo;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class SelectiveScoreSelection implements ScoringBehavior {

  private StateInfo stateInfo;
  private ScoreInterface scoreInterface;
  private DiceHandler diceHandler;

  public SelectiveScoreSelection(StateInfo stateInfo,
                                 ScoreInterface scoreInterface,
                                 DiceHandler diceHandler) {
    this.stateInfo = stateInfo;
    this.scoreInterface = scoreInterface;
    this.diceHandler = diceHandler;
  }

  @Override
  public void setScore(String boxId) {
    String playerName = stateInfo.getCurrentPlayer().getName();
    String messageString = playerName + " put the results on " + boxId;
    scoreInterface.setScore(playerName,boxId,diceHandler.getValues());
    scoreInterface.clearTempScores(playerName);

    stateInfo.setScoringAllowed(false);
    stateInfo.setRollingAllowed(true);
    stateInfo.nextPlayer();
    stateInfo.setStateMessage(messageString);
  }
  @Override
  public void setTempScores() {
    String playerName = stateInfo.getCurrentPlayer().getName();
    scoreInterface.setTempScores(playerName, diceHandler.getValues());
  }

  @Override
  public void evaluateGameEnd() {
    boolean allScoresSet = false;
    for (Player player : stateInfo.getPlayers()) {
      String playerName = player.getName();
      allScoresSet = scoreInterface.isAllScoreSet(playerName);
    }
    stateInfo.setGameEnd(allScoresSet);
  }
}
