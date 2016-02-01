package enjug.erijan.games.yatzy.view;

import enjug.erijan.games.util.GenericObserver;
import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.*;
import enjug.erijan.games.yatzy.control.GameControl;
import enjug.erijan.games.yatzy.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * <p>
 * GUI wrapper for any kind of Yatzy game.
 * Built from four separate panels: Score, Dice, Buttons and Info.
  * </p>
 *
 * Created by Jan Eriksson on 03/11/15.
 */

public class YatzyGui implements GenericObserver<GameState> {

  private JFrame jFrame;

  // JPanel objects
  private ScoreColumn selectionColumn;
  private PlayerColumn currentPlayerColumn;
  private Map<String,PlayerColumn> playerColumns;
  private DicePanel dicePanel;
//  private ButtonsPanel buttonsPanel;
  private InfoPanel infoPanel;

  // Model and controller references
  private DiceHandler diceHandler;
  private GameControl gameControl;
  private GameState gameState;
  private ScoreInterface scoreInterface;

  // Buttons
  private final Dimension preferedButtonSize = new Dimension(100,30);
  //private JButton rollButton;
  //private JButton scoreButton;
  private JButton newGameButton;

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
    c.gridwidth = 2;
    c.fill = GridBagConstraints.BOTH;
    selectionColumn = new ScoreColumn(scoreInterface);
    selectionColumn.setCurrentPlayer(gameState.getCurrentPlayer().getName());
    jFrame.add(selectionColumn,c);

    c.gridwidth = 1;
    c.gridx = 2;
    playerColumns = new HashMap<>();
    for (Player player : gameState.getPlayers()) {
      currentPlayerColumn = new PlayerColumn(player.getName(), scoreInterface, gameControl);
      playerColumns.put(player.getName(),currentPlayerColumn);
      jFrame.add(currentPlayerColumn,c);
      c.gridx++;
    }

    c.gridy = 1;
    c.gridwidth = 10;
    dicePanel = new DicePanel(gameControl, diceHandler, gameState);
    jFrame.add(dicePanel,c);
    dicePanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (gameState.isRollingAllowed()) {
          dicePanel.moveActiveDice(diceHandler);
          gameControl.rollActiveDice();
          jFrame.pack();
        }
      }
    });

    c.gridx = 2;
    c.gridy = 0;
    c.gridwidth = 3;
    c.fill = GridBagConstraints.BOTH;
    jFrame.add(infoPanel,c);


    jFrame.pack();

    gameState.registerObserver(this);
  }
  /**
   * <p>
   * Unsets current player column before getting next player.
   * Set current player to actual current player according to GameState.
   * Mark current player column.
   * <br>
   * Enable/disable roll and score button.
   * </p>
   *
   * @param subjectRef Game state model.
   */
  @Override
  public void update(GameState subjectRef) {
    currentPlayerColumn.setBorder(BorderFactory.createEmptyBorder());
    MouseListener[] mouseListeners = currentPlayerColumn.getMouseListeners();
    for (MouseListener m: mouseListeners) {
      currentPlayerColumn.removeMouseListener(m);
    }

    String currentPlayer = subjectRef.getCurrentPlayer().getName();
    currentPlayerColumn = playerColumns.get(currentPlayer);
    currentPlayerColumn.setBorder(BorderFactory.createEtchedBorder());
    currentPlayerColumn.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (subjectRef.isScoringAllowed()) {
          String selected = selectionColumn.getSelected();
          gameControl.setScore(selected);
        }
      }
    });

    selectionColumn.setCurrentPlayer(currentPlayer);
  }

  public JButton createRollButton(GameControl gameControl, DiceHandler diceHandler) {
    JButton rollButton = new JButton("Roll");
    rollButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    rollButton.setPreferredSize(preferedButtonSize);

    rollButton.addActionListener(e -> {
      dicePanel.moveActiveDice(diceHandler);
      gameControl.rollActiveDice();
      jFrame.pack();
    });
    return rollButton;
//    jFrame.add(rollButton);
  }

  public JButton createSetScoreButton(GameControl gameControl) {
    JButton scoreButton = new JButton("Set Score");
    scoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    scoreButton.setPreferredSize(preferedButtonSize);
    scoreButton.setEnabled(false);

    scoreButton.addActionListener(e -> {
      scoreButton.setEnabled(false);
      String selected = selectionColumn.getSelected();
      gameControl.setScore(selected);
    });
//    jFrame.add(scoreButton);
    return scoreButton;
  }

  public JButton createNewGameButton() {
    JButton newGameButton = new JButton("New game");
    newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    newGameButton.setPreferredSize(preferedButtonSize);

    newGameButton.addActionListener(e -> {
      jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
      YatzyMain.main(null);

    });
//    jFrame.add(newGameButton);
    return newGameButton;
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
//  private class ButtonsPanel extends JPanel implements GenericObserver<GameState> {
//
//    private final Dimension preferedButtonSize = new Dimension(100,30);
//    private JButton rollButton;
//    private JButton scoreButton;
//    private JButton newGameButton;
//
//
//    public ButtonsPanel() {
//      this.setLayout(new GridLayout(18,1));
//      addRollButton(diceHandler);
//      addSetScoreButton();
//      addNewGameButton();
//      gameState.registerObserver(this);
//      //this.setBorder(BorderFactory.createEtchedBorder());
//    }
//
//    public void addRollButton(DiceHandler diceHandler) {
//      rollButton = new JButton("Roll");
//      rollButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//      rollButton.setPreferredSize(preferedButtonSize);
//
//      rollButton.addActionListener(e -> {
//        dicePanel.moveActiveDice(diceHandler);
//        gameControl.rollActiveDice();
//        jFrame.pack();
//      });
//
//      this.add(rollButton);
//     }
//
//    public void addSetScoreButton() {
//      scoreButton = new JButton("Set Score");
//      scoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//      scoreButton.setPreferredSize(preferedButtonSize);
//      scoreButton.setEnabled(false);
//
//      scoreButton.addActionListener(new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//          scoreButton.setEnabled(false);
//          //JLabel nameLabel = scorePanel.getCurrentPlayerLabel();
//          //nameLabel.setBorder(null);
//          String selected = selectionColumn.getSelected();
//          gameControl.setScore(selected);
//
//          //nameLabel = scorePanel.getCurrentPlayerLabel();
//          //nameLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
//
//        }
//      });
//      this.add(scoreButton);
//    }
//
//    public void addNewGameButton() {
//
//      for (int j = 0; j < 15; j++) {
//        this.add(Box.createRigidArea(preferedButtonSize));
//      }
//
//      newGameButton = new JButton("New game");
//      newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//      newGameButton.setPreferredSize(preferedButtonSize);
//
//      newGameButton.addActionListener(e -> {
//        //RulesetBuilder.YATZY.newGame();
//        jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
//        YatzyMain.main(null);
//
//      });
//      this.add(newGameButton);
//    }
//
//    /**
//     * Enabel/disable roll and score button.
//     * @param gameState Game state model.
//     */
//    @Override
//    public void update(GameState gameState) {
//      rollButton.setEnabled(gameState.isRollingAllowed());
//      scoreButton.setEnabled(gameState.isScoringAllowed());
//    }
//  }

}
