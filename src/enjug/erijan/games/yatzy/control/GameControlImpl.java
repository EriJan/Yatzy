package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.GameDie;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class GameControlImpl implements GameControl {

  private ScoringBehavior scoringBehavior;
  private RollBehavior rollBehavior;

  public GameControlImpl(ScoringBehavior scoringBehavior, RollBehavior rollBehavior) {
    this.scoringBehavior = scoringBehavior;
    this.rollBehavior = rollBehavior;
  }

  @Override
  public void toggleActiveDie(GameDie die) {
    rollBehavior.toggleActiveDie(die);
  }

  @Override
  public void setScore(String boxId) {
    scoringBehavior.setScore(boxId);
    rollBehavior.resetDice();
  }

  @Override
  public void rollActiveDice() {
    rollBehavior.rollActiveDice();
    scoringBehavior.setTempScores();
  }
}
