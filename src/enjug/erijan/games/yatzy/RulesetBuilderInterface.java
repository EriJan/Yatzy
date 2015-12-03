package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.view.YatzyGui;

/**
 * Builder method to create a new game of some kind of Yatzy.
 *
 * Created by Jan Eriksson on 18/11/15.
 */
public interface RulesetBuilderInterface {
  YatzyGui createAndPopulateGame();
}
