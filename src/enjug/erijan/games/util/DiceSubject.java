package enjug.erijan.games.util;

/**
 *
 * Observable subject for dicehandler
 *
 * Created by Jan Eriksson on 30/10/15.
 *
 */

public interface DiceSubject {
  public void registerObserver(DiceObserver o);
  public void removeObserver(DiceObserver o);
  public void notifyObservers();
}
