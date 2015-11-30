package enjug.erijan.games.yatzy.model;

/**
 * Created by Jan Eriksson on 30/10/15.
 */

public interface StateInfoSubject {
  public void registerObserver(StateInfoObserver o);
  public void removeObserver(StateInfoObserver o);
  public void notifyObservers();
}
