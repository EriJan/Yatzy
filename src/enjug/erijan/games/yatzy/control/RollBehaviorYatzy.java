package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.model.GameState;
import enjug.erijan.games.yatzy.model.StateInfo;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class RollBehaviorYatzy implements RollBehavior {


  private static final int maxRerolls = 2;
  private DiceHandler diceHandler;
  private StateInfo stateInfo;
  private int rollsDone;

  public RollBehaviorYatzy(StateInfo stateInfo, DiceHandler diceHandler) {
    this.stateInfo = stateInfo;
    this.diceHandler = diceHandler;
  }

  @Override
  public void rollActiveDice() {
    String playerName = stateInfo.getCurrentPlayer().getName();
    String message;
    if (rollsDone < maxRerolls) {
      rollsDone++;
      diceHandler.rollActiveDice();
//      gameState.setCurrentDiceValue(diceHandler.getValues());
      stateInfo.setScoringAllowed(true);
      message = playerName + " rolled some dice.";

    } else if (rollsDone == maxRerolls) {
      diceHandler.rollActiveDice();
//      gameState.setCurrentDiceValue(diceHandler.getValues());
      stateInfo.setRollingAllowed(false);
      diceHandler.deActivateAllDice();
      rollsDone = 0;
      message = playerName + " last roll.";

    } else {
      message = "No more rolls alowed.";
    }
    stateInfo.setStateMessage(message);
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
