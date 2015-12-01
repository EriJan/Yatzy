package enjug.erijan.games.yatzy.rules;

/** Defines the ScoreRule method.
 *
 * Every implementation of
 *
 * Created by Jan Eriksson on 27/10/15.
 */
@FunctionalInterface
public interface ScoreRule {
  int calculateScore(int... result);
}
