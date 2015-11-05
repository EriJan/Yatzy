package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;

import java.util.Iterator;

/**
 * Created by Janne on 27/10/15.
 */
public class YatzyAgent{
  private DiceHandler dice;
  private ScoreModel scoreColumn;
  private int noOfRolls;
  private int rollsDone;

  public YatzyAgent(DiceHandler dice) {
    this.dice = dice;
    scoreColumn = new YatzyScoreModel(new Player("Test"));
  }

  public void executeAction(DiceHandler dice) {
    this.dice = dice;
  }

  public void rollDice() {
    dice.rollActiveDice();
  }

  public ScoreModel getScoreColumn() {
    return scoreColumn;
  }

  public void setScore(Enum targetBox) {
    Iterator<GameDie> dieIterator = dice.getDice();
    int[] result = new int[5];
    int i = 0;
    while (dieIterator.hasNext()) {
      GameDie die = dieIterator.next();
      result[i] = die.getSideUp();
      i++;
    }
    scoreColumn.setResult(targetBox,result);
  }
}
