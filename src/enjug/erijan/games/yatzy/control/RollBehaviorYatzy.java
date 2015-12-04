package enjug.erijan.games.yatzy.control;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.model.GameState;

import java.util.Iterator;

/**
 * Standard Yatzy rolling. Uses five dice.
 *
 * Created by Jan Eriksson on 16/11/15.
 */

public class RollBehaviorYatzy implements RollBehavior {


  private static final int maxRerolls = 2;
  private DiceHandler diceHandler;
  private GameState gameState;
  private int rollsDone;
  /**
   * Constructor, set local references to game state and dice models.
   *
   * @param gameState Reference to a model for game state.
   * @param diceHandler Reference to a model for the dice.
   */
  public RollBehaviorYatzy(GameState gameState, DiceHandler diceHandler) {
    this.gameState = gameState;
    this.diceHandler = diceHandler;
  }
  /**
   * Roll active dice for the active player.
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

  /**
   * Setup dice for next player.
   */
  @Override
  public void resetDice() {
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
      Iterator<GameDie> dieIterator = diceHandler.getDice();
      boolean anyActiveDice = false;
      while (dieIterator.hasNext() && !anyActiveDice) {
        GameDie localDie = dieIterator.next();
        anyActiveDice = diceHandler.isActiveDie(localDie);
      }
      gameState.setChanged();
      gameState.setRollingAllowed(anyActiveDice);
    }
  }
}
