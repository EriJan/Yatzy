package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.rules.ScoreRule;

import javax.swing.*;
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
  private YatzyAgentInterface yatzyAgent;

  /**
   * Constructor to YatzyGui
   *
   * @param yatzyAgent
   * @param diceHandler
   */
  public YatzyGui(Class<T> boxTypes, YatzyAgentInterface yatzyAgent, DiceHandler diceHandler) {
    boxClass = boxTypes;
    yatzyBoxTypes = EnumSet.allOf(boxTypes);


    jFrame = new JFrame("Yatzy");
    jFrame.setLayout(new GridBagLayout());
    jFrame.setVisible(true);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    infoLabel = new JLabel();

    this.yatzyAgent = yatzyAgent;
    this.diceHandler = diceHandler;

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    scorePanel = new ScorePanel<T>(boxClass,yatzyAgent);
    jFrame.add(scorePanel,c);

    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 0;

    buttonsPanel = new ButtonsPanel();
    jFrame.add(buttonsPanel,c);

    c = new GridBagConstraints();
    c.gridx = 2;
    c.gridy = 0;
    dicePanel = new DicePanel(yatzyAgent, diceHandler);
    jFrame.add(dicePanel,c);

    addInfoText("Lets play some Yatzy");
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
      button.addActionListener(e -> {
        dicePanel.moveActiveDice(diceHandler);
        String message = yatzyAgent.rollActiveDice();
        updateInfoText(message);
      });

      this.add(button);
     }

    public void addSetScoreButton() {
      JButton button = new JButton("Set Score");
      button.setAlignmentX(Component.CENTER_ALIGNMENT);

      button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

          JLabel nameLabel = scorePanel.getCurrentPlayerLabel();
          nameLabel.setBorder(null);

          String messageString;
          Enum selected = scorePanel.getSelected();
          messageString = yatzyAgent.setScore(selected);
          updateInfoText(messageString);

          nameLabel = scorePanel.getCurrentPlayerLabel();
          nameLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        }
      });
      this.add(button);
    }

    public void addNewGameButton() {
      JButton jButton = new JButton("New game");
      jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      jButton.addActionListener(e -> {
        yatzyAgent.newGame(jFrame);
      });
      this.add(jButton);
    }
  }


}
