package enjug.erijan.games.yatzy.view;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.*;
import enjug.erijan.games.yatzy.control.GameControl;
import enjug.erijan.games.yatzy.model.GameState;
import enjug.erijan.games.yatzy.model.Player;
import enjug.erijan.games.yatzy.model.StateInfo;
import enjug.erijan.games.yatzy.model.StateInfoObserver;

import javax.swing.*;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;


/**
 * Created by Jan Eriksson on 03/11/15.
 */

public class YatzyGui {

  private DiceHandler diceHandler;
  private List<String> yatzyBoxTypes;

  private JFrame jFrame;
  private JLabel infoLabel;
  private ScorePanel scorePanel;
  private DicePanel dicePanel;
  private ButtonsPanel buttonsPanel;
  private GameControl gameControl;
  private GameState gameState;
  private InfoPanel infoPanel;
  //private StateInfo stateInfo;

  /**
   * Constructor to YatzyGui
   *
   * @param gameControl
   * @param diceHandler
   */
  public YatzyGui(GameControl gameControl,
                  DiceHandler diceHandler, GameState gameState) {
    yatzyBoxTypes = new ArrayList<>();

    jFrame = new JFrame("Yatzy");
    jFrame.setLayout(new GridBagLayout());
    jFrame.setVisible(true);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    infoPanel = new InfoPanel(gameState);
    this.gameState = gameState;
    this.gameControl = gameControl;
    this.diceHandler = diceHandler;

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 1;
    scorePanel = new ScorePanel();
    jFrame.add(scorePanel,c);

    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 1;

    buttonsPanel = new ButtonsPanel();
    jFrame.add(buttonsPanel,c);

    c = new GridBagConstraints();
    c.gridx = 2;
    c.gridy = 1;
    dicePanel = new DicePanel(gameControl, diceHandler);
    jFrame.add(dicePanel,c);

    c.gridx = 1;
    c.gridy = 0;
    c.gridwidth = 3;
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
  private class ButtonsPanel extends JPanel implements StateInfoObserver {

    private final Dimension preferedButtonSize = new Dimension(100,30);
    private JButton rollButton;
    private JButton scoreButton;
    private JButton newGameButton;

    public ButtonsPanel() {
      this.setLayout(new GridLayout(18,1));
      addRollButton(diceHandler);
      addSetScoreButton();
      addNewGameButton();
      gameState.registerObserver(this);
      //this.setBorder(BorderFactory.createEtchedBorder());
    }

    public void addRollButton(DiceHandler diceHandler) {
      rollButton = new JButton("Roll");
      rollButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      rollButton.setPreferredSize(preferedButtonSize);

      rollButton.addActionListener(e -> {
        dicePanel.moveActiveDice(diceHandler);
        gameControl.rollActiveDice();
      });

      this.add(rollButton);
     }

    public void addSetScoreButton() {
      scoreButton = new JButton("Set Score");
      scoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      scoreButton.setPreferredSize(preferedButtonSize);
      scoreButton.setEnabled(false);

      scoreButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          scoreButton.setEnabled(false);
          JLabel nameLabel = scorePanel.getCurrentPlayerLabel();
          nameLabel.setBorder(null);
          String selected = scorePanel.getSelected();
          gameControl.setScore(selected);

          nameLabel = scorePanel.getCurrentPlayerLabel();
          nameLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        }
      });
      this.add(scoreButton);
    }

    public void addNewGameButton() {

      for (int j = 0; j < 15; j++) {
        this.add(Box.createRigidArea(preferedButtonSize));
      }

      newGameButton = new JButton("New game");
      newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      newGameButton.setPreferredSize(preferedButtonSize);

      newGameButton.addActionListener(e -> {
        //RulesetFactory.YATZY.newGame();
        jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
        YatzyMain.main(null);

      });
      this.add(newGameButton);
    }

    @Override
    public void update(StateInfo stateInfo) {
      rollButton.setEnabled(stateInfo.isRollingAllowed());
      scoreButton.setEnabled(stateInfo.isScoringAllowed());
    }
  }

  /**
   * Created by Jan Eriksson on 19/11/15.
   * Todo blank istf 0...
   */

  public class ScorePanel extends JPanel implements StateInfoObserver {

    private Map scoreColumnPerPlayer;
    private Map playerLabels;
    private Map<String, JComponent> scoreSelection;
    private ButtonGroup scoreSelectionButtons;

    public ScorePanel() {

      this.setLayout(new GridBagLayout());

      scoreSelectionButtons = new ButtonGroup();

      playerLabels = new HashMap<String,JLabel>();
      scoreColumnPerPlayer = new HashMap<String,Map>();
      scoreSelection = new HashMap<>();

      addScoreSelection();

      int column = 1;

      for (Player player : gameState.getPlayers()) {
        addScore(player, column);

        column++;
      }
      gameState.registerObserver(this);
      JLabel jLabel = getCurrentPlayerLabel();
      jLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
    }

    public JLabel getCurrentPlayerLabel() {
      String name = gameState.getCurrentPlayer().getName();
      return  (JLabel) playerLabels.get(name);
    }

    public void addScoreSelection() {
      GridBagConstraints c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 0;
      int row = 1;

      List<String> allScores = gameState.getAllScores();
      for (String boxId : allScores) {
        if (gameState.isDerivedScore(boxId)) {
          JLabel jLabel = new JLabel(boxId);
          Font font = jLabel.getFont();
          Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
          jLabel.setFont(boldFont);
          c.gridx = 0;
          c.gridy = row;
          c.anchor = GridBagConstraints.CENTER;
          this.add(jLabel, c);
          row++;
          scoreSelection.put(boxId, jLabel);
        } else {
          JRadioButton radButton = new JRadioButton(boxId);
          scoreSelectionButtons.add(radButton);
          radButton.setEnabled(true);
          radButton.setActionCommand(boxId);
          c.gridx = 0;
          c.gridy = row;
          c.anchor = GridBagConstraints.LINE_START;
          this.add(radButton, c);
          row++;
          scoreSelection.put(boxId, radButton);
        }
      }

      JRadioButton button = (JRadioButton) scoreSelection.get(allScores.get(0));
      scoreSelectionButtons.clearSelection();
      button.setSelected(true);
    }

    public void updateScoreSelection() {
      for (JComponent jRadioButton : scoreSelection.values()) {
        jRadioButton.setEnabled(false);
      }

      List<String> availSelect = gameState.getAvailableScores();

      for (String boxId : availSelect) {
        JRadioButton button = (JRadioButton) scoreSelection.get(boxId);
        button.setEnabled(true);
      }
      scoreSelectionButtons.clearSelection();
      if (availSelect.size() > 0) {
        JRadioButton button = (JRadioButton) scoreSelection.get(availSelect.get(0));
        button.setSelected(true);
      }

      this.validate();
      this.repaint();
    }

    public String getSelected() {
      return scoreSelectionButtons.getSelection().getActionCommand();
    }

    public void addScore(Player player, int column) {
      HashMap<String,JComponent> scoreColumn = new HashMap<>();

      GridBagConstraints c = new GridBagConstraints();
      c.gridy = 0;
      c.gridx = column;

      String name = player.getName();
      JLabel nameLabel = new JLabel(name);
      nameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
      nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
      nameLabel.setPreferredSize(new Dimension(60,25));
      Font font = nameLabel.getFont();
      Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
      nameLabel.setFont(boldFont);
      playerLabels.put(name,nameLabel);
      this.add(nameLabel, c);

      List<String> allScores = gameState.getAllScores();
      int row = 1;
      for (String boxId : allScores) {
        c.gridy = row;
        c.anchor = GridBagConstraints.CENTER;
        JLabel label = new JLabel(Integer.toString(gameState.getScore(boxId)));
        //button.setActionCommand(boxId.name());
        label.setForeground(Color.BLACK);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createEtchedBorder());
        label.setPreferredSize(new Dimension(60, 30));
        this.add(label, c);
        scoreColumn.put(boxId,label);

        row++;
      }
      scoreColumnPerPlayer.put(name,scoreColumn);
    }

    public void updateScore() {
      String name = gameState.getCurrentPlayer().getName();
      Map scoreColumn = (Map) scoreColumnPerPlayer.get(name);
      //JLabel nameLabel = (JLabel) playerLabels.get(name);
      List<String> allScores = gameState.getAllScores();
      for (String boxId : allScores) {
        JLabel label = (JLabel) scoreColumn.get(boxId);
        if (gameState.isScoreSet(boxId)) {
          label.setText(Integer.toString(gameState.getScore(boxId)));
          label.setForeground(Color.BLACK);
        } else {
          int tmpScore = gameState.getTempScore(boxId);
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
      jFrame.pack();
    }

    @Override
    public void update(StateInfo stateInfo) {
      updateScoreSelection();
      updateScore();
      System.out.println("Updating score selection again");
    }
  }

  /**
   * Created by Jan Eriksson on 19/11/15.
   */
  public static class InfoPanel extends JPanel implements StateInfoObserver {

    JLabel jLabel;

    public InfoPanel(StateInfo stateInfo) {
      jLabel = new JLabel("Lets play some Yatzy!");
      //this.stateInfo = stateInfo;
      this.add(jLabel);
      stateInfo.registerObserver(this);
    }

    @Override
    public void update(StateInfo stateInfo) {
      jLabel.setText(stateInfo.getStateMessage());
      if (stateInfo.isGameEnd()) {
        String winner = stateInfo.getWinner();
        gameMessage("Game ends with " + winner + " as the winner!");
      }
    }
  }
}
