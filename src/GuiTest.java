/**
 * Created by Janne on 27/10/15.
 */
import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.ScoreObserver;
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

public class GuiTest implements ScoreObserver {

  JFrame jframe;
  static YatzyDice testDice = new YatzyDice();
  JPanel diePanel = new JPanel();

  public GuiTest() {
    jframe = new JFrame("Dice");
    jframe.setLayout(new GridBagLayout());
    //jframe.getContentPane().setBackground(Color.green);
    jframe.setVisible(false);
    jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

  }

  public void addScoreSelection(ScoreModel scoreModel) {
    JPanel jPanel= new JPanel();
    jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    jframe.add(jPanel,c);

    ButtonGroup group = new ButtonGroup();
    int row = 1;

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


  }
  public void addScore(ScoreModel scoreModel, int column) {
    GridBagConstraints c = new GridBagConstraints();
    c.gridy = 0;
    c.gridx = column;
    JButton namebotton = new JButton("Name");
    jframe.add(namebotton,c);

    int row = 1;
    Iterator<ScoreBox> scoreBoxIterator = scoreModel.getScoreIterator();
    while (scoreBoxIterator.hasNext()) {

      Map.Entry thisEntry = (Map.Entry) scoreBoxIterator.next();
      //Object key = thisEntry.getKey();
      //Object value = thisEntry.getValue();
      ScoreBox scoreBox = (ScoreBox) thisEntry.getValue();
      //ScoreBox scoreBox = (ScoreBox) scoreBoxIterator.next();
      c.gridy = row;
      c.anchor = GridBagConstraints.CENTER;
      JButton botton = new JButton(Integer.toString(scoreBox.getScore()));
      botton.setForeground(Color.BLACK);
      //botton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      botton.setMargin(new Insets(5,5,5,5));
      botton.setHorizontalTextPosition(JButton.CENTER);

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

  public void addDice(DiceHandler diceHandler, int column) {
    Iterator<GameDie> dice = diceHandler.getDice();
    //JPanel jPanel= new JPanel();
    diePanel.removeAll();
    diePanel.setLayout(new BoxLayout(diePanel,BoxLayout.Y_AXIS));
    while (dice.hasNext()) {
      GameDie die = dice.next();
      ImageIcon imageIcon;
      if (diceHandler.isActiveDie(die)) {
        imageIcon = die.getSideImage();
      } else {
        imageIcon = die.getSideImageAlt();
      }
      //JLabel label = new JLabel(imageIcon);
//      JButton label = new JButton(imageIcon);
      JButton label = new JButton(imageIcon);
      label.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          diceHandler.toggleActiveDie(die);
        }
      });

      diePanel.add(label);
    }
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = column;
    c.gridy = 0;
    //c.fill = GridBagConstraints.VERTICAL;
    c.anchor = GridBagConstraints.CENTER;
    c.gridheight = 0;
    c.gridwidth = 2;
    jframe.getContentPane().add(diePanel,c);
    jframe.pack();
    jframe.setVisible(true);
  }

  public void addRollButton(DiceHandler diceHandler, int column, ScoreModel scoreModel) {
    JButton button = new JButton("Roll");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        diceHandler.rollActiveDice();
        Iterator<GameDie> dieIterator = diceHandler.getDice();
        int[] result = new int[5];
        int i = 0;
        while (dieIterator.hasNext()) {
          GameDie die = dieIterator.next();
          result[i] = die.getSideUp();
          i++;
        }
        for(YatzyBoxTypes ybt : YatzyBoxTypes.values()) {
          scoreModel.enterResult(ybt,result);
        }
        addScore(scoreModel,0);
      }
    });
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = column;
    c.gridy = 16;
    jframe.getContentPane().add(button,c);
    jframe.pack();
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
    ScoreModel scoreModel2 = new YatzyScoreModel();
    //YatzyDice testDice = new YatzyDice();
    testDice.registerObserver(testGui);
    testDice.rollAllDice();
    testGui.addScoreSelection(scoreModel);
    testGui.addScore(scoreModel,1);
    testGui.addScore(scoreModel2,2);
    testGui.addRollButton(testDice,3,scoreModel);


  }

  @Override
  public void update() {
    addDice(testDice,3);
    //addScore(scoreModel,2);
  }
}
