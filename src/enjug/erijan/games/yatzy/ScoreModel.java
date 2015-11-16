package enjug.erijan.games.yatzy;

import java.util.Iterator;

/**
 * Created by Jan Eriksson on 27/10/15.
 */

public interface ScoreModel extends ScoreObservable {

  void setResult(Enum scoreBox, int... result);

  void setTempScores(int... result);

  void clearTempScores();

  int getScore(Enum scoreBox);

  int getTempScore(Enum scoreBox);

  Iterator getScoreIterator();

  boolean isAllScoreSet();

  boolean isScoreSet(Enum scoreBox);

  boolean isDerivedScore(Enum scoreBox);

  Player getPlayer();
}
