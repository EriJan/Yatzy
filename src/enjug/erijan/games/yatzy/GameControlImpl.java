package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.GameDie;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class GameControlImpl implements GameControl {

  private Scoring scoring;
  private RollControl rollControl;

  public GameControlImpl(Scoring scoring, RollControl rollControl) {
    this.scoring = scoring;
    this.rollControl = rollControl;
  }

  @Override
  public void toggleActiveDie(GameDie die) {
    rollControl.toggleActiveDie(die);
  }

  @Override
  public void setScore(String boxId) {
    scoring.setScore(boxId);
    rollControl.resetDice();
  }

  @Override
  public void rollActiveDice() {
    rollControl.rollActiveDice();
    scoring.setTempScores();
  }
}
