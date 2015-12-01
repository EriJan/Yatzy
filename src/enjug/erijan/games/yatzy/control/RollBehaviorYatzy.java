package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.model.GameState;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class RollBehaviorYatzy implements RollBehavior {


  private static final int maxRerolls = 2;
  private DiceHandler diceHandler;
  private GameState gameState;
  private int rollsDone;

  public RollBehaviorYatzy(GameState gameState, DiceHandler diceHandler) {
    this.gameState = gameState;
    this.diceHandler = diceHandler;
  }

  @Override
  public void rollActiveDice() {
    String playerName = gameState.getCurrentPlayer().getName();
    String message;
    if (rollsDone < maxRerolls) {
      rollsDone++;
      diceHandler.rollActiveDice();
      gameState.setScoringAllowed(true);
      message = playerName + " rolled some dice.";

    } else if (rollsDone == maxRerolls) {
      diceHandler.rollActiveDice();
      gameState.setRollingAllowed(false);
      diceHandler.deActivateAllDice();
      rollsDone = 0;
      message = playerName + " last roll.";

    } else {
      message = "No more rolls alowed.";
    }
    gameState.setChanged();
    gameState.setStateMessage(message);
  }

  @Override
  public void resetDice() {
    rollsDone = 0;
    diceHandler.setAllDiceActive();
  }

  @Override
  public void toggleActiveDie(GameDie die) {
    if (rollsDone > 0) {
      diceHandler.toggleActiveDie(die);
    }
  }
}
