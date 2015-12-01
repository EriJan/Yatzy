package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.model.Player;
import enjug.erijan.games.yatzy.model.StateInfo;

import java.util.HashMap;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class RollBehaviorMaxiYatzy implements RollBehavior {

  private static final int maxRerolls = 3;

  private DiceHandler diceHandler;
  private StateInfo stateInfo;
  private int rollsDone;
  HashMap<String,Integer> savedRolls;

  public RollBehaviorMaxiYatzy(StateInfo stateInfo, DiceHandler diceHandler) {
    this.diceHandler = diceHandler;
    this.stateInfo = stateInfo;
    savedRolls = new HashMap<>();
    for (Player player : stateInfo.getPlayers()) {
      savedRolls.put(player.getName(),0);
    }
  }

  @Override
  public void rollActiveDice() {
    String message;

    String playerName = stateInfo.getCurrentPlayer().getName();
    int extraRolls = savedRolls.get(playerName);
    int tempRerolls = extraRolls + maxRerolls;

    if (rollsDone < tempRerolls) {
      rollsDone++;
      diceHandler.rollActiveDice();
//      stateInfo.setCurrentDiceValue(diceHandler.getValues());
      stateInfo.setScoringAllowed(true);
      message = playerName + " rolled some dice "
          + (tempRerolls - rollsDone) + " rolls left.";

    } else if (rollsDone == tempRerolls) {
      diceHandler.rollActiveDice();
      savedRolls.put(playerName,0);
//      stateInfo.setCurrentDiceValue(diceHandler.getValues());
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
    String playerName = stateInfo.getCurrentPlayer().getName();
    int extraRolls = savedRolls.get(playerName);
    int rollsToSave = extraRolls + maxRerolls - rollsDone;
    savedRolls.put(playerName,rollsToSave);
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
