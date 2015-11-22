package enjug.erijan.games.yatzy.view;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.*;
import enjug.erijan.games.yatzy.rules.ScoreRule;

import javax.swing.*;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Jan Eriksson on 03/11/15.
 */

public class YatzyGui<T extends Enum<T> & ScoreRule> {

  private DiceHandler diceHandler;
  private EnumSet<T> yatzyBoxTypes;
  private Class<T> boxClass;

  private JFrame jFrame;
  private JLabel infoLabel;
  private ScorePanel scorePanel;
  private DicePanel dicePanel;
  private ButtonsPanel buttonsPanel;
  private GameControl yatzyAgent;
  private InfoPanel infoPanel;
  //private StateInfo stateInfo;

  /**
   * Constructor to YatzyGui
   *
   * @param yatzyAgent
   * @param diceHandler
   */
  public YatzyGui(Class<T> boxTypes, GameControl yatzyAgent,
                  DiceHandler diceHandler, StateInfo stateInfo) {
    boxClass = boxTypes;
    yatzyBoxTypes = EnumSet.allOf(boxTypes);


    jFrame = new JFrame("Yatzy");
    jFrame.setLayout(new GridBagLayout());
    jFrame.setVisible(true);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    //infoLabel = new JLabel();
    infoPanel = new InfoPanel(stateInfo);
    this.yatzyAgent = yatzyAgent;
    this.diceHandler = diceHandler;

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 1;
    scorePanel = new ScorePanel(yatzyAgent);
    jFrame.add(scorePanel,c);

    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 1;

    buttonsPanel = new ButtonsPanel();
    jFrame.add(buttonsPanel,c);

    c = new GridBagConstraints();
    c.gridx = 2;
    c.gridy = 1;
    dicePanel = new DicePanel(yatzyAgent, diceHandler);
    jFrame.add(dicePanel,c);

    //addInfoText("Lets play some Yatzy");
    c.gridx = 0;
    c.gridy = 0;
//    c.anchor = GridBagConstraints.CENTER;
//    c.gridheight = 2;
//    c.gridwidth = 0;
//    c.ipady = 40;
    jFrame.getContentPane().add(infoPanel,c);

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

  public void updateInfoText(String str) {
    infoLabel.setText(str);
    jFrame.pack();
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


  // Todo, add space between buttons
  private class ButtonsPanel extends JPanel {

    private final Dimension preferedButtonSize = new Dimension(100,40);

    public ButtonsPanel() {
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      addRollButton(diceHandler);
      addSetScoreButton();
      addNewGameButton();
      //this.setBorder(BorderFactory.createEtchedBorder());
    }

    public void addRollButton(DiceHandler diceHandler) {
      JButton button = new JButton("Roll");
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
      button.setPreferredSize(preferedButtonSize);

      button.addActionListener(e -> {
        dicePanel.moveActiveDice(diceHandler);
        yatzyAgent.rollActiveDice();
        //updateInfoText(message);
      });

      this.add(button);
     }

    public void addSetScoreButton() {
      JButton button = new JButton("Set Score");
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
      button.setPreferredSize(preferedButtonSize);

      button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

          JLabel nameLabel = scorePanel.getCurrentPlayerLabel();
          nameLabel.setBorder(null);

          String messageString;
          Enum selected = scorePanel.getSelected();
          yatzyAgent.setScore(selected);
          //updateInfoText(messageString);

          nameLabel = scorePanel.getCurrentPlayerLabel();
          nameLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        }
      });
      this.add(button);
    }

    public void addNewGameButton() {

      for (int j = 0; j < 5; j++) {
        this.add(Box.createRigidArea(preferedButtonSize));

      }

      JButton button = new JButton("New game");
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
      button.setPreferredSize(preferedButtonSize);

      button.addActionListener(e -> {
        yatzyAgent.newGame(jFrame);
      });
      this.add(button);
    }
  }


  /**
   * Created by Jan Eriksson on 19/11/15.
   */
  public class ScorePanel extends JPanel implements ScoreObserver {

    private Map scoreColumnPerPlayer;
    private Map playerLabels;
    private Map scoreSelection;
    private ButtonGroup scoreSelectionButtons;

    public ScorePanel(GameControl yatzyAgent) {

      this.setLayout(new GridBagLayout());

      scoreSelectionButtons = new ButtonGroup();

      playerLabels = new HashMap<String,JLabel>();
      scoreColumnPerPlayer = new HashMap<String,Map>();
      scoreSelection = new EnumMap<T,JRadioButton>(boxClass);

      addScoreSelection(yatzyAgent.getActiveScoreColumn());

      int column = 1;
      Iterator<ScoreColumn> colIterator = yatzyAgent.getScoreColumns();

      while (colIterator.hasNext()) {
        ScoreColumn scoreColumn = colIterator.next();
        addScore(scoreColumn, column);
        scoreColumn.registerObserver(this);
        column++;
      }
      JLabel jLabel = getCurrentPlayerLabel();
      jLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
    }

    public JLabel getCurrentPlayerLabel() {
      ScoreColumn scoreColumn = yatzyAgent.getActiveScoreColumn();
      String name = scoreColumn.getPlayer().getName();
      return  (JLabel) playerLabels.get(name);
    }

    public void addScoreSelection(ScoreColumn scoreColumn) {
      GridBagConstraints c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 0;
      int row = 1;

      for (T key : yatzyBoxTypes) {
        if (scoreColumn.isDerivedScore(key)) {
          JLabel jLabel = new JLabel(key.name());
          Font font = jLabel.getFont();
          Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
          jLabel.setFont(boldFont);
          c.gridx = 0;
          c.gridy = row;
          c.anchor = GridBagConstraints.CENTER;
          this.add(jLabel, c);
          row++;
          scoreSelection.put(key, jLabel);
        } else {
          JRadioButton radButton = new JRadioButton(key.name());
          scoreSelectionButtons.add(radButton);
          radButton.setEnabled(true);
          radButton.setActionCommand(key.name());
          c.gridx = 0;
          c.gridy = row;
          c.anchor = GridBagConstraints.LINE_START;
          this.add(radButton, c);
          row++;
          scoreSelection.put(key, radButton);
        }
      }

      // Top Radio button is ONES
      JRadioButton button = (JRadioButton) scoreSelection.get(Enum.valueOf(boxClass,"ONES"));
      scoreSelectionButtons.clearSelection();
      button.setSelected(true);
    }

    public void updateScoreSelection(ScoreColumn scoreColumn) {

      for (Enum key : yatzyBoxTypes) {
        if (!scoreColumn.isDerivedScore(key)) {
          JRadioButton button = (JRadioButton) scoreSelection.get(key);
          if (scoreColumn.isScoreSet(key)) {
            button.setEnabled(false);
          } else {
            button.setEnabled(true);
            // Lazy auto select, last active will be preselected
            scoreSelectionButtons.clearSelection();
            button.setSelected(true);
          }
        }
      }
      this.validate();
      this.repaint();
    }

    public T getSelected() {
      return Enum.valueOf(boxClass,scoreSelectionButtons.getSelection().
          getActionCommand());
    }

    public void addScore(ScoreColumn scoreModel, int column) {
      EnumMap<T,JComponent> scoreColumn = new EnumMap<T,JComponent>(boxClass);

      GridBagConstraints c = new GridBagConstraints();
      c.gridy = 0;
      c.gridx = column;

      String name = scoreModel.getPlayer().getName();
      JLabel nameLabel = new JLabel(name);
      nameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
      nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
      nameLabel.setPreferredSize(new Dimension(60,25));
      Font font = nameLabel.getFont();
      Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
      nameLabel.setFont(boldFont);
      playerLabels.put(name,nameLabel);
      this.add(nameLabel, c);

      int row = 1;
      for (T ybt : yatzyBoxTypes) {
        c.gridy = row;
        c.anchor = GridBagConstraints.CENTER;
        JLabel label = new JLabel(Integer.toString(scoreModel.getScore(ybt)));
        //button.setActionCommand(ybt.name());
        label.setForeground(Color.BLACK);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createEtchedBorder());
        label.setPreferredSize(new Dimension(60, 30));
        this.add(label, c);
        scoreColumn.put(ybt,label);

        row++;
      }
      scoreColumnPerPlayer.put(name,scoreColumn);
    }

    public void updateScore(ScoreColumn scoreModel) {
      String name = scoreModel.getPlayer().getName();
      Map scoreColumn = (Map) scoreColumnPerPlayer.get(name);
      //JLabel nameLabel = (JLabel) playerLabels.get(name);
      for (T ybt : yatzyBoxTypes) {
        JLabel label = (JLabel) scoreColumn.get(ybt);
        if (scoreModel.isScoreSet(ybt)) {
          label.setText(Integer.toString(scoreModel.getScore(ybt)));
          label.setForeground(Color.BLACK);
        } else {
          int tmpScore = scoreModel.getTempScore(ybt);
          label.setText(Integer.toString(tmpScore));
          if (tmpScore > 0) {
            label.setForeground(Color.GREEN);
          } else {
            label.setForeground(Color.BLACK);
          }
        }
        label.setPreferredSize(new Dimension(60, 30));
      }
      this.validate();
      this.repaint();
    }


    @Override
    public void update(ScoreColumn scoreColumn) {
      updateScoreSelection(scoreColumn);
      updateScore(scoreColumn);
    }
  }

  /**
   * Created by Jan Eriksson on 19/11/15.
   */
  public static class InfoPanel extends JPanel implements StateInfoObserver {

    JLabel jLabel;
    //StateInfo stateInfo;

    public InfoPanel(StateInfo stateInfo) {
      jLabel = new JLabel("Lets play some Yatzy!");
      //this.stateInfo = stateInfo;
      this.add(jLabel);
      stateInfo.registerObserver(this);
    }

    @Override
    public void update(StateInfo stateInfo) {
      jLabel.setText(stateInfo.getStateMessage());

    }
  }
}
