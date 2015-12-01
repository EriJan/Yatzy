package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.view.YatzyGui;

/**
 * Created by Jan Eriksson on 18/11/15.
 */
public interface RulesetFactoryInterface {
  YatzyGui createAndPopulateGame();
}
