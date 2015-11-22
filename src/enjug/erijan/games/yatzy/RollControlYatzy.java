package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;

import java.util.List;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class RollControlYatzy implements RollControl {

  private static final int maxRerolls = 3;

  private DiceHandler diceHandler;
  private List<Player> players;
  private int reRolls;
//  Map<String,Integer> rolls

  RollControlYatzy(List<Player> players, DiceHandler diceHandler) {
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
