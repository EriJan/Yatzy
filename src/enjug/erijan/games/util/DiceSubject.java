package enjug.erijan.games.util;

/**
 * Created by Jan Eriksson on 30/10/15.
 * Observable subject for dicehandler
 */

public interface DiceSubject {
  public void registerObserver(DiceObserver o);
  public void removeObserver(DiceObserver o);
  public void notifyObservers();
}
