package enjug.erijan.games.yatzy;
import enjug.erijan.games.yatzy.rules.ScoreBox;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;

/**
 * Created by Jan Eriksson on 27/10/15.
 */

public interface ScoreModel<T extends Enum<T>> extends ScoreObservable {

  void setScoreBoxMap(EnumMap<T, ScoreBox> scoreBoxMap);

  void setDerivedScores(EnumSet<T> derivedScores);

  void setSumRange(EnumSet<T> sumRange);

  void setTotalRange(EnumSet<T> totalRange);

  void setResult(T scoreBox, int... result);

  void setTempScores(int... result);

  void clearTempScores();

  int getScore(T scoreBox);

  int getTotal();

  int getTempScore(T scoreBox);

  Iterator getScoreIterator();

  boolean isAllScoreSet();

  boolean isScoreSet(T scoreBox);

  boolean isDerivedScore(T scoreBox);

  Player getPlayer();
}
