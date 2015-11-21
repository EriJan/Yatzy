package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;

import java.util.List;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class RollControlMaxiYatzy implements RollControl {

  private static final int maxRerolls = 2;

  private DiceHandler diceHandler;
  private List<Player> players;
  private int reRolls;
//  Map<String,Integer> rolls

  RollControlMaxiYatzy(List<Player> players, DiceHandler diceHandler) {
    this.players = players;
    this.diceHandler = diceHandler;
  }

  @Override
  public void rollActiveDice() {
    diceHandler.rollActiveDice();
  }

  @Override
  public int rollsLeft() {
    return maxRerolls - reRolls;
  }
}
