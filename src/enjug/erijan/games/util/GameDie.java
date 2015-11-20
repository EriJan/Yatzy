package enjug.erijan.games.util;

import java.util.Random;

/**
 * Created by Jan Eriksson on 27/10/15.
 * Abstract class describing a general
 * n sided die.
 */

public abstract class GameDie implements Comparable<GameDie> {
  private final int noOfFaces;
  private int face;

  /**
   * @param noOfFaces Set the number of sides on the die.
   */

  GameDie(int noOfFaces) {
    this.noOfFaces = noOfFaces;
    this.face = noOfFaces;
  }

  /**
   * Randomize the current face of the die.
   * The range is 1 up to and including noOfFaces.
   */
  public void rollDie() {
    Random random = new Random();
    face = random.nextInt(noOfFaces) + 1;
  }

  /**
   * @return
   */
  public int getFace() {
    return face;
  }

  @Override
  public int compareTo(GameDie o) {
    return this.getFace() - o.getFace();
  }
}
