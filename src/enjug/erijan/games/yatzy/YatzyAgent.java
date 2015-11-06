package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;

import java.util.Iterator;

/**
 * Created by Janne on 27/10/15.
 */
public class YatzyAgent implements YatzyAgentInterface {
  private DiceHandler dice;
  private ScoreModel scoreColumn;
  private static final int noOfRolls = 2;
  private int rollsDone;

  public YatzyAgent() {
    //this.dice = dice;
    scoreColumn = new YatzyScoreModel(new Player("Test"));
  }

  @Override
  public void rollActiveDice() {
    if (rollsDone <= noOfRolls) {
      rollsDone++;
      dice.rollActiveDice();
      setTempScore();
    }
  }

  @Override
  public void toggleActiveDie(GameDie die) {
    dice.toggleActiveDie(die);
  }

  @Override
  public void setScore(Enum targetBox) {
    scoreColumn.setResult(targetBox,dice.getValues());
    dice.setAllDiceActive();
    rollsDone = 0;
  }

  @Override
  public int getNoOfRoll() {
    return 0;
  }

  @Override
  public void yourTurn(DiceHandler dice) {
    this.dice = dice;

  }

  @Override
  public ScoreModel getScoreColumn() {
    return scoreColumn;
  }

  public void setTempScore() {
    Iterator<GameDie> dieIterator = dice.getDice();
    int[] result = new int[5];
    int i = 0;
    while (dieIterator.hasNext()) {
      GameDie die = dieIterator.next();
      result[i] = die.getSideUp();
      i++;
    }
    scoreColumn.setTempScores(result);
  }
}
