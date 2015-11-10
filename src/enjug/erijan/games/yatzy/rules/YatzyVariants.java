package enjug.erijan.games.yatzy.rules;

import enjug.erijan.games.yatzy.YatzyAgent;
import enjug.erijan.games.yatzy.YatzyAgentInterface;

/**
 * Created by Janne on 29/10/15.
 */
public enum YatzyVariants implements YatzyVariantFactory {
  YATZY {
    @Override
    public YatzyAgentInterface create() {
      return new YatzyAgent();
    }
  },
  YATZEE {
    @Override
    public YatzyAgentInterface create() {
      return new YatzyAgent();
    }
  },
  MAXI_YATZY {
    @Override
      public YatzyAgentInterface create() {
        return new YatzyAgent();
      }
  }
}
