package enjug.erijan.games.yatzy.rules;

/**
 *
 * Defines the ScoreRule method.
 *
 * Used to calcualate score from a result.
 *
 * Created by Jan Eriksson on 27/10/15.
 */
@FunctionalInterface
public interface ScoreRule {
  /**
   *
   * @param result Score calculation base.
   * @return The calculated score based on result.
   */
  int calculateScore(int... result);
}
