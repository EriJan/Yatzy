package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;

import java.util.*;

/**
 * Created by Janne on 27/10/15.
 */
public class YatzyAgent implements YatzyAgentInterface {
  private DiceHandler dice;
  private List players;

  private Iterator turnIterator;
  private List scoreColumns;
  private ScoreModel activeScoreColumn;
  private static final int noOfRolls = 3;
  private int rollsDone;

  public YatzyAgent() {
    players = new ArrayList<Player>();
    scoreColumns = new ArrayList<ScoreModel>();
    dice = new YatzyDice();
    addPlayers();
    turnIterator = scoreColumns.listIterator();
    //activePlayer = (Player) turnIterator.next();
    activeScoreColumn = (ScoreModel) turnIterator.next();
    YatzyGui yatzyGui = new YatzyGui(this,dice);
  }

  private void addPlayers() {
    int playerCounter = 1;
    boolean morePlayers = true;
    while (morePlayers) {
      String playerInputStr = YatzyGui.userInput(
          "What is the name of player " + playerCounter + " ?");

      if ( playerInputStr.isEmpty()) {
        if (playerCounter > 1) {
          morePlayers = false;
        } else {
          YatzyGui.gameMessage("Please add at least one player.");
        }
      } else {
        Player newPlayer = new Player(playerInputStr);
        players.add(newPlayer);
        scoreColumns.add(new YatzyScoreModel(newPlayer));
        playerCounter++;
      }
    }
  }

  @Override
  public String rollActiveDice() {
    String message;
    if (rollsDone <= noOfRolls) {
      rollsDone++;
      dice.rollActiveDice();
      setTempScore();
      message = activeScoreColumn.getPlayer().getName() + " rolled some dice.";
    } else {
      message = "No more rolls alowed.";
    }
    return message;
  }

  @Override
  public void toggleActiveDie(GameDie die) {
    dice.toggleActiveDie(die);
  }

  @Override
  public String setScore(Enum targetBox) {
    String messageString = "";
    if (!activeScoreColumn.isScoreSet(targetBox)) {
      activeScoreColumn.clearTempScores();
      activeScoreColumn.setResult(targetBox, dice.getValues());
      dice.setAllDiceActive();
      rollsDone = 0;

      if (turnIterator.hasNext()) {
        activeScoreColumn = (ScoreModel) turnIterator.next();
      } else {
        turnIterator = scoreColumns.listIterator();
        activeScoreColumn = (ScoreModel) turnIterator.next();
      }

      messageString = "Score set on " + targetBox.name();
    } else {
      messageString = "This score is already set, try again";
    }
    return messageString;
  }

  @Override
  public int rollsLeft() {
    return noOfRolls - rollsDone;
  }

  @Override
  public void yourTurn(DiceHandler dice) {
    this.dice = dice;

  }

  @Override
  public ScoreModel getActiveScoreColumn() {
    return activeScoreColumn;
  }

  @Override
  public Iterator getScoreColumns() {
    return scoreColumns.listIterator();
  }

  @Override
  public void setTempScore() {
    Iterator<GameDie> dieIterator = dice.getDice();
    int[] result = new int[5];
    int i = 0;
    while (dieIterator.hasNext()) {
      GameDie die = dieIterator.next();
      result[i] = die.getSideUp();
      i++;
    }
    activeScoreColumn.setTempScores(result);
  }
}
