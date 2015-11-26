package enjug.erijan.games.yatzy;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public interface Scoring {
  void setScore(String targetBox);

  void setAvailableScores();

  void setTempScores();
}
