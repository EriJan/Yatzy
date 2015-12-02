package enjug.erijan.games.util;

/**
 * GenericSubject to be paired with the generic GenericObserver interface in this package.
 * The methods setChanged and clearChanged have empty default implementations
 * provided since they are somethimes not needed.
 *
 * Created by Jan Eriksson on 2/12/15.
 */

public interface GenericSubject {
  /**
   * Register as obsrever at the Subject.
   * @param observer The observer to be registered.
   */
  void registerObserver(GenericObserver observer);

  /**
   * Remove observer from subject.
   * @param observer The observer to be removed.
   */
  void removeObserver(GenericObserver observer);

  /**
   * Notify all registered observers by calling their update method.
   */
  void notifyObservers();

  /**
   * Optional observer feature, only notify observers when changed set.
   */
  default void setChanged() {
  }

  /**
   * Optional, used to clear a changed state.
   */
  default void clearChanged() {
  }
}
