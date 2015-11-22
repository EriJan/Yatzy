package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;

import java.util.List;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class RollControlYatzy implements RollControl {

  private static final int maxRerolls = 3;

  private DiceHandler diceHandler;
  private List<Player> players;
  private int reRolls;
  private int rollsDone;
//  Map<String,Integer> rolls

  RollControlYatzy(DiceHandler diceHandle) {
    this.diceHandler = diceHandler;
  }

  @Override
  public void rollActiveDice(String playerName) {
    String message;
    if (rollsDone == 0) {
      diceHandler.setAllDiceActive();
      rollsDone++;
      diceHandler.rollActiveDice();
      setTempScore();
      message = playerName + " rolled some dice.";

    } else if (rollsDone < noOfReRolls) {
      rollsDone++;
      diceHandler.rollActiveDice();
      setTempScore();
      message = playerName + " rolled some dice.";

    } else if (rollsDone == noOfReRolls) {
      rollsDone++;
      diceHandler.rollActiveDice();
      setTempScore();
      message = playerName + " rolled some dice.";
      diceHandler.deActivateAllDice();

    } else {
      message = "No more rolls alowed.";
    }
    //return message;
    stateInfo.setStateMessage(message);
    diceHandler.rollActiveDice();
  }

  @Override
  public int rollsLeft() {
    return maxRerolls - reRolls;
  }
}
