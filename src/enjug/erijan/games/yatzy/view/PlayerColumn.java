package enjug.erijan.games.yatzy.view;

import enjug.erijan.games.util.GenericObserver;
import enjug.erijan.games.yatzy.model.GameState;
import enjug.erijan.games.yatzy.model.Player;
import enjug.erijan.games.yatzy.model.ScoreInterface;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A panel with player name and scores.
 *
 * @author Jan Eriksson
 * @Version 1.0
 * @since 07/12/15
 */
public class PlayerColumn extends JPanel implements GenericObserver<ScoreInterface> {

  private String playerName;
  private JLabel playerLabel;
  private Map<String, JComponent> scoreColumn;
  private final Dimension preferedLabelSize = new Dimension(60, 20);

  public PlayerColumn(String playerName, ScoreInterface scoreInterface) {
    List<String> allScores = scoreInterface.getAllScores();
    this.setLayout(new GridLayout(0,1));

    this.playerName = playerName;
    playerLabel = new JLabel(playerName);
//    playerLabel = new JLabel(playerName);

    playerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    playerLabel.setPreferredSize(new Dimension(60,25));
    Font font = playerLabel.getFont();
    Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
    playerLabel.setFont(boldFont);
    this.add(playerLabel);

    scoreColumn = new HashMap<>();
    for (String boxId : allScores) {
      JButton scoreComponent = new JButton(Integer.toString(scoreInterface
          .getScore(playerName, boxId)));

      scoreComponent.setForeground(Color.BLACK);
      scoreComponent.setHorizontalTextPosition(JLabel.CENTER);
      scoreComponent.setHorizontalAlignment(JLabel.CENTER);
      scoreComponent.setBorder(BorderFactory.createEtchedBorder());
      scoreComponent.setPreferredSize(preferedLabelSize);
      this.add(scoreComponent);

      scoreColumn.put(boxId, scoreComponent);
      if (scoreInterface.isDerivedScore(boxId)) {

      } else {

      }
    }
    scoreInterface.registerObserver(this);
  }

  @Override
  public void update(ScoreInterface scoreInterface) {
    List<String> allScores = scoreInterface.getAllScores();
    for (String boxId : allScores) {
      JLabel label = (JLabel) scoreColumn.get(boxId);
      if (scoreInterface.isScoreSet(playerName,boxId)) {
        label.setText(Integer.toString(scoreInterface.getScore(playerName, boxId)));
        label.setForeground(Color.BLACK);
      } else {
        int tmpScore = scoreInterface.getTempScore(playerName,boxId);
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

}
