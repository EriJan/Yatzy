package enjug.erijan.games.yatzy.view;

import enjug.erijan.games.util.GenericObserver;
import enjug.erijan.games.yatzy.model.ScoreInterface;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * <p>
 *   A JPanel with the score names and radio buttons for selection
 * </p>
 *
 * @author Jan Eriksson
 * @Version 1.0
 * @since 09/12/15
 */
public class ScoreColumn extends JPanel implements GenericObserver<ScoreInterface> {

  String currentPlayer;
  private Map<String, JLabel> scoreSelection;
  private ButtonGroup scoreSelectionButtons;
  private final Dimension prefSize = new Dimension(150,20);

  public ScoreColumn(ScoreInterface scoreInterface) {

    List<String>  allScores = scoreInterface.getAllScores();
    this.setLayout(new GridLayout(0,1));
    scoreSelectionButtons = new ButtonGroup();
    scoreSelection = new HashMap<>();
    this.add(Box.createRigidArea(prefSize));
    for (String boxId : allScores) {
//      if (scoreInterface.isDerivedScore(boxId)) {
        JLabel jLabel = new JLabel(boxId);
        Font font = jLabel.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
        jLabel.setFont(boldFont);
        jLabel.setPreferredSize(prefSize);
        this.add(jLabel);
        scoreSelection.put(boxId, jLabel);
//      } else {
//        JRadioButton radButton = new JRadioButton(boxId);
//        scoreSelectionButtons.add(radButton);
//        radButton.setEnabled(true);
//        radButton.setActionCommand(boxId);
//        radButton.setPreferredSize(prefSize);
//        this.add(radButton);
//        scoreSelection.put(boxId, radButton);
//      }
    }

//    JRadioButton button = (JRadioButton) scoreSelection.get(allScores.get(0));
//    scoreSelectionButtons.clearSelection();
//    button.setSelected(true);

    scoreInterface.registerObserver(this);
  }

  /**
   * Get the string corresponding to the selected score.
   * @return Selected score.
   */
  public String getSelected() {
    return scoreSelectionButtons.getSelection().getActionCommand();
  }

  public void setCurrentPlayer(String currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  /**
   * Updates the current score selection based on current players available scores.
   *
   */
  @Override
  public void update(ScoreInterface subjectRef) {
    for (JLabel label : scoreSelection.values()) {
      label.setEnabled(false);
    }

    String playerName = currentPlayer;
    java.util.List<String> availSelect = subjectRef.getAvailableScores(playerName);

    for (String boxId : availSelect) {
      JLabel label = scoreSelection.get(boxId);
      label.setEnabled(true);
    }

    this.validate();
    this.repaint();
  }
}
