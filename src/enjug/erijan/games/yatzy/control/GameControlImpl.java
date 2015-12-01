package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.GameDie;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class GameControlImpl implements GameControl {

  private ScoringBehavior scoringBehavior;
  private RollBehavior rollBehavior;

  private GameControlImpl() {};

  public static GameControl getEmptyGameControl() {
    return new GameControlImpl();
  }

  @Override
  public void setScoringBehavior(ScoringBehavior scoringBehavior) {
    this.scoringBehavior = scoringBehavior;
  }

  @Override
  public void setRollBehavior(RollBehavior rollBehavior) {
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
