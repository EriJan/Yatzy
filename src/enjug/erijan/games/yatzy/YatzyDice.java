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

public class YatzyDice implements DiceHandler, LocalSubject {

  List<GameDie> dice;
  DieTypes dieType;
  List<LocalObserver> observers;

  public YatzyDice() {
    dice = new ArrayList<GameDie>();
    dieType = DieTypes.D6;
    for (int i = 0; i < 5; i++) {
      //dice.add(DieTypes.D6.create());
      dice.add(dieType.create());
    }
    observers = new ArrayList<LocalObserver>();
  }

  int[] getValues() {
    int[] retValues = new int[dice.size()];
    for (int i = 0; i < dice.size(); i++) {
      retValues[i] = dice.get(i).getSideUp();

    }
    return retValues;
  }

  @Override
  public void rollDice(List<GameDie> activeDice) {
    for (GameDie d : activeDice) {
      d.rollDie();
    }
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
