package enjug.erijan.games.yatzy;

/**
 * Created by Jan Eriksson on 22/11/15.
 */
public interface ScoreWriter<T extends Enum<T>> {
  //Writer
  void setResult(T scoreBox, int... result);

  void setTempScores(int... result);

  void clearTempScores();
}
