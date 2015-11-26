package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;

import java.util.List;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class RollControlYatzy implements RollControl {

  private static final int maxRerolls = 2;

  private DiceHandler diceHandler;
  private int rollsDone;
  private GameState gameState;

  RollControlYatzy(GameState gameState, DiceHandler diceHandler) {
    this.diceHandler = diceHandler;
    this.gameState = gameState;
  }

  @Override
  public void rollActiveDice(String playerName) {
    String message;
    if (rollsDone < maxRerolls) {
      rollsDone++;
      diceHandler.rollActiveDice();
      gameState.setCurrentDiceValue(diceHandler.getValues());
      gameState.setScoringAllowed(true);
      message = playerName + " rolled some dice.";

    } else if (rollsDone == maxRerolls) {
      diceHandler.rollActiveDice();
      gameState.setCurrentDiceValue(diceHandler.getValues());
      gameState.setRollingAllowed(false);
      diceHandler.deActivateAllDice();
      rollsDone = 0;
      message = playerName + " last roll.";

    } else {
      message = "No more rolls alowed.";
    }
    gameState.setStateMessage(message);
  }

  @Override
  public void resetDice() {
    rollsDone = 0;
    diceHandler.setAllDiceActive();
  }

  @Override
  public int rollsLeft() {
    return maxRerolls - rollsDone;
  }

  @Override
  public void toggleActiveDie(GameDie die) {
    diceHandler.toggleActiveDie(die);
  }
}
