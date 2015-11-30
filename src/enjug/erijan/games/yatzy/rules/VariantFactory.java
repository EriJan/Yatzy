package enjug.erijan.games.yatzy.rules;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.control.GameControl;
import enjug.erijan.games.yatzy.model.GameState;
import enjug.erijan.games.yatzy.view.YatzyGui;

/**
 * Created by Jan Eriksson on 18/11/15.
 */
public interface VariantFactory {
  GameState createGameState();
  DiceHandler createDice();
  GameControl createGameControl(GameState gameState, DiceHandler diceHandler);
  YatzyGui getGui(GameControl gameControl, DiceHandler diceHandler,
                  GameState gameState);
}
