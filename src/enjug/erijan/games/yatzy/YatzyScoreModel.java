package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.ScoreBox;
import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;

import java.util.*;

/**
 * Created by Janne on 27/10/15.
 */

public class YatzyScoreModel extends ScoreModel {

  Map scoreBoxMap;
  private static YatzyBoxTypes[] sumRange;
  private static YatzyBoxTypes[] totalRange;

  private List<LocalObserver> observers;

  YatzyScoreModel() {
    scoreBoxMap = new EnumMap<YatzyBoxTypes,ScoreBox>(YatzyBoxTypes.class);
    for(YatzyBoxTypes box : YatzyBoxTypes.values()) {
      scoreBoxMap.put(box,box.createScoreBox());
    }

    sumRange = new YatzyBoxTypes[] {
        YatzyBoxTypes.ONES,
        YatzyBoxTypes.TWOS,
        YatzyBoxTypes.THREES,
        YatzyBoxTypes.FOURS,
        YatzyBoxTypes.FIVES,
        YatzyBoxTypes.SIXES};

    totalRange = new YatzyBoxTypes[] {
        YatzyBoxTypes.SUM,
        YatzyBoxTypes.ONE_PAIR,
        YatzyBoxTypes.TWO_PAIR,
        YatzyBoxTypes.THREE_OF_SAME,
        YatzyBoxTypes.FOUR_OF_SAME,
        YatzyBoxTypes.FULL_HOUSE,
        YatzyBoxTypes.SMALL_STRAIGHT,
        YatzyBoxTypes.BIG_STRAIGHT,
        YatzyBoxTypes.YATZY,
        YatzyBoxTypes.CHANCE
    };

    observers = new ArrayList<LocalObserver>();

  }

  @Override
  public void enterResult(Enum scoreBox, int... result) {
    //YatzyBoxTypes boxKey = (YatzyBoxTypes) scoreBox;
    ScoreBox localBox = (ScoreBox) scoreBoxMap.get(scoreBox);
    localBox.setScore(result);
  }

  private void setSum() {
    int localSum = 0;
    ScoreBox localBox;
    for(YatzyBoxTypes box : sumRange) {
      localBox = (ScoreBox) scoreBoxMap.get(box);
      localSum += localBox.getScore();
    }
    localBox = (ScoreBox) scoreBoxMap.get(YatzyBoxTypes.SUM);
    localBox.setScore(localSum);

    localBox = (ScoreBox) scoreBoxMap.get(YatzyBoxTypes.BONUS);
    localBox.setScore(localSum);

    localSum = 0;
    for(YatzyBoxTypes box : totalRange) {
      localBox = (ScoreBox) scoreBoxMap.get(box);
      localSum += localBox.getScore();
    }
    localBox = (ScoreBox) scoreBoxMap.get(YatzyBoxTypes.TOTAL);
    localBox.setScore(localSum);
  }

  @Override
  public int getScore(Enum scoreBox) {
    ScoreBox localBox = (ScoreBox) scoreBoxMap.get(scoreBox);
    return localBox.getScore();
  }

  @Override
  public Iterator getScoreIterator() {
    return null;
  }

  @Override
  public String toString() {
    String retString = "";

    for(YatzyBoxTypes box : YatzyBoxTypes.values()) {
      ScoreBox localBox = (ScoreBox) scoreBoxMap.get(box);

      retString += box.toString() + " " + localBox.getScore() + " \n";

    }
    return retString;
  }

  @Override
  public void registerObserver(LocalObserver o) {
    observers.add(o);
  }

  @Override
  public void removeObserver(LocalObserver o) {
    observers.remove(o);
  }

  @Override
  public void notifyObservers() {
    for (LocalObserver o : observers) {
      o.update();
    }
  }
}
