package enjug.erijan.games.yatzy;

import com.sun.org.apache.xpath.internal.axes.OneStepIterator;
import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.DiceObserver;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

/**
 * Created by Janne on 03/11/15.
 */
public class YatzyGui implements ScoreObserver, DiceObserver {
  private JFrame jFrame;
  private JPanel diePanel;
  private JLabel infoLabel;
  private YatzyAgentInterface yatzyAgent;
  private DiceHandler diceHandler;
  private Map scoreColumnPerPlayer;
  //private Map scoreColumn;
  private Map scoreSelection;
  private ButtonGroup scoreSelectionButtons;
  private static EnumSet<YatzyBoxTypes> yatzyBoxTypes;

  static {
    yatzyBoxTypes = EnumSet.allOf(YatzyBoxTypes.class);
  }

  public YatzyGui(YatzyAgentInterface yatzyAgent, DiceHandler diceHandler) {
    diePanel = new JPanel();
    jFrame = new JFrame("Yatzy");
    infoLabel = new JLabel();
    jFrame.setLayout(new GridBagLayout());
    jFrame.setVisible(true);
    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    scoreSelectionButtons = new ButtonGroup();

    scoreColumnPerPlayer = new HashMap<String,Map>();
    scoreSelection = new EnumMap<YatzyBoxTypes,JRadioButton>(YatzyBoxTypes.class);

    this.yatzyAgent = yatzyAgent;
    this.diceHandler = diceHandler;
    addScoreSelection();

    int column = 1;
    Iterator colIterator = yatzyAgent.getScoreColumns();

    while (colIterator.hasNext()) {
      ScoreModel scoreModel = (ScoreModel) colIterator.next();
      addScore(scoreModel, column);
      column++;
      scoreModel.registerObserver(this);
    }
    addDice(diceHandler,column);
    addRollButton(column);
    addDoneButton(column);
    addInfoText("Lets play some Yatzy");

    //yatzyAgent.getActiveScoreColumn().registerObserver(this);
    diceHandler.registerObserver(this);
    jFrame.pack();

  }

  public void addScoreSelection() {

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    int row = 1;

    for (Enum key : yatzyBoxTypes) {
      if (Arrays.binarySearch(YatzyScoreModel.DERIVED_SCORES, key) >= 0) {
        JLabel radButton = new JLabel(key.name());
        c.gridx = 0;
        c.gridy = row;
        c.anchor = GridBagConstraints.CENTER;
        jFrame.add(radButton, c);
        row++;
        scoreSelection.put(key, radButton);
      } else {
        JRadioButton radButton = new JRadioButton(key.name());
        scoreSelectionButtons.add(radButton);
        radButton.setEnabled(true);
        radButton.setActionCommand(key.name());
        c.gridx = 0;
        c.gridy = row;
        c.anchor = GridBagConstraints.LINE_START;
        jFrame.add(radButton, c);
        row++;
        scoreSelection.put(key, radButton);
      }
    }

    // Top Radio button is ONES
    JRadioButton button = (JRadioButton) scoreSelection.get(YatzyBoxTypes.ONES);
    scoreSelectionButtons.clearSelection();
    button.setSelected(true);
  }

  public void updateScoreSelection(ScoreModel scoreModel) {

    for (YatzyBoxTypes key : YatzyBoxTypes.values()) {
      if (Arrays.binarySearch(YatzyScoreModel.DERIVED_SCORES, key) < 0) {
        JRadioButton button = (JRadioButton) scoreSelection.get(key);
        if (scoreModel.isScoreSet(key)) {
          button.setEnabled(false);
        } else {
          button.setEnabled(true);
          // Lazy auto select, last active will be preselected
          scoreSelectionButtons.clearSelection();
          button.setSelected(true);
        }
      }
    }
    jFrame.validate();
    jFrame.repaint();
    jFrame.pack();
  }

  public void addScore(ScoreModel scoreModel, int column) {
    Map scoreColumn = new EnumMap<YatzyBoxTypes,JButton>(YatzyBoxTypes.class);

    GridBagConstraints c = new GridBagConstraints();
    c.gridy = 0;
    c.gridx = column;

    String name = scoreModel.getPlayer().getName();
    JLabel nameLabel = new JLabel(name);
    nameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    nameLabel.setPreferredSize(new Dimension(60,25));

    jFrame.add(nameLabel,c);

    int row = 1;
    for (YatzyBoxTypes ybt : YatzyBoxTypes.values()) {
      c.gridy = row;
      c.anchor = GridBagConstraints.CENTER;
      JLabel button = new JLabel(Integer.toString(scoreModel.getScore(ybt)));
      //button.setActionCommand(ybt.name());
      button.setForeground(Color.BLACK);
      button.setHorizontalTextPosition(JLabel.CENTER);
      button.setHorizontalAlignment(JLabel.CENTER);
      button.setBorder(BorderFactory.createEtchedBorder());
      button.setPreferredSize(new Dimension(60,30));
      jFrame.add(button,c);
      scoreColumn.put(ybt,button);

      row++;
    }
    jFrame.pack();
    scoreColumnPerPlayer.put(name,scoreColumn);
  }

  public void updateScore(ScoreModel scoreModel) {
    String name = scoreModel.getPlayer().getName();
    Map scoreColumn = (Map) scoreColumnPerPlayer.get(name);

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

  public void updateDice(DiceHandler diceHandler) {
    Iterator<GameDie> dice = diceHandler.getDice();
    diePanel.removeAll();
    //diePanel.setLayout(new BoxLayout(diePanel,BoxLayout.Y_AXIS));
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
    jFrame.pack();
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
    c.gridy = 2;
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

  public static void gameMessage(String message) {
    JOptionPane.showMessageDialog(null, message, "Game Message",
        JOptionPane.PLAIN_MESSAGE);
  }

  public static int userInputFromMenu(String queryString, String... menuElements) {
    String retStr = (String) JOptionPane.showInputDialog(null,
        queryString, "Choose from menu", JOptionPane.INFORMATION_MESSAGE,
        null, menuElements, menuElements[0]);
    retStr = (retStr == null) ? "" : retStr;
    int menuItemNo = 0;
    int i = 0;
    for (String str : menuElements) {
      if (retStr.equals(str)) {
        menuItemNo = i;
      }
      i++;
    }
    return menuItemNo;
  }

  public static String userInput(String queryString) {
    String retStr = (String) JOptionPane.showInputDialog(queryString);
    retStr = (retStr == null) ? "" : retStr;
    return retStr;
  }

  @Override
  public void update(DiceHandler diceHandler) {
    updateDice(diceHandler);
  }

  @Override
  public void update(ScoreModel scoreModel) {
    updateScore(scoreModel);
    updateScoreSelection(scoreModel);
  }
}
