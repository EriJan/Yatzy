package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.DieTypes;
import enjug.erijan.games.util.GameDie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Janne on 27/10/15.
 */

public class YatzyDice implements DiceHandler, DiceObservable {

  List<GameDie> dice;
  List<GameDie> activeDice;
  DieTypes dieType;
  List<ScoreObserver> observers;

  public YatzyDice() {
    dice = new ArrayList<GameDie>();
    activeDice = new ArrayList<GameDie>();
    dieType = DieTypes.D6;
    for (int i = 0; i < 5; i++) {
      //dice.add(DieTypes.D6.create());
      dice.add(dieType.create());
    }
    for(GameDie d : dice) {
      activeDice.add(d);
    }
    observers = new ArrayList<ScoreObserver>();
  }

//  int[] getValues() {
//    int[] retValues = new int[dice.size()];
//    for (int i = 0; i < dice.size(); i++) {
//      retValues[i] = dice.get(i).getSideUp();
//
//    }
//    return retValues;
//  }

  @Override
  public void rollActiveDice() {
    for (GameDie d : activeDice) {
      d.rollDie();
    }
    notifyObservers();
  }

  @Override
  public void toggleActiveDie(GameDie die) {
    if (activeDice.contains(die)) {
      activeDice.remove(die);
    } else {
      activeDice.add(die);
    }
    notifyObservers();
  }

  @Override
  public boolean isActiveDie(GameDie die) {
    return activeDice.contains(die);
  }

  @Override
  public void rollAllDice() {
    for (GameDie d : dice) {
      d.rollDie();
    }
    notifyObservers();
  }

  @Override
  public String toString() {
    String tmpStr = "";
    for (GameDie d : dice) {
      tmpStr += d.getSideUp() + " ";
    }
    return tmpStr;
  }

  @Override
  public Iterator<GameDie> getDice() {
    return dice.listIterator();
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
      o.update();
    }
  }
}
