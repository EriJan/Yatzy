package enjug.erijan.games.yatzy.view;

import enjug.erijan.games.yatzy.GameControl;
import enjug.erijan.games.yatzy.ScoreColumn;
import enjug.erijan.games.yatzy.ScoreObserver;
import enjug.erijan.games.yatzy.rules.ScoreRule;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Jan Eriksson on 19/11/15.
 */
public class ScorePanel<T extends Enum<T> & ScoreRule> extends JPanel implements ScoreObserver {
  private Class<T> boxClass;
  private EnumSet<T> yatzyBoxTypes;
  private Map scoreColumnPerPlayer;
  private Map playerLabels;
  private Map scoreSelection;
  private ButtonGroup scoreSelectionButtons;
  private GameControl yatzyAgent;

  public ScorePanel(Class<T> boxClass, GameControl yatzyAgent) {
    this.boxClass = boxClass;
    yatzyBoxTypes = EnumSet.allOf(boxClass);
    this.yatzyAgent = yatzyAgent;

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
