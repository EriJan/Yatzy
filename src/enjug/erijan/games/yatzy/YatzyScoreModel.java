package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.ScoreBox;
import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;

import java.util.*;

/**
 * Created by Janne on 27/10/15.
 */

public class YatzyScoreModel implements ScoreModel {

  public static final YatzyBoxTypes[] DERIVED_SCORES= new YatzyBoxTypes[] {
      YatzyBoxTypes.SUM,
      YatzyBoxTypes.BONUS,
      YatzyBoxTypes.TOTAL};

  private static final YatzyBoxTypes[] SUM_RANGE= new YatzyBoxTypes[] {
      YatzyBoxTypes.ONES,
      YatzyBoxTypes.TWOS,
      YatzyBoxTypes.THREES,
      YatzyBoxTypes.FOURS,
      YatzyBoxTypes.FIVES,
      YatzyBoxTypes.SIXES};

  private static final YatzyBoxTypes[] TOTAL_RANGE = new YatzyBoxTypes[] {
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

  private Map scoreBoxMap;
  private Player player;
  private List<ScoreObserver> observers;

  public YatzyScoreModel(Player player) {
    this.player = player;

    scoreBoxMap = new EnumMap<YatzyBoxTypes,ScoreBox>(YatzyBoxTypes.class);
    for (YatzyBoxTypes box : YatzyBoxTypes.values()) {
      scoreBoxMap.put(box,box.createScoreBox());
    }

    observers = new ArrayList<ScoreObserver>();

  }

  @Override
  public void setResult(Enum scoreBox, int... result) {
    ScoreBox localBox = (ScoreBox) scoreBoxMap.get(scoreBox);
    if (!localBox.isScoreSet()) {
      localBox.setScore(result);
      setSum();
      notifyObservers();
    }
  }

  @Override
  public void setTempScores(int... result) {
    for (YatzyBoxTypes ybt : YatzyBoxTypes.values()) {
      if (Arrays.binarySearch(DERIVED_SCORES, ybt) < 0) {
        ScoreBox localBox = (ScoreBox) scoreBoxMap.get(ybt);
        localBox.setTempScore(result);
        //setSum();
      }
    }
    notifyObservers();
  }

  @Override
  public void clearTempScores() {
    for (YatzyBoxTypes ybt : YatzyBoxTypes.values()) {
      ScoreBox localBox = (ScoreBox) scoreBoxMap.get(ybt);
      localBox.setTempScore(0);
      //setSum();
    }
    notifyObservers();
  }

  private void setSum() {
    int localSum = 0;
    ScoreBox localBox;
    for(YatzyBoxTypes box : SUM_RANGE) {
      localBox = (ScoreBox) scoreBoxMap.get(box);
      localSum += localBox.getScore();
    }
    localBox = (ScoreBox) scoreBoxMap.get(YatzyBoxTypes.SUM);
    localBox.setScore(localSum);

    localBox = (ScoreBox) scoreBoxMap.get(YatzyBoxTypes.BONUS);
    localBox.setScore(localSum);

    localSum = 0;

    if (isAllScoreSet()) {
      for (YatzyBoxTypes box : TOTAL_RANGE) {
        localBox = (ScoreBox) scoreBoxMap.get(box);
        localSum += localBox.getScore();
      }
      localBox = (ScoreBox) scoreBoxMap.get(YatzyBoxTypes.TOTAL);
      localBox.setScore(localSum);
    }
  }

  @Override
  public int getScore(Enum scoreBox) {
    ScoreBox localBox = (ScoreBox) scoreBoxMap.get(scoreBox);
    return localBox.getScore();
  }

  @Override
  public int getTempScore(Enum scoreBox) {
    ScoreBox localBox = (ScoreBox) scoreBoxMap.get(scoreBox);
    return localBox.getTempScore();
  }

  @Override
  public Iterator getScoreIterator() {
    return scoreBoxMap.entrySet().iterator();
  }

  public boolean isAllScoreSet() {
    boolean setScoreFound = true;
    Iterator<Enum> scoreKeyIterator = scoreBoxMap.keySet().iterator();

    // Loop unil a score is found to not be set
    while (scoreKeyIterator.hasNext() && setScoreFound) {
      Enum key = scoreKeyIterator.next();
      // No check on the derived scores
      if (Arrays.binarySearch(DERIVED_SCORES, key) < 0) {
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
  public String toString() {
    String retString = "";

    for(YatzyBoxTypes box : YatzyBoxTypes.values()) {
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
