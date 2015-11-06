package enjug.erijan.games.yatzy;

import java.util.Iterator;

/**
 * Created by Janne on 27/10/15.
 */

public abstract class ScoreModel implements ScoreObservable {

  public abstract void setResult(Enum scoreBox, int... result);

  public abstract void setTempScores(int... result);

  public abstract int getScore(Enum scoreBox);

  public abstract int getTempScore(Enum scoreBox);

  public abstract Iterator getScoreIterator();

  public abstract boolean isScoreSet(Enum scoreBox);
}
