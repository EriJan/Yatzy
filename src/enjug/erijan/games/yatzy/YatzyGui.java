package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.DiceObserver;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Janne on 03/11/15.
 */
public class YatzyGui implements ScoreObserver, DiceObserver {
  private JFrame jFrame;
  private JPanel diePanel;
  private JLabel infoLabel;
  private YatzyAgentInterface yatzyAgent;
  private DiceHandler diceHandler;
  private Map scoreColumn;
  private Map scoreSelection;
  private ButtonGroup scoreSelectionButtons;
  private static EnumSet<YatzyBoxTypes> yatzyBoxTypes = EnumSet.allOf(YatzyBoxTypes.class);

  public YatzyGui(YatzyAgentInterface yatzyAgent, DiceHandler diceHandler) {
    diePanel = new JPanel();
    jFrame = new JFrame("Yatzy");
    infoLabel = new JLabel();
    jFrame.setLayout(new GridBagLayout());
    jFrame.setVisible(true);
    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    scoreSelectionButtons = new ButtonGroup();
    scoreColumn = new EnumMap<YatzyBoxTypes,JButton>(YatzyBoxTypes.class);
    scoreSelection = new EnumMap<YatzyBoxTypes,JRadioButton>(YatzyBoxTypes.class);

    this.yatzyAgent = yatzyAgent;
    this.diceHandler = diceHandler;

    addScoreSelection();
    addScore(yatzyAgent.getScoreColumn(),1);
    addDice(diceHandler,2);
    addRollButton(2);
    addDoneButton(2);
    addInfoText("Lets play some Yatzy");

    yatzyAgent.getScoreColumn().registerObserver(this);
    diceHandler.registerObserver(this);
    jFrame.pack();

  }

  public void addScoreSelection() {

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    int row = 1;

    for (Enum key : yatzyBoxTypes) {
      JRadioButton radButton = new JRadioButton(key.name());
      scoreSelectionButtons.add(radButton);
      radButton.setEnabled(true);
      radButton.setActionCommand(key.name());
      c.gridx = 0;
      c.gridy = row;
      c.anchor = GridBagConstraints.LINE_START;
      jFrame.add(radButton, c);
      row++;
      scoreSelection.put(key,radButton);
    }
  }

  public void updateScoreSelection(ScoreModel scoreModel) {

    for (YatzyBoxTypes ybt : YatzyBoxTypes.values()) {
      JRadioButton button = (JRadioButton) scoreSelection.get(ybt);
      if (scoreModel.isScoreSet(ybt)) {
        button.setEnabled(false);
      }
    }
    jFrame.validate();
    jFrame.repaint();
    jFrame.pack();
  }

  public void addScore(ScoreModel scoreModel, int column) {
    GridBagConstraints c = new GridBagConstraints();
    c.gridy = 0;
    c.gridx = column;
    JLabel namebotton = new JLabel("Name");
    jFrame.add(namebotton,c);

    int row = 1;
    for (YatzyBoxTypes ybt : YatzyBoxTypes.values()) {
      c.gridy = row;
      c.anchor = GridBagConstraints.CENTER;
      JLabel button = new JLabel(Integer.toString(scoreModel.getScore(ybt)));
      //button.setActionCommand(ybt.name());
      button.setForeground(Color.BLACK);
      button.setHorizontalTextPosition(JButton.CENTER);
      button.setPreferredSize(new Dimension(40,25));
      jFrame.add(button,c);
      scoreColumn.put(ybt,button);

      row++;
    }
    jFrame.pack();
  }

  public void updateScore(ScoreModel scoreModel, int column) {

    for (YatzyBoxTypes ybt : YatzyBoxTypes.values()) {
      JLabel button = (JLabel) scoreColumn.get(ybt);
      if (scoreModel.isScoreSet(ybt)) {
        button.setText(Integer.toString(scoreModel.getScore(ybt)));
        button.setForeground(Color.BLACK);
      } else {
        int tmpScore = scoreModel.getTempScore(ybt);
        button.setText(Integer.toString(tmpScore));
        if (tmpScore > 0) {
          button.setForeground(Color.GREEN);
        } else {
          button.setForeground(Color.RED);
        }
      }
    }
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
      JButton button = new JButton(imageIcon);
      button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          yatzyAgent.toggleActiveDie(die);
        }
      });

      diePanel.add(button);
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
        yatzyAgent.rollActiveDice();
      }
    });
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = column;
    c.gridy = 1;
    jFrame.getContentPane().add(button,c);
    jFrame.pack();
  }

  public void addDoneButton(int column) {
    JButton button = new JButton("Done");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Enum selected = YatzyBoxTypes.
            valueOf(scoreSelectionButtons.getSelection().
                getActionCommand());
        yatzyAgent.setScore(selected);
      }
    });

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = column;
    c.gridy = 3;
    jFrame.getContentPane().add(button,c);
    jFrame.pack();
  }

  public void addInfoText(String str) {
    infoLabel.setText(str);
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 19;
    c.anchor = GridBagConstraints.CENTER;
    c.gridheight = 2;
    c.gridwidth = 0;
    c.ipady = 40;
    jFrame.getContentPane().add(infoLabel,c);
    //jFrame.pack();
  }

  @Override
  public void update(DiceHandler diceHandler) {
    addDice(diceHandler,2);
  }

  @Override
  public void update(ScoreModel scoreModel) {
    updateScore(scoreModel,1);
    updateScoreSelection(scoreModel);
  }
}
