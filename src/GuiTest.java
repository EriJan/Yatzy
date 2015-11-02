/**
 * Created by Janne on 27/10/15.
 */
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.LocalObserver;
import enjug.erijan.games.yatzy.ScoreModel;
import enjug.erijan.games.yatzy.YatzyDice;
import enjug.erijan.games.yatzy.YatzyScoreModel;
import enjug.erijan.games.yatzy.rules.ScoreBox;
import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class GuiTest implements LocalObserver {

  JFrame jframe;
  static YatzyDice testDice = new YatzyDice();

  public GuiTest() {
    jframe = new JFrame("Dice");
    jframe.setLayout(new GridBagLayout());
    //jframe.getContentPane().setBackground(Color.green);
    jframe.setVisible(false);
    jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

  }

  public void addScore(ScoreModel scoreModel) {
    JPanel jPanel= new JPanel();
    jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    jframe.add(jPanel,c);

    ButtonGroup group = new ButtonGroup();
    int row = 0;

    //
    for(YatzyBoxTypes ybt : YatzyBoxTypes.values()) {

      JRadioButton radButton = new JRadioButton(ybt.name());
      group.add(radButton);
      radButton.setEnabled(false);
      c.gridx = 0;
      c.gridy = row;
      c.anchor = GridBagConstraints.LINE_START;
      jframe.add(radButton, c);
      row++;
    }

    row = 0;
    Iterator<ScoreBox> scoreBoxIterator = scoreModel.getScoreIterator();
    while (scoreBoxIterator.hasNext()) {

      Map.Entry thisEntry = (Map.Entry) scoreBoxIterator.next();
      //Object key = thisEntry.getKey();
      //Object value = thisEntry.getValue();
      ScoreBox scoreBox = (ScoreBox) thisEntry.getValue();
      //ScoreBox scoreBox = (ScoreBox) scoreBoxIterator.next();
      c.gridy = row;
      c.gridx = 1;
      JButton botton = new JButton(Integer.toString(scoreBox.getScore()));
      botton.setForeground(Color.BLACK);
      botton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      botton.setMargin(new Insets(2,2,2,2));
      //botton.setPreferredSize(new Dimension());
      botton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          botton.setForeground(Color.RED);
        }
      });

     jframe.add(botton,c);

      row++;
    }

    //jframe.getContentPane().add(jPanel);
    jframe.pack();
    jframe.setVisible(true);
  }

  public void addDice(Iterator<GameDie> dice) {
    JPanel jPanel= new JPanel();
    jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));
    while (dice.hasNext()) {
      ImageIcon imageIcon = dice.next().getSideImage();

      JLabel label = new JLabel(imageIcon);
      jPanel.add(label);
    }
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 3;
    c.gridy = 0;
    //c.fill = GridBagConstraints.VERTICAL;
    c.anchor = GridBagConstraints.CENTER;
    c.gridheight = 0;
    jframe.getContentPane().add(jPanel,c);
    jframe.pack();
    jframe.setVisible(true);
  }

  public static void main(String[] args) {

//    GuiTest testGui = new GuiTest();
//    testDice.registerObserver(testGui);
//    GuiTest testGui1 = new GuiTest();
//    testDice.registerObserver(testGui1);
//    GuiTest testGui2 = new GuiTest();
//    testDice.registerObserver(testGui2);
//    //testGui.addDice(testDice.getDice());
//    for (int i = 0; i < 5; i++) {
//      testDice.rollAllDice();
//      System.out.println(testDice.toString());
//      //testGui.addDice(testDice.getDice());
//    }

    GuiTest testGui = new GuiTest();
    ScoreModel scoreModel = new YatzyScoreModel();
    testDice.registerObserver(testGui);
    testDice.rollAllDice();
    testGui.addScore(scoreModel);
  }

  @Override
  public void update() {
    addDice(testDice.getDice());
  }
}
