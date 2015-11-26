package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.GameDie;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class GameControl {

  private Scoring scoring;
  private RollControl rollControl;

  public GameControl(Scoring scoring, RollControl rollControl) {
    this.scoring = scoring;
    this.rollControl = rollControl;
  }

  public void toggleActiveDie(GameDie die) {
    rollControl.toggleActiveDie(die);
  }

  public void setScore(String boxId) {
    scoring.setScore(boxId);
    rollControl.resetDice();
  }

  public void rollActiveDice() {
    rollControl.rollActiveDice("");
    scoring.setTempScores();
  }
}
