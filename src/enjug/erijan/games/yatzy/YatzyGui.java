package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.DiceObserver;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.rules.ScoreRule;
import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Jan Eriksson on 03/11/15.
 */


public class YatzyGui<T extends Enum<T> & ScoreRule> implements ScoreObserver, DiceObserver {

  private static final ImageIcon[] selectedDieIcons;
  private static final ImageIcon[] unselectedDieIcons;
  private EnumSet<T> yatzyBoxTypes;

  private JFrame jFrame;
  private JPanel diePanel;
  private JPanel selectedDicePanel;
  private JLabel infoLabel;
  private YatzyAgentInterface yatzyAgent;
  private Map scoreColumnPerPlayer;
  private Map playerLabels;
  private Map scoreSelection;
  private List dieButtons;
  private ButtonGroup scoreSelectionButtons;


  static {
    //yatzyBoxTypes = EnumSet.allOf(YatzyBoxTypes.class);
    selectedDieIcons = new ImageIcon[6];
    unselectedDieIcons = new ImageIcon[6];
    for (int i = 1; i <= 6; i++) {
      ImageIcon tmpIcon = new ImageIcon(YatzyGui.class.getResource
          ("../util/DiceIcons/d6-" + i + ".png"));
      Image image = tmpIcon.getImage();
      Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
      unselectedDieIcons[i-1] = new ImageIcon(newimg);

      ImageIcon tmpIconAlt = new ImageIcon(YatzyGui.class.getResource
          ("../util/DiceIcons/d6-" + i + "-bow.png"));
      Image imageAlt = tmpIconAlt.getImage();
      Image newimgAlt = imageAlt.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
      selectedDieIcons[i-1] = new ImageIcon(newimgAlt);
    }
  }

  /**
   * Constructor to YatzyGui
   *
   * @param yatzyAgent
   * @param diceHandler
   */
  public YatzyGui(Class<T> boxTypes, YatzyAgentInterface yatzyAgent, DiceHandler diceHandler) {
    diePanel = new JPanel();
    selectedDicePanel = new JPanel();
    jFrame = new JFrame("Yatzy");
    jFrame.setLayout(new GridBagLayout());
    jFrame.setVisible(true);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    infoLabel = new JLabel();
    scoreSelectionButtons = new ButtonGroup();

    playerLabels = new HashMap<String,JLabel>();
    scoreColumnPerPlayer = new HashMap<String,Map>();
    scoreSelection = new EnumMap<T,JRadioButton>(boxTypes);
    dieButtons = new ArrayList<GuiDie>();

    this.yatzyAgent = yatzyAgent;
    addScoreSelection();

    int column = 1;
    Iterator colIterator = yatzyAgent.getScoreColumns();

    while (colIterator.hasNext()) {
      ScoreModel scoreModel = (ScoreModel) colIterator.next();
      addScore(scoreModel, column);
      column++;
      scoreModel.registerObserver(this);
    }
    JLabel jLabel = getCurrentPlayerLabel();
    jLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
    addDice(diceHandler, column + 1);
    addRollButton(diceHandler,column);
    addSetScoreButton(column);
    addNewGameButton(column);
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
        JLabel jLabel = new JLabel(key.name());
        Font font = jLabel.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
        jLabel.setFont(boldFont);
        c.gridx = 0;
        c.gridy = row;
        c.anchor = GridBagConstraints.CENTER;
        jFrame.add(jLabel, c);
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
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    nameLabel.setPreferredSize(new Dimension(60,25));
    Font font = nameLabel.getFont();
    Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
    nameLabel.setFont(boldFont);
    playerLabels.put(name,nameLabel);
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
    //JLabel nameLabel = (JLabel) playerLabels.get(name);
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
          button.setForeground(Color.BLACK);
        }
      }
    }
    jFrame.validate();
    jFrame.repaint();
    //jFrame.pack();
  }

  public void addDice(DiceHandler diceHandler, int column) {

    diePanel.removeAll();
    diePanel.setPreferredSize(new Dimension(500,500));
    Iterator<GameDie> dice = diceHandler.getDice();
    //diePanel.setLayout(new BoxLayout(diePanel,BoxLayout.Y_AXIS));
    diePanel.setLayout(new GridBagLayout());

    Integer[] allowedPosArr = {0,1,2,3,4,5,6,7,8,9};
    ArrayList colRands = new ArrayList<Integer>(Arrays.asList(allowedPosArr));
    Collections.shuffle(colRands);
    ArrayList rowRands = new ArrayList<Integer>(Arrays.asList(allowedPosArr));
    Collections.shuffle(rowRands);

    int rowPicker = 0;
    int colPicker = 0;
    while (dice.hasNext()) {

      int row = (int) rowRands.get(rowPicker);
      int col = (int) colRands.get(colPicker);
      rowPicker++;
      colPicker++;

      GridBagConstraints c = new GridBagConstraints();
      c.gridx = col;
      c.gridy = row;
      c.anchor = GridBagConstraints.CENTER;

      GameDie die = dice.next();
      ImageIcon imageIcon = unselectedDieIcons[die.getFace() - 1];
      JButton button = new JButton(imageIcon);
      button.addActionListener(e -> yatzyAgent.toggleActiveDie(die));

      dieButtons.add(new GuiDie(die,button,new int[] {row,col}));
      diePanel.add(button, c);
    }

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = column;
    c.gridy = 0;
    //c.anchor = GridBagConstraints.CENTER;
    c.gridheight = 0;
    c.gridwidth = 0;
    jFrame.getContentPane().add(diePanel,c);

    c = new GridBagConstraints();
    c.gridx = column - 1;
    c.gridy = 3;
    c.gridheight = 10;
    c.gridwidth = 1;
    selectedDicePanel.setLayout(new GridLayout(5,1));
    jFrame.getContentPane().add(selectedDicePanel,c);

    selectedDicePanel.setVisible(true);
    jFrame.pack();
    jFrame.setVisible(true);
  }

  public void updateDice(DiceHandler diceHandler) {

    for (Object o : dieButtons) {
      GuiDie guiDie = (GuiDie) o;
      GameDie die = guiDie.getDie();
      JButton jButton = guiDie.getjButton();

      ImageIcon imageIcon;

      if (diceHandler.isActiveDie(die)) {
        imageIcon = unselectedDieIcons[die.getFace() - 1];

        int[] cord = guiDie.getLastPostion();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = cord[1];
        c.gridy = cord[0];
        //c.anchor = GridBagConstraints.CENTER;
        selectedDicePanel.remove(jButton);
        diePanel.add(jButton,c);

      } else {
        imageIcon = selectedDieIcons[die.getFace() - 1];

        diePanel.remove(jButton);
        selectedDicePanel.add(jButton);
      }

      jButton.setIcon(imageIcon);
    }
    diePanel.validate();
    diePanel.repaint();
    selectedDicePanel.validate();
    selectedDicePanel.repaint();
    jFrame.pack();
  }

  public void addRollButton(DiceHandler diceHandler, int column) {
    JButton button = new JButton("Roll");
    button.addActionListener(e -> {
      moveActiveDice(diceHandler);
      String message = yatzyAgent.rollActiveDice();
      updateInfoText(message);
    });

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = column;
    c.gridy = 1;
    jFrame.getContentPane().add(button,c);
    jFrame.pack();
  }

  private void moveActiveDice(DiceHandler diceHandler) {

    Iterator<GameDie> dice = diceHandler.getDice();
    Integer[] allowedPosArr = {0,1,2,3,4,5,6,7,8,9};
    ArrayList colRands = new ArrayList<Integer>(Arrays.asList(allowedPosArr));
    Collections.shuffle(colRands);
    ArrayList rowRands = new ArrayList<Integer>(Arrays.asList(allowedPosArr));
    Collections.shuffle(rowRands);

    int rowPicker = 0;
    int colPicker = 0;

    //for (int i = 0; i < dieButtons.size(); i++) {
    for (Object o : dieButtons) {
      GuiDie guiDie = (GuiDie) o;
      GameDie die = guiDie.getDie();
      JButton jButton = guiDie.getjButton();
      //int[] cord = guiDie.getLastPostion();

      int row = (int) rowRands.get(rowPicker);
      int col = (int) colRands.get(colPicker);

      if (diceHandler.isActiveDie(die)) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = col;
        c.gridy = row;
        c.anchor = GridBagConstraints.CENTER;
        diePanel.remove(jButton);
        diePanel.add(jButton,c);

      }

      guiDie.setLastPostion(new int[] {row,col});
      rowPicker++;
      colPicker++;
    }
    diePanel.validate();
    diePanel.repaint();
    selectedDicePanel.validate();
    selectedDicePanel.repaint();
    //jFrame.pack();
  }

  public void addSetScoreButton(int column) {
    JButton button = new JButton("Set Score");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        JLabel nameLabel = getCurrentPlayerLabel();
        nameLabel.setBorder(null);

        String messageString;
        Enum selected = YatzyBoxTypes.
            valueOf(scoreSelectionButtons.getSelection().
                getActionCommand());
        messageString = yatzyAgent.setScore(selected);
        updateInfoText(messageString);

        nameLabel = getCurrentPlayerLabel();
        nameLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());

      }
    });

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = column;
    c.gridy = 2;
    jFrame.getContentPane().add(button,c);
    //jFrame.pack();
  }

  public void addNewGameButton(int column) {
    JButton jButton = new JButton("New game");
    jButton.addActionListener(e -> {
      yatzyAgent.newGame(jFrame);
    });

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = column;
    c.gridy = 17;
    jFrame.getContentPane().add(jButton,c);
  }

  private JLabel getCurrentPlayerLabel() {
    ScoreModel scoreModel = yatzyAgent.getActiveScoreColumn();
    String name = scoreModel.getPlayer().getName();
    return  (JLabel) playerLabels.get(name);
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

  @Override
  public void update(DiceHandler diceHandler) {
    updateDice(diceHandler);
    //addDice(diceHandler, 10);
  }

  @Override
  public void update(ScoreModel scoreModel) {
    updateScore(scoreModel);
    updateScoreSelection(scoreModel);
  }

  private class GuiDie {
    GameDie die;
    JButton jButton;
    int[] lastPostion;

    private GuiDie(GameDie die, JButton jButton, int[] lastPostion) {
      this.die = die;
      this.jButton = jButton;
      this.lastPostion = lastPostion;
    }

    public void setLastPostion(int[] lastPostion) {
      this.lastPostion = lastPostion;
    }

    public void setjButton(JButton jButton) {
      this.jButton = jButton;
    }

    public GameDie getDie() {
      return die;
    }

    public JButton getjButton() {
      return jButton;
    }

    public int[] getLastPostion() {
      return lastPostion;
    }
  }

}
