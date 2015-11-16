package enjug.erijan.games.yatzy.rules;

import enjug.erijan.games.yatzy.YatzyAgent;
import enjug.erijan.games.yatzy.YatzyAgentInterface;

/**
 * Created by Jan Eriksson on 29/10/15.
 */
public enum YatzyVariants implements YatzyVariantFactory {
  YATZY {
    @Override
    public YatzyAgentInterface create() {
      return new YatzyAgent<YatzyBoxTypes>(YatzyBoxTypes.class,this);
    }
  },
  YATZEE {
    @Override
    public YatzyAgentInterface create() {
      return new YatzyAgent<YatzyBoxTypes>(YatzyBoxTypes.class,this);
    }
  },
  MAXI_YATZY {
    @Override
      public YatzyAgentInterface create() {
        return new YatzyAgent<MaxiYatzyBoxTypes>(MaxiYatzyBoxTypes.class,this);
      }
  }
}
