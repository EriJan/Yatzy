package enjug.erijan.games.yatzy.rules;

import enjug.erijan.games.yatzy.YatzyAgentInterface;

/**
 * Created by Jan Eriksson on 08/11/15.
 */
public interface YatzyVariantFactory {
  YatzyAgentInterface create();
}
