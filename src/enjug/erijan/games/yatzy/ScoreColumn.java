package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.ScoreBox;
import enjug.erijan.games.yatzy.rules.ScoreBoxFactory;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Janne on 27/10/15.
 */
public class ScoreColumn implements ScoreKeeper {

  Map scoreBoxMap;
  Enum currentScoreBox;


  public ScoreColumn() {

    //scoreBoxMap = new EnumMap<, ScoreBox>();
  }

  @Override
  public void enterResult(Enum scoreBox, int... result) {

  }

  @Override
  public int getScore(Enum scoreBox) {
    return 0;
  }

  @Override
  public void setupScoring(Enum scoreBox) {
    //scoreBoxMap = new EnumMap(scoreBox.getClass());
    //for(Enum e : scoreBox.getClass().values)
  }

  @Override
  public String toString() {
    return "ScoreColumn{" +
        "scoreBoxMap=" + scoreBoxMap +
        ", currentScoreBox=" + currentScoreBox +
        '}';
  }
}
