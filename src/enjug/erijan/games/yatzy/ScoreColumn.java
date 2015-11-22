package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.ScoreBox;

import java.util.*;


/**
 * Created by Jan Eriksson on 27/10/15.
 */

public class ScoreColumn<T extends Enum<T>> implements ScoreWriter<T>, ScoreReader<T>, ScoreSubject{
  private EnumMap<T,ScoreBox> scoreBoxMap;
  private Player player;
  private List<ScoreObserver> observers;
  private Class<T> boxTypes;
  private EnumSet<T> derivedScores;
  private EnumSet<T> sumRange;
  private EnumSet<T> totalRange;
  private Set<T> keySet;
  private T sumKey;
  private T bonusKey;
  private T totalKey;

  public ScoreColumn(Class<T> boxTypes, Player player) {
    this.player = player;
    this.boxTypes = boxTypes;
    observers = new ArrayList<ScoreObserver>();
  }

  public void setScoreBoxMap(EnumMap<T, ScoreBox> scoreBoxMap) {
    this.scoreBoxMap = scoreBoxMap;
    keySet = scoreBoxMap.keySet();
  }

  public void setDerivedScores(EnumSet<T> derivedScores) {
    this.derivedScores = derivedScores;
    Iterator derivedIter = this.derivedScores.iterator();
    sumKey = (T) derivedIter.next();
    bonusKey = (T) derivedIter.next();
    totalKey = (T) derivedIter.next();
  }

  public void setSumRange(EnumSet<T> sumRange) {
    this.sumRange = sumRange;
  }

  public void setTotalRange(EnumSet<T> totalRange) {
    this.totalRange = totalRange;
  }

  @Override
  public void setResult(T scoreBox, int... result) {
    ScoreBox localBox = scoreBoxMap.get(scoreBox);
    if (!localBox.isScoreSet()) {
      localBox.setScore(result);
      setSum();
      notifyObservers();
    }
  }

  @Override
  public void setTempScores(int... result) {
    for (T ybt : boxTypes.getEnumConstants()) {
      if (!derivedScores.contains(ybt)) {
        ScoreBox localBox = scoreBoxMap.get(ybt);
        localBox.setTempScore(result);
        //setSum();
      }
    }
    notifyObservers();
  }

  @Override
  public void clearTempScores() {
    for (T ybt : boxTypes.getEnumConstants()) {
      ScoreBox localBox = scoreBoxMap.get(ybt);
      localBox.setTempScore(0);
      //setSum();
    }
    notifyObservers();
  }

  private void setSum() {
    int localSum = 0;
    ScoreBox localBox;
    for(Enum box : sumRange) {
      localBox = scoreBoxMap.get(box);
      localSum += localBox.getScore();
    }
    localBox = scoreBoxMap.get(Enum.valueOf(boxTypes,"SUM"));
    localBox.setScore(localSum);

    localBox = scoreBoxMap.get(Enum.valueOf(boxTypes,"BONUS"));
    localBox.setScore(localSum);

    localSum = 0;

    if (isAllScoreSet()) {
      for (Enum box : totalRange) {
        localBox = scoreBoxMap.get(box);
        localSum += localBox.getScore();
      }
      localBox = scoreBoxMap.get(Enum.valueOf(boxTypes,"TOTAL"));
      localBox.setScore(localSum);
    }
  }

  @Override
  public int getScore(T scoreBox) {
    ScoreBox localBox = scoreBoxMap.get(scoreBox);
    return localBox.getScore();
  }

  @Override
  public int getTotal() {
    ScoreBox localBox = scoreBoxMap.get(totalKey);
    return localBox.getScore();
  }

  @Override
  public int getTempScore(T scoreBox) {
    ScoreBox localBox = scoreBoxMap.get(scoreBox);
    return localBox.getTempScore();
  }

  @Override
  public boolean isAllScoreSet() {
    boolean setScoreFound = true;
    Iterator<T> scoreKeyIterator = scoreBoxMap.keySet().iterator();

    // Loop unil a score is found to not be set
    while (scoreKeyIterator.hasNext() && setScoreFound) {
      T key = scoreKeyIterator.next();
      // No check on the derived scores
      if (!derivedScores.contains(key)) {
        ScoreBox localBox = (ScoreBox) scoreBoxMap.get(key);
        setScoreFound = localBox.isScoreSet();
      }
    }
    // setScoreFound will be false as soon as an unset score found
    return  setScoreFound;
  }

  @Override
  public boolean isScoreSet(T scoreBox) {
    ScoreBox localBox = scoreBoxMap.get(scoreBox);
    return localBox.isScoreSet();
  }

  @Override
  public boolean isDerivedScore(T scoreBox) {
    return derivedScores.contains(scoreBox);
  }

  @Override
  public String toString() {
    String retString = "";

    for(T box : boxTypes.getEnumConstants()) {
      ScoreBox localBox = (ScoreBox) scoreBoxMap.get(box);

      retString += box.toString() + " " + localBox.getScore() + " \n";

    }
    return retString;
  }

  @Override
  public void registerObserver(ScoreObserver o) {
    observers.add(o);
  }

  @Override
  public void removeObserver(ScoreObserver o) {

    observers.remove(o);
  }

  @Override
  public void notifyObservers() {
    for (ScoreObserver o : observers) {
      o.update(this);
    }
  }

  @Override
  public Player getPlayer() {
    return player;
  }
}
