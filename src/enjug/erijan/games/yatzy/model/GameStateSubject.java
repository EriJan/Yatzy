package enjug.erijan.games.yatzy.model;

/**
 * Created by Jan Eriksson on 30/10/15.
 */

public interface GameStateSubject {
  void registerObserver(GameStateObserver o);
  void removeObserver(GameStateObserver o);
  void notifyObservers();
  void setChanged();
  void clearChanged();
}
