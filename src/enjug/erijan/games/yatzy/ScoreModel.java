package enjug.erijan.games.yatzy;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by Janne on 27/10/15.
 */

public abstract class ScoreModel implements LocalSubject {

  public abstract void enterResult(Enum scoreBox, int... result);

  public abstract int getScore(Enum scoreBox);

  public abstract Iterator getScoreIterator();

}
