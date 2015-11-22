package enjug.erijan.games.yatzy;

/**
 * Created by Jan Eriksson on 30/10/15.
 */

public interface ScoreObserver {
  public void update(ScoreReader scoreColumn);
}

