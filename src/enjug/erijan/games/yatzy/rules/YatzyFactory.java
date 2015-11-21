package enjug.erijan.games.yatzy.rules;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.*;
import enjug.erijan.games.yatzy.view.YatzyGui;

/**
 * Created by Jan Eriksson on 18/11/15.
 */
public interface YatzyFactory {
  ScoreModel getScoreModel(Player player);
  DiceHandler getDice();
  YatzyGui getGui(GameControlInterface yatzyAgent, DiceHandler diceHandler);
}
