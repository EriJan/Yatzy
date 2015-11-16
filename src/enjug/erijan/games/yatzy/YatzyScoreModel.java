package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.ScoreBox;
import enjug.erijan.games.yatzy.rules.ScoreBoxFactory;
import enjug.erijan.games.yatzy.rules.YatzyRuleBook;

import java.util.*;

/**
 * Created by Jan Eriksson on 27/10/15.
 */

public class YatzyScoreModel<T extends Enum<T> & ScoreBoxFactory> implements ScoreModel {
  private EnumMap<T,ScoreBox> scoreBoxMap;
  private Player player;
  private List<ScoreObserver> observers;
  //private EnumSet<T> yatzyBoxTypes;
  private Class<T> boxTypes;
  private EnumSet<T> derivedScores;
  private EnumSet<T> sumRange;
  private EnumSet<T> totalRange;

  public YatzyScoreModel(Class<T> boxTypes, Player player) {
    this.player = player;
    this.boxTypes = boxTypes;
    //yatzyBoxTypes = EnumSet.allOf(boxTypes);
    scoreBoxMap = new EnumMap<T,ScoreBox>(boxTypes);
    for (T box : boxTypes.getEnumConstants()) {
      scoreBoxMap.put(box,box.createScoreBox());
    }

    observers = new ArrayList<ScoreObserver>();

    derivedScores = EnumSet.of(Enum.valueOf(boxTypes,"SUM"),
        Enum.valueOf(boxTypes,"BONUS"), Enum.valueOf(boxTypes,"TOTAL"));

    sumRange = EnumSet.range(Enum.valueOf(boxTypes,"ONES"),Enum.valueOf(boxTypes,"SIXES"));
    totalRange = EnumSet.range(Enum.valueOf(boxTypes,"SUM"),Enum.valueOf(boxTypes,"YATZY"));
  }

  @Override
  public void setResult(Enum scoreBox, int... result) {
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
  public int getScore(Enum scoreBox) {
    ScoreBox localBox = scoreBoxMap.get(scoreBox);
    return localBox.getScore();
  }

  @Override
  public int getTempScore(Enum scoreBox) {
    ScoreBox localBox = scoreBoxMap.get(scoreBox);
    return localBox.getTempScore();
  }

  @Override
  public Iterator getScoreIterator() {
    return scoreBoxMap.entrySet().iterator();
  }

  @Override
  public boolean isAllScoreSet() {
    boolean setScoreFound = true;
    Iterator<T> scoreKeyIterator = scoreBoxMap.keySet().iterator();

    // Loop unil a score is found to not be set
    while (scoreKeyIterator.hasNext() && setScoreFound) {
      Enum key = scoreKeyIterator.next();
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
  public boolean isScoreSet(Enum scoreBox) {
    ScoreBox localBox = (ScoreBox) scoreBoxMap.get(scoreBox);
    return localBox.isScoreSet();
  }

  @Override
  public boolean isDerivedScore(Enum scoreBox) {
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
