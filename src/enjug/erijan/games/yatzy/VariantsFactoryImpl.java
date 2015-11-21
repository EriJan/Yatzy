package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.rules.MaxiYatzyBoxes;
import enjug.erijan.games.yatzy.rules.ScoreBox;
import enjug.erijan.games.yatzy.rules.YatzyBoxes;
import enjug.erijan.games.yatzy.view.YatzyGui;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 * Created by Jan Eriksson on 29/10/15.
 */
public enum VariantsFactoryImpl implements VariantFactory {
  YATZY {
    @Override
    public ScoreColumn getScoreModel(Player player) {
      ScoreColumn scoreColumn;
      scoreColumn = new ScoreColumnImpl<YatzyBoxes>(YatzyBoxes.class,player);

      EnumMap<YatzyBoxes, ScoreBox> scoreBoxMap;
      scoreBoxMap = new EnumMap<YatzyBoxes,ScoreBox>(YatzyBoxes.class);
      for (YatzyBoxes box : YatzyBoxes.values()) {
        scoreBoxMap.put(box,box.getScoreBox());
      }
      scoreColumn.setScoreBoxMap(scoreBoxMap);

      scoreColumn.setDerivedScores(EnumSet.of(YatzyBoxes.SUM, YatzyBoxes.BONUS,
          YatzyBoxes.TOTAL));
      scoreColumn.setSumRange(EnumSet.range(YatzyBoxes.ONES, YatzyBoxes.SIXES));
      scoreColumn.setTotalRange(EnumSet.range(YatzyBoxes.SUM, YatzyBoxes.YATZY));

      return scoreColumn;
    }

    @Override
    public DiceHandler getDice() {
      return new DiceHandlerImpl(5);
    }

    @Override
    public YatzyGui getGui(GameControl yatzyAgent, DiceHandler dice) {
      return new YatzyGui<YatzyBoxes>(YatzyBoxes.class, yatzyAgent, dice);
    }
  },
  YATZEE {
    @Override
    public ScoreColumn getScoreModel(Player player) {
      return null;
    }

    @Override
    public DiceHandler getDice() {
      return new DiceHandlerImpl(5);
    }

    @Override
    public YatzyGui getGui(GameControl yatzyAgent, DiceHandler dice) {
      return null;
    }
  },
  MAXI_YATZY {
    @Override
    public ScoreColumn getScoreModel(Player player) {
      ScoreColumn scoreColumn;
      scoreColumn = new ScoreColumnImpl<MaxiYatzyBoxes>(MaxiYatzyBoxes.class,player);

      EnumMap<MaxiYatzyBoxes, ScoreBox> scoreBoxMap;
      scoreBoxMap = new EnumMap<MaxiYatzyBoxes,ScoreBox>(MaxiYatzyBoxes.class);
      for (MaxiYatzyBoxes box : MaxiYatzyBoxes.values()) {
        scoreBoxMap.put(box,box.getScoreBox());
      }
      scoreColumn.setScoreBoxMap(scoreBoxMap);


      scoreColumn.setDerivedScores(EnumSet.of(MaxiYatzyBoxes.SUM, MaxiYatzyBoxes.BONUS,
          MaxiYatzyBoxes.TOTAL));
      scoreColumn.setSumRange(EnumSet.range(MaxiYatzyBoxes.ONES, MaxiYatzyBoxes.SIXES));
      scoreColumn.setTotalRange(EnumSet.range(MaxiYatzyBoxes.SUM, MaxiYatzyBoxes.YATZY));

      return scoreColumn;
    }

    @Override
    public DiceHandler getDice() {
      return new DiceHandlerImpl(6);
    }

    @Override
    public YatzyGui getGui(GameControl yatzyAgent, DiceHandler dice) {
      return new YatzyGui<MaxiYatzyBoxes>(MaxiYatzyBoxes.class,yatzyAgent,dice);
    }
  }
}
