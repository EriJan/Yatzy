package enjug.erijan.games.yatzy.view;

import enjug.erijan.games.util.GenericObserver;
import enjug.erijan.games.yatzy.model.GameState;
import enjug.erijan.games.yatzy.model.Player;
import enjug.erijan.games.yatzy.model.ScoreInterface;

import javax.swing.*;
import java.awt.*;
import java.util.*;

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
  private GameState gameState;

  /**
   * Panel for displaying the scores.
   */
  public ScorePanel(ScoreInterface scoreInterface, GameState gameState) {

    this.setLayout(new GridBagLayout());
    this.gameState = gameState;
    scoreSelectionButtons = new ButtonGroup();

    playerLabels = new HashMap<String,JLabel>();
    scoreColumnPerPlayer = new HashMap<String,Map>();
    scoreSelection = new HashMap<>();

    addScoreSelection(scoreInterface);

    int column = 1;

    for (Player player : gameState.getPlayers()) {
      addScore(scoreInterface,player, column);

      column++;
    }
    scoreInterface.registerObserver(this);
    JLabel jLabel = getCurrentPlayerLabel(gameState);
    jLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
  }

  public JLabel getCurrentPlayerLabel(GameState gameState) {
    String name = gameState.getCurrentPlayer().getName();
    return  (JLabel) playerLabels.get(name);
  }

  public void addScoreSelection(ScoreInterface scoreInterface) {
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    int row = 1;

    java.util.List<String> allScores = scoreInterface.getAllScores();
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
  public void updateScoreSelection(ScoreInterface scoreInterface) {
    for (JComponent jRadioButton : scoreSelection.values()) {
      jRadioButton.setEnabled(false);
    }

    String playerName = gameState.getCurrentPlayer().getName();
    java.util.List<String> availSelect = scoreInterface.getAvailableScores(playerName);

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
  public void addScore(ScoreInterface scoreInterface, Player player, int column) {
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

    java.util.List<String> allScores = scoreInterface.getAllScores();
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
  public void updateScore(ScoreInterface scoreInterface) {
    String name = gameState.getCurrentPlayer().getName();
    Map scoreColumn = (Map) scoreColumnPerPlayer.get(name);
    //JLabel nameLabel = (JLabel) playerLabels.get(name);
    java.util.List<String> allScores = scoreInterface.getAllScores();
    for (String boxId : allScores) {
      JLabel label = (JLabel) scoreColumn.get(boxId);
      if (scoreInterface.isScoreSet(name,boxId)) {
        label.setText(Integer.toString(scoreInterface.getScore(name, boxId)));
        label.setForeground(Color.BLACK);
      } else {
        int tmpScore = scoreInterface.getTempScore(name,boxId);
        label.setText(Integer.toString(tmpScore));
        if (tmpScore > 0) {
          Color color = new Color(0xA408E9);
          label.setForeground(color);
        } else {
          label.setForeground(Color.BLACK);
        }
      }
      label.setPreferredSize(new Dimension(60, 30));
    }
    this.validate();
    this.repaint();
    //jFrame.pack();
  }

  /**
   * This method is called from the score model to trigger updates to
   * the score view.
   *
   * @param scoreInterface Reference to the score model.
   */
  @Override
  public void update(ScoreInterface scoreInterface) {
    updateScoreSelection(scoreInterface);
    updateScore(scoreInterface);
    System.out.println("Updating score selection again");
  }
}
