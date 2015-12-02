package enjug.erijan.games.util;

/**
 * This is a generic observer pattern interface.
 * The point is to avoid having to cast an object references
 * and instead use a generic type for the treturned subject referece.
 * Created by Jan Eriksson on 2/11/15.
 */

public interface GenericObserver<T> {
  void update(T subjectRef);
}

