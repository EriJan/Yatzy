package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class RollControlMaxiYatzy implements RollControl {

  private static final int maxRerolls = 3;

  private DiceHandler diceHandler;
  private GameState gameState;
  private int rollsDone;
  HashMap<String,Integer> savedRolls;

  RollControlMaxiYatzy(GameState gameState, DiceHandler diceHandler) {
    this.diceHandler = diceHandler;
    this.gameState = gameState;
    savedRolls = new HashMap<>();
    for (Player player : gameState.getPlayers()) {
      savedRolls.put(player.getName(),0);
    }
  }

  @Override
  public void rollActiveDice() {
    String message;

    String playerName = gameState.getCurrentPlayer().getName();
    int extraRolls = savedRolls.get(playerName);
    int tempRerolls = extraRolls + maxRerolls;

    if (rollsDone < tempRerolls) {
      rollsDone++;
      diceHandler.rollActiveDice();
      gameState.setCurrentDiceValue(diceHandler.getValues());
      gameState.setScoringAllowed(true);
      message = playerName + " rolled some dice "
          + (tempRerolls - rollsDone) + " rolls left.";

    } else if (rollsDone == tempRerolls) {
      diceHandler.rollActiveDice();
      savedRolls.put(playerName,0);
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
    String playerName = gameState.getCurrentPlayer().getName();
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
