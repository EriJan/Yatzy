package enjug.erijan.games.util;

import java.util.Random;

/**
 * Created by Janne on 27/10/15.
 */

public abstract class GameDie {
  private final int noOfSides;
  private int sideUp;

  GameDie(int noOfSides){
    this.noOfSides = noOfSides;
    this.sideUp = noOfSides;
  }

  public void rollDie() {
    Random random = new Random();
    sideUp = random.nextInt(noOfSides) + 1;
  }

  public int getNoOfSides() {
    return noOfSides;
  }

  public int getSideUp() {
    return sideUp;
  }

}
