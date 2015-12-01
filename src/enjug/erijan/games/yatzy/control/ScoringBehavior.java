package enjug.erijan.games.yatzy.control;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public interface ScoringBehavior {
  void setScore(String targetBox);

  void setTempScores();

  void evaluateGameEnd();
}