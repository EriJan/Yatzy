package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.model.Player;
import enjug.erijan.games.yatzy.model.GameState;

import java.nio.channels.Pipe;
import java.util.HashMap;

/**
 * Maxi Yatzy rolling. Uses six dice and the possibility to save rolls.
 *
 * Fixme: the bahavior is buggy.
 * Created by Jan Eriksson on 16/11/15.
 */
public class RollBehaviorMaxiYatzy implements RollBehavior {

  private static final int INITIAL_REROLLS = 2;

  private DiceHandler diceHandler;
  private GameState gameState;
  private int rollsDone;

  HashMap<String,Integer> savedRolls;

  /**
   * Constructor, set local references to game state and dice models.
   * Initiates a hashmap for the saved rolls, one entry per player.
   *
   * @param gameState Reference to a model for game state.
   * @param diceHandler Reference to a model for the dice.
   */
  public RollBehaviorMaxiYatzy(GameState gameState, DiceHandler diceHandler) {
    this.diceHandler = diceHandler;
    this.gameState = gameState;
    savedRolls = new HashMap<>();
    for (Player player : gameState.getPlayers()) {
      savedRolls.put(player.getName(),0);
    }
    rollsDone = 0;
  }

  /**
   * Roll active dice for active player.
   * First take any saved rolls from hashmap and add to
   * temporary max number of rolls.
   *
   * After first roll scoring as allowed by setting that property on
   * game state model to false.
   *
   * After last roll all dice are deactivated and rolling is disallowed
   * by setting a property on state model to false.
   *
   */
  @Override
  public void rollActiveDice() {
    String message;

    String playerName = gameState.getCurrentPlayer().getName();
    int extraRolls = savedRolls.get(playerName);
    int tempRerolls = extraRolls + INITIAL_REROLLS;

    if (rollsDone < tempRerolls) {
      diceHandler.rollActiveDice();
      gameState.setScoringAllowed(true);
      message = playerName + " rolled some dice "
          + (tempRerolls - rollsDone) + " rolls left.";
      rollsDone++;

    } else if (rollsDone == tempRerolls) {
      diceHandler.rollActiveDice();
      gameState.setRollingAllowed(false);
      diceHandler.deActivateAllDice();
      rollsDone++;
      message = playerName + " last roll.";

    } else {
      message = "No more rolls alowed.";
    }
    gameState.setChanged();
    gameState.setStateMessage(message);
  }

  /**
   * Save rolls and setup dice for next player.
   */
  @Override
  public void resetDice() {
    String playerName = gameState.getCurrentPlayer().getName();
    int extraRolls = savedRolls.get(playerName);
    // First roll is free, add one...
    int rollsToSave = extraRolls + INITIAL_REROLLS - rollsDone + 1;
    savedRolls.put(playerName,rollsToSave);
    rollsDone = 0;
    diceHandler.setAllDiceActive();
  }

  /**
   * No holding of dice allowed until at least one roll.
   *
   * @param die Specific die to activate/deactivate.
   */
  @Override
  public void toggleActiveDie(GameDie die) {
    if (rollsDone > 0) {
      diceHandler.toggleActiveDie(die);
    }
  }
}
