/**
 * Created by Janne on 27/10/15.
 */
import enjug.erijan.games.yatzy.YatzyDice;

public class DiceTest {
  public static void main(String[] args) {
    YatzyDice testDevice = new YatzyDice();

    for (int i = 0; i < 10; i++) {
      testDevice.rollAllDice();
      System.out.println(testDevice.toString());
    }
  }
}
