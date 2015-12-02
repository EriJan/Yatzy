package enjug.erijan.games.util;

/**
 * This is a generic observer pattern interface.
 * The point is to avoid having to cast an object reference
 * and instead use a generic type for the returned subject reference.
 *
 * Created by Jan Eriksson on 2/12/15.
 */

public interface GenericObserver<T> {
  /**
   * Generic update method. Since there is only one subject this shuould be a safe solution.
   *
   * @param subjectRef A reference to the subject is passed to the observer.
   *                   To be used for any data needed from subject by observer.
   */
  void update(T subjectRef);
}

