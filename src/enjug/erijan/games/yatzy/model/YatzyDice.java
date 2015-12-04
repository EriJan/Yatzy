package enjug.erijan.games.yatzy.model;

import enjug.erijan.games.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of DiceHandler with a number of 6 sided dice, D6, defined at construction.
 *
 * The field dice store every die in the pool, does not change.
 *
 * Active dice are stored as references in the activeDice list. Removed and added
 * with the toggleActiveDice method.
 *
 * Created by Jan Eriksson on 27/10/15.
 */

public class YatzyDice implements DiceHandler {

  List<GameDie> dice;
  List<GameDie> activeDice;
  DieFactory dieType;
  List<GenericObserver> observers;

  /**
   * Constructor, defines a number of dice to create.
   *
   * @param numberOfDice The number of dice in the pool.
   */

  public YatzyDice(int numberOfDice) {
    dice = new ArrayList<GameDie>();
    activeDice = new ArrayList<GameDie>();
    dieType = DieTypes.D6;
    for (int i = 0; i < numberOfDice; i++) {
      dice.add(dieType.create());
    }
    for(GameDie d : dice) {
      activeDice.add(d);
    }
    observers = new ArrayList<>();
  }

  @Override
  public int[] getValues() {
    int[] retValues = new int[dice.size()];
    for (int i = 0; i < dice.size(); i++) {
      retValues[i] = dice.get(i).getFace();

    }
    return retValues;
  }

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
  public void setAllDiceActive() {
    activeDice.removeAll(activeDice);
    activeDice.addAll(dice);
    notifyObservers();
  }

  @Override
  public void deActivateAllDice() {
    activeDice.removeAll(activeDice);
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
      tmpStr += d.getFace() + " ";
    }
    return tmpStr;
  }

  @Override
  public Iterator<GameDie> getDice() {
    return dice.listIterator();
  }

  @Override
  public void registerObserver(GenericObserver o) {
    observers.add(o);
  }

  @Override
  public void removeObserver(GenericObserver o) {
    observers.remove(o);
  }

  @Override
  public void notifyObservers() {
    for (GenericObserver o : observers) {
      o.update(this);
    }
  }
}
