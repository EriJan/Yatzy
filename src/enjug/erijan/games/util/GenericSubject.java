package enjug.erijan.games.util;

/**
 * GenericSubject to be paired with the generic GenericObserver interface in this package.
 * The methods setChanged and clearChanged have empty default implementations
 * provided since they are somethimes not needed.
 *
 * Created by Jan Eriksson on 2/11/15.
 */

public interface GenericSubject {

  void registerObserver(GenericObserver o);

  void removeObserver(GenericObserver o);

  void notifyObservers();

  default void setChanged() {
  }

  default void clearChanged() {
  }
}
