/**
 * Created by Janne on 27/10/15.
 */
import enjug.erijan.*;
public class DiceTest {
  public static void main(String[] args) {
    FiveD6Handler testDevice = new FiveD6Handler();

    for (int i = 0; i < 10; i++) {
      testDevice.rollAllDice();
      System.out.println(testDevice.toString());
    }
  }
}
