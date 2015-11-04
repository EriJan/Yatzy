package enjug.erijan.games.util;

import javax.swing.*;
import java.util.Random;

/**
 * Created by Janne on 27/10/15.
 */

public abstract class GameDie {
  private final int noOfSides;
  private int sideUp;
  protected static ImageIcon[] sideIcons;
  protected static ImageIcon[] sideIconsAlt;


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

  public ImageIcon getSideImage() {
    return sideIcons[sideUp-1];
  }

  public ImageIcon getSideImageAlt() {
    return sideIconsAlt[sideUp-1];
  }
}
