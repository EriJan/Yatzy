package enjug.erijan.games.yatzy;

import javax.swing.*;

/**
 * Created by Jan Eriksson on 08/11/15.
 */
public class YatzyMain {
  public static void main(String[] args) {

    try {
      UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
    } catch (Exception e) {
      e.printStackTrace();
    }


    YatzyAgent agent = new YatzyAgent();
  }
}
