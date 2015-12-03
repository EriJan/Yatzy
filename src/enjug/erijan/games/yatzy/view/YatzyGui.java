package enjug.erijan.games.yatzy.view;

import enjug.erijan.games.util.GenericObserver;
import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.*;
import enjug.erijan.games.yatzy.control.GameControl;
import enjug.erijan.games.yatzy.model.*;

import javax.swing.*;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

/**
 * <p>
 * GUI wrapper for any kind of Yatzy game.
 * Built from four separate panels: Score, Dice, Buttons and Info.
 * Fixme buttons should not have their own panel.
 * </p>
 *
 * Created by Jan Eriksson on 03/11/15.
 */

public class YatzyGui {

  private JFrame jFrame;

  private ScorePanel scorePanel;
  private DicePanel dicePanel;
  private ButtonsPanel buttonsPanel;
  private InfoPanel infoPanel;

  private DiceHandler diceHandler;
  private GameControl gameControl;
  private GameState gameState;
  private ScoreInterface scoreInterface;

  /**
   * Creates all the panels and places them in a GridBagLayout.
   *
   * @param gameControl Controller.
   * @param scoreInterface Score model.
   * @param diceHandler Dice model.
   * @param gameState State model.
   */
  public YatzyGui(GameControl gameControl, ScoreInterface scoreInterface,
                  DiceHandler diceHandler, GameState gameState) {

    jFrame = new JFrame("Yatzy");
    jFrame.setLayout(new GridBagLayout());
    jFrame.setVisible(true);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    infoPanel = new InfoPanel(gameState);
    this.gameState = gameState;
    this.gameControl = gameControl;
    this.diceHandler = diceHandler;
    this.scoreInterface = scoreInterface;
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


  /**
   * Static simple message dialog for use from anywhere.
   * @param message Message to be displayed.
   */
  public static void gameMessage(String message) {
    JOptionPane.showMessageDialog(null, message, "Game Message",
        JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * Dialog to query a few options from from a drop down menu.
   *
   * @param queryString Question to user.
   * @param menuElements Options to select between.
   * @return Index of the selected element.
   */
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

  /**
   * Dialog to get String input from the user.
   *
   * @param queryString Question to user.
   * @return User input.
   */
  public static String userInput(String queryString) {
    String retStr = (String) JOptionPane.showInputDialog(queryString);
    retStr = (retStr == null) ? "" : retStr;
    return retStr;
  }

  /**
   * Panel holding the three buttons Roll, Score and New Game.
   * This class is an observer of GameState. Buttons are activeated and
   * inactivated through a property in GameState.
   */
  private class ButtonsPanel extends JPanel implements GenericObserver<GameState> {

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
        //RulesetBuilder.YATZY.newGame();
        jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
        YatzyMain.main(null);

      });
      this.add(newGameButton);
    }

    /**
     * Enabel/disable roll and score button.
     * @param gameState Game state model.
     */
    @Override
    public void update(GameState gameState) {
      rollButton.setEnabled(gameState.isRollingAllowed());
      scoreButton.setEnabled(gameState.isScoringAllowed());
    }
  }

  /**
   * Panel for the ScoreSheet.
   * RadioButton is used to select score to set when Score button is pressed.
   * Unavailable scores are greyed out.
   * <p>
   * Observer to a ScoreInterface
   * </p>
   * Created by Jan Eriksson on 19/11/15.
   * Todo blank istf 0...
   */
  public class ScorePanel extends JPanel implements GenericObserver<ScoreInterface> {

    private Map scoreColumnPerPlayer;
    private Map playerLabels;
    private Map<String, JComponent> scoreSelection;
    private ButtonGroup scoreSelectionButtons;

    /**
     * Panel for displaying the scores.
     */
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
      scoreInterface.registerObserver(this);
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

      List<String> allScores = scoreInterface.getAllScores();
      for (String boxId : allScores) {
        if (scoreInterface.isDerivedScore(boxId)) {
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

    /**
     * Updates the current score selection based on curren players available scores.
     *
     */
    public void updateScoreSelection() {
      for (JComponent jRadioButton : scoreSelection.values()) {
        jRadioButton.setEnabled(false);
      }

      String playerName = gameState.getCurrentPlayer().getName();
      List<String> availSelect = scoreInterface.getAvailableScores(playerName);

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

    /**
     * Get the string corresponding to the selected score.
     * @return Selected score.
     */
    public String getSelected() {
      return scoreSelectionButtons.getSelection().getActionCommand();
    }

    /**
     * Add score column for a specific player.
     *
     * @param player Name of player.
     * @param column Index of column.
     */
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

      List<String> allScores = scoreInterface.getAllScores();
      int row = 1;
      for (String boxId : allScores) {
        c.gridy = row;
        c.anchor = GridBagConstraints.CENTER;
        JLabel label = new JLabel(Integer.toString(scoreInterface.getScore(name,boxId)));
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

    /**
     * Called when score model is updated.
     */
    public void updateScore() {
      String name = gameState.getCurrentPlayer().getName();
      Map scoreColumn = (Map) scoreColumnPerPlayer.get(name);
      //JLabel nameLabel = (JLabel) playerLabels.get(name);
      List<String> allScores = scoreInterface.getAllScores();
      for (String boxId : allScores) {
        JLabel label = (JLabel) scoreColumn.get(boxId);
        if (scoreInterface.isScoreSet(name,boxId)) {
          label.setText(Integer.toString(scoreInterface.getScore(name, boxId)));
          label.setForeground(Color.BLACK);
        } else {
          int tmpScore = scoreInterface.getTempScore(name,boxId);
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

    /**
     * This method is called from the score model to trigger updates to
     * the score view.
     *
     * @param scoreInterface Reference to the score model.
     */
    @Override
    public void update(ScoreInterface scoreInterface) {
      updateScoreSelection();
      updateScore();
      System.out.println("Updating score selection again");
    }
  }

  /**
   * A panel where the game displays information to the user.
   *
   * Created by Jan Eriksson on 19/11/15.
   */
  public static class InfoPanel extends JPanel implements GenericObserver<GameState> {

    JLabel jLabel;

    public InfoPanel(GameState gameState) {
      jLabel = new JLabel("Lets play some Yatzy!");
      //this.gameState = gameState;
      this.add(jLabel);
      gameState.registerObserver(this);
    }

    @Override
    public void update(GameState gameState) {
      jLabel.setText(gameState.getStateMessage());
        if (gameState.isGameEnd()) {
          String winner = gameState.getWinner();
          gameMessage("Game ends with " + winner + " as the winner!");
        }
    }
  }
}
