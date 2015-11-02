package enjug.erijan.games.yatzy;

/**
 * Created by Janne on 30/10/15.
 */

public interface LocalSubject {
  public void registerObserver(LocalObserver o);
  public void removeObserver(LocalObserver o);
  public void notifyObservers();
}
