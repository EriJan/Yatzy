package enjug.erijan.games.util;

/**
 *
 * Factory interface for the creation of GameDie objects.
 *
 * Created by Jan Eriksson on 28/10/15.
 *
 */
public interface DieFactory {

  /**
   * Create and return an implementation of the abstract GameDie class.
   * @return A concrete object of type GameDie.
   */
  GameDie create();
}
