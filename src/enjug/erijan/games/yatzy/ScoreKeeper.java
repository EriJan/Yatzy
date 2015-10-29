package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.ScoreBoxFactory;

/**
 * Created by Janne on 29/10/15.
 */
public interface ScoreKeeper<E extends Enum<E>> {
  void enterResult(E scoreBox, int... result);
  int getScore(E scoreBox);
  void setupScoring(E scoreBox);
}
