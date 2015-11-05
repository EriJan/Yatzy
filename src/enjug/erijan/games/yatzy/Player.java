package enjug.erijan.games.yatzy;

/**
 * Created by Janne on 04/11/15.
 */
public class Player {
  private final String name;
  //private AbstractYatzyAgent behavior;

  public Player(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  //public void setBehavior(AbstractYatzyAgent behavior) {
//    this.behavior = behavior;
//  }
}
