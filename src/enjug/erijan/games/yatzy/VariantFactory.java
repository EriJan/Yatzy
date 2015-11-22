package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.view.YatzyGui;

/**
 * Created by Jan Eriksson on 18/11/15.
 */
public interface VariantFactory {
  ScoreColumn getScoreModel(Player player);
  DiceHandler getDice();
  YatzyGui getGui(GameControl yatzyAgent, DiceHandler diceHandler, StateInfo stateInfo);
}
