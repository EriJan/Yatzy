package enjug.erijan.games.yatzy;

/**
 * Created by Jan Eriksson on 30/10/15.
 */

public interface ScoreSubject {
  public void registerObserver(ScoreObserver o);
  public void removeObserver(ScoreObserver o);
  public void notifyObservers();
}
