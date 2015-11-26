package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.ScoreBox;

import java.util.Iterator;

/**
 * Created by Jan Eriksson on 22/11/15.
 */
public interface ScoreReader {
  //Reader
  Iterator<ScoreBox> getScoreBoxIterator(String playerName);

  int getScore(String playerName, Enum boxKey);

  int getTempScore(String playerName, Enum boxKey);

  int getTotal(String playerName);

  boolean isAllScoreSet(String playerName);

  boolean isScoreSet(String playerName,Enum boxKey);

  boolean isDerivedScore(String playerName,Enum boxKey);
}
