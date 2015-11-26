package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.view.YatzyGui;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class GameControl {

  //private RulesetFactory yatzyVariant;

  //private DiceHandler dice;
  //private GameState gameState;
  //private ScoreSheet sheet;
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
