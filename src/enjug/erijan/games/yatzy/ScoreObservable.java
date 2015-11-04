package enjug.erijan.games.yatzy;

/**
 * Created by Janne on 30/10/15.
 */

public interface ScoreObservable {
  public void registerObserver(ScoreObserver o);
  public void removeObserver(ScoreObserver o);
  public void notifyObservers();
}
