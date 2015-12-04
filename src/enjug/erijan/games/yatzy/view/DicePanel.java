package enjug.erijan.games.yatzy.view;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.util.GenericObserver;
import enjug.erijan.games.yatzy.control.GameControl;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * <p>
 * Gui representation of the Dice.
 * It is a panel with tow panels inside, one for inactive, saved dice, and one
 * for the rolled dice.
 * </p>
 * Observer to the dice model.
 * <p>
 * Created by Jan Eriksson on 19/11/15.</p>
 */
public class DicePanel extends JPanel implements GenericObserver<DiceHandler> {
  private static final ImageIcon[] selectedDieIcons;
  private static final ImageIcon[] unselectedDieIcons;

  private JPanel diePanel;
  private JPanel selectedDicePanel;
  private List<GuiDie> dieButtons;

  /**
   * Static block where dice images are resized and assigned to array of ImageIcons.
   */
  static {
    selectedDieIcons = new ImageIcon[6];
    unselectedDieIcons = new ImageIcon[6];
    for (int i = 1; i <= 6; i++) {
      ImageIcon tmpIcon = new ImageIcon(YatzyGui.class.getResource
          ("DiceIcons/d6-" + i + ".png"));
      Image image = tmpIcon.getImage();
      Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
      unselectedDieIcons[i-1] = new ImageIcon(newimg);

      ImageIcon tmpIconAlt = new ImageIcon(YatzyGui.class.getResource
          ("DiceIcons/d6-" + i + "-bow.png"));
      Image imageAlt = tmpIconAlt.getImage();
      Image newimgAlt = imageAlt.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
      selectedDieIcons[i-1] = new ImageIcon(newimgAlt);
    }
  }

  /**
   * Create dice representation and panels.
   *
   * @param gameControl Controller.
   * @param diceHandler Dice model.
   */
  public DicePanel(GameControl gameControl, DiceHandler diceHandler) {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    dieButtons = new ArrayList<>();
    diceHandler.registerObserver(this);
    addDice(gameControl, diceHandler);
  }

  /**
   * Create the dice representation and panels.
   *
   * @param gameControl Game controller.
   * @param diceHandler Dice model.
   */
  public void addDice(GameControl gameControl, DiceHandler diceHandler) {
    diePanel = new JPanel();
    selectedDicePanel = new JPanel();

    selectedDicePanel.setPreferredSize(new Dimension(600,60));
    diePanel.setPreferredSize(new Dimension(600,600));
    Dimension prefSize = new Dimension(60,60);


    Iterator<GameDie> dice = diceHandler.getDice();
    diePanel.setLayout(new GridBagLayout());

    while (dice.hasNext()) {

      GameDie die = dice.next();
      ImageIcon imageIcon = unselectedDieIcons[die.getFace() - 1];

      JButton button = new JButton(imageIcon);
      button.addActionListener(e -> gameControl.toggleActiveDie(die));
      button.setOpaque(false);
      button.setContentAreaFilled(false);
      button.setBorderPainted(false);
      button.setPreferredSize(prefSize);
      dieButtons.add(new GuiDie(die,button,new int[] {0,0}));
      selectedDicePanel.add(button);
    }

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = i;
        c.gridy = j;
        c.anchor = GridBagConstraints.CENTER;
        diePanel.add(Box.createRigidArea(prefSize),c);

      }
    }
    this.add(selectedDicePanel);
    this.add(diePanel);

    selectedDicePanel.setVisible(true);
  }

  /**
   * Called when there is an update to any die.
   * @param diceHandler Dice model.
   */
  public void updateDice(DiceHandler diceHandler) {
    Collections.sort(dieButtons);
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
        imageIcon = unselectedDieIcons[die.getFace() - 1];

        diePanel.remove(jButton);
        selectedDicePanel.add(jButton);
      }

      jButton.setIcon(imageIcon);
    }
    diePanel.validate();
    diePanel.repaint();
    selectedDicePanel.validate();
    selectedDicePanel.repaint();
  }

  /**
   * Move dice around the panel to get some kind of rolling illusion.
   *
   * @param diceHandler Dice model.
   */
  // TODO nicer effect when moving dice would be to scatter from a point.
  public void moveActiveDice(DiceHandler diceHandler) {

    Iterator<GameDie> dice = diceHandler.getDice();
    Integer[] allowedPosArr = {0,1,2,3,4,5,6,7,8,9};
    ArrayList colRands = new ArrayList<Integer>(Arrays.asList(allowedPosArr));
    Collections.shuffle(colRands);
    ArrayList rowRands = new ArrayList<Integer>(Arrays.asList(allowedPosArr));
    Collections.shuffle(rowRands);

    int rowPicker = 0;
    int colPicker = 0;

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

  /**
   * Update called from Subject.
   * @param diceHandler Dice model.
   */
  @Override
  public void update(DiceHandler diceHandler) {
     updateDice(diceHandler);
   }


  /**
   * Nested class representing a visual die.
   * Keeps track of position to make movement between inactive panel
   * and rolling area run smoothly. Implements Comparable for sorting
   * of the saved, inActive dice.
   */
  private class GuiDie implements Comparable<GuiDie>{
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

    @Override
    public int compareTo(GuiDie o) {
      return this.die.getFace() - o.getDie().getFace();
    }
  }

}
