package enjug.erijan.games.yatzy;

/**
 * Created by Jan Eriksson on 22/11/15.
 */
public interface ScoreReader<T extends Enum<T>> {
  //Reader
  int getScore(T scoreBox);

  int getTempScore(T scoreBox);

  int getTotal();

  boolean isAllScoreSet();

  boolean isScoreSet(T scoreBox);

  boolean isDerivedScore(T scoreBox);

  Player getPlayer();
}
