package enjug.erijan.games.yatzy;

/**
 * Created by Janne on 30/10/15.
 */

public interface DiceObservable {
  public void registerObserver(DiceObserver o);
  public void removeObserver(DiceObserver o);
  public void notifyObservers();
}
