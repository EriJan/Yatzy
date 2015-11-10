package enjug.erijan.games.yatzy.rules;

import enjug.erijan.games.yatzy.YatzyAgentInterface;

/**
 * Created by Janne on 08/11/15.
 */
public interface YatzyVariantFactory {
  YatzyAgentInterface create();
}
