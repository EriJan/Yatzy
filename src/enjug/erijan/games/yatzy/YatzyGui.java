package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.DiceObserver;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Janne on 03/11/15.
 */
public class YatzyGui implements ScoreObserver, DiceObserver {
  JFrame jFrame;
  JPanel diePanel;
  YatzyAgent yatzyAgent;
  private Map scoreColumn;
  private List scoreColumns;
  private ButtonGroup scoreSelectionButtons;

  public YatzyGui(YatzyAgent yatzyAgent, ScoreModel scoreModel, YatzyDice diceHandler) {
    diePanel = new JPanel();
    jFrame = new JFrame("Yatzy");
    jFrame.setLayout(new GridBagLayout());
    jFrame.setVisible(true);
    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    scoreSelectionButtons = new ButtonGroup();
    scoreColumn = new HashMap<String,JButton>();

    this.yatzyAgent = yatzyAgent;

    addScoreSelection();
    addScore(scoreModel,1);
    addDice(diceHandler,2);
    addRollButton(2);
    addDoneButton(2);

    scoreModel.registerObserver(this);
    diceHandler.registerObserver(this);
   }

  public void addScoreSelection() {

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    int row = 1;

    for(YatzyBoxTypes ybt : YatzyBoxTypes.values()) {

      JRadioButton radButton = new JRadioButton(ybt.name());
      scoreSelectionButtons.add(radButton);
      radButton.setEnabled(true);
      radButton.setActionCommand(ybt.name());
      c.gridx = 0;
      c.gridy = row;
      c.anchor = GridBagConstraints.LINE_START;
      jFrame.add(radButton, c);
      row++;
    }
  }

  public void addScore(ScoreModel scoreModel, int column) {
    GridBagConstraints c = new GridBagConstraints();
    c.gridy = 0;
    c.gridx = column;
    JButton namebotton = new JButton("Name");
    jFrame.add(namebotton,c);

    int row = 1;
    for (YatzyBoxTypes ybt : YatzyBoxTypes.values()) {
       c.gridy = row;
      c.anchor = GridBagConstraints.CENTER;
      JButton button = new JButton(Integer.toString(scoreModel.getScore(ybt)));
      button.setActionCommand(ybt.name());
      button.setForeground(Color.BLACK);
      button.setHorizontalTextPosition(JButton.CENTER);
      jFrame.add(button,c);
      scoreColumn.put(ybt.name(),button);

      row++;
    }
    jFrame.pack();
  }

  public void updateScore(ScoreModel scoreModel, int column) {
    int row = 1;
    for (YatzyBoxTypes ybt : YatzyBoxTypes.values()) {
      JButton button = (JButton) scoreColumn.get(ybt.name());
      button.setText(Integer.toString(scoreModel.getScore(ybt)));
    }
    System.out.println("Saker sker");
    jFrame.validate();
    jFrame.repaint();
    jFrame.pack();
  }

  public void addDice(DiceHandler diceHandler, int column) {
    Iterator<GameDie> dice = diceHandler.getDice();
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
    c.anchor = GridBagConstraints.CENTER;
    c.gridheight = 0;
    c.gridwidth = 2;
    jFrame.getContentPane().add(diePanel,c);
    jFrame.pack();
    jFrame.setVisible(true);
  }

  public void addRollButton(int column) {
    JButton button = new JButton("Roll");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        yatzyAgent.rollDice();

      }
    });
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = column;
    c.gridy = 16;
    jFrame.getContentPane().add(button,c);
    jFrame.pack();
  }

  public void addDoneButton(int column) {
    JButton button = new JButton("Done");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        YatzyBoxTypes selected = YatzyBoxTypes.
            valueOf(scoreSelectionButtons.getSelection().getActionCommand());
        yatzyAgent.setScore(selected);
      }
    });

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = column;
    c.gridy = 17;
    jFrame.getContentPane().add(button,c);
    jFrame.pack();
  }

  @Override
  public void update(DiceHandler diceHandler) {
    addDice(diceHandler,2);
  }

  @Override
  public void update(ScoreModel scoreModel) {
    updateScore(scoreModel,1);
  }
}
