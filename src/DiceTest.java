/**
 * Created by Janne on 27/10/15.
 */
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.LocalObserver;
import enjug.erijan.games.yatzy.YatzyDice;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class DiceTest implements LocalObserver {

  JFrame jframe;
  static YatzyDice testDice = new YatzyDice();

  public DiceTest() {
    jframe = new JFrame("Dice");
    jframe.setLayout(new BoxLayout(jframe.getContentPane(), BoxLayout.Y_AXIS));
    //jframe.getContentPane().setBackground(Color.green);
    jframe.setVisible(false);
    jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

  }

  public void addDice(Iterator<GameDie> dice) {
    JPanel jPanel= new JPanel();
    jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
   while (dice.hasNext()) {

      Image image = dice.next().getSideImage().getImage();
      Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
      ImageIcon imageIcon = new ImageIcon(newimg);

      JLabel label = new JLabel(imageIcon);
      jPanel.add(label);
    }
    jframe.getContentPane().add(jPanel);
    jframe.pack();
    jframe.setVisible(true);
  }

  public static void main(String[] args) {

    DiceTest testGui = new DiceTest();
    testDice.registerObserver(testGui);
    DiceTest testGui1 = new DiceTest();
    testDice.registerObserver(testGui1);
    DiceTest testGui2 = new DiceTest();
    testDice.registerObserver(testGui2);
    //testGui.addDice(testDice.getDice());
    for (int i = 0; i < 5; i++) {
      testDice.rollAllDice();
      System.out.println(testDice.toString());
      //testGui.addDice(testDice.getDice());
    }
  }

  @Override
  public void update() {
    addDice(testDice.getDice());
  }
}
