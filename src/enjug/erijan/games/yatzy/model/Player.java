package enjug.erijan.games.yatzy.model;

/**
 * A simple player class, only contains a name.
 *
 * Created by Jan Eriksson on 04/11/15.
 */
public class Player {
  private final String name;

  public Player(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
