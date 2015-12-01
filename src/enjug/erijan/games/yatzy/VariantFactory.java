package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.control.GameControl;
import enjug.erijan.games.yatzy.model.GameState;
import enjug.erijan.games.yatzy.model.ScoreInterface;
import enjug.erijan.games.yatzy.model.StateInfo;
import enjug.erijan.games.yatzy.view.YatzyGui;

/**
 * Created by Jan Eriksson on 18/11/15.
 */
public interface VariantFactory {
  GameState createGameState();

  DiceHandler createDice();

  GameControl createGameControl(ScoreInterface scoreInterface,
                                StateInfo stateInfo,
                                DiceHandler diceHandler);

  YatzyGui getGui(ScoreInterface scoreInterface, StateInfo stateInfo,
                  DiceHandler diceHandler,
                  GameControl gameControl);
}
