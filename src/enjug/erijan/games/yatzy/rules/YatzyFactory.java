package enjug.erijan.games.yatzy.rules;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.*;

/**
 * Created by Jan Eriksson on 18/11/15.
 */
public interface YatzyFactory {
  ScoreModel getScoreModel(Player player);
  DiceHandler getDice();
  YatzyGui getGui(YatzyAgentInterface yatzyAgent, DiceHandler diceHandler);
}
