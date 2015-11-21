package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.rules.MaxiYatzyBoxes;
import enjug.erijan.games.yatzy.rules.ScoreBox;
import enjug.erijan.games.yatzy.rules.YatzyBoxes;
import enjug.erijan.games.yatzy.rules.YatzyFactory;
import enjug.erijan.games.yatzy.view.YatzyGui;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 * Created by Jan Eriksson on 29/10/15.
 */
public enum YatzyVariants implements YatzyFactory {
  YATZY {
    @Override
    public ScoreModel getScoreModel(Player player) {
      ScoreModel scoreModel;
      scoreModel = new YatzyScoreModel<YatzyBoxes>(YatzyBoxes.class,player);

      EnumMap<YatzyBoxes, ScoreBox> scoreBoxMap;
      scoreBoxMap = new EnumMap<YatzyBoxes,ScoreBox>(YatzyBoxes.class);
      for (YatzyBoxes box : YatzyBoxes.values()) {
        scoreBoxMap.put(box,box.getScoreBox());
      }
      scoreModel.setScoreBoxMap(scoreBoxMap);

      scoreModel.setDerivedScores(EnumSet.of(YatzyBoxes.SUM, YatzyBoxes.BONUS,
          YatzyBoxes.TOTAL));
      scoreModel.setSumRange(EnumSet.range(YatzyBoxes.ONES, YatzyBoxes.SIXES));
      scoreModel.setTotalRange(EnumSet.range(YatzyBoxes.SUM, YatzyBoxes.YATZY));

      return scoreModel;
    }

    @Override
    public DiceHandler getDice() {
      return new YatzyDice(5);
    }

    @Override
    public YatzyGui getGui(GameControlInterface yatzyAgent, DiceHandler dice) {
      return new YatzyGui<YatzyBoxes>(YatzyBoxes.class, yatzyAgent, dice);
    }
  },
  YATZEE {
    @Override
    public ScoreModel getScoreModel(Player player) {
      return null;
    }

    @Override
    public DiceHandler getDice() {
      return new YatzyDice(5);
    }

    @Override
    public YatzyGui getGui(GameControlInterface yatzyAgent, DiceHandler dice) {
      return null;
    }
  },
  MAXI_YATZY {
    @Override
    public ScoreModel getScoreModel(Player player) {
      ScoreModel scoreModel;
      scoreModel = new YatzyScoreModel<MaxiYatzyBoxes>(MaxiYatzyBoxes.class,player);

      EnumMap<MaxiYatzyBoxes, ScoreBox> scoreBoxMap;
      scoreBoxMap = new EnumMap<MaxiYatzyBoxes,ScoreBox>(MaxiYatzyBoxes.class);
      for (MaxiYatzyBoxes box : MaxiYatzyBoxes.values()) {
        scoreBoxMap.put(box,box.getScoreBox());
      }
      scoreModel.setScoreBoxMap(scoreBoxMap);


      scoreModel.setDerivedScores(EnumSet.of(MaxiYatzyBoxes.SUM, MaxiYatzyBoxes.BONUS,
          MaxiYatzyBoxes.TOTAL));
      scoreModel.setSumRange(EnumSet.range(MaxiYatzyBoxes.ONES, MaxiYatzyBoxes.SIXES));
      scoreModel.setTotalRange(EnumSet.range(MaxiYatzyBoxes.SUM, MaxiYatzyBoxes.YATZY));

      return scoreModel;
    }

    @Override
    public DiceHandler getDice() {
      return new YatzyDice(6);
    }

    @Override
    public YatzyGui getGui(GameControlInterface yatzyAgent, DiceHandler dice) {
      return new YatzyGui<MaxiYatzyBoxes>(MaxiYatzyBoxes.class,yatzyAgent,dice);
    }
  }
}
