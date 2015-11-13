package enjug.erijan.games.util;

/**
 * Created by Jan Eriksson on 28/10/15.
 * Factory interface for the creation of GameDie objects.
 */
public interface DieFactory {

  /**
   * Create and return an implementation of the abstract GameDie class.
   * @return A concrete object of type GameDie.
   */
  GameDie create();
}
