package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class YatzyAgent implements YatzyAgentInterface {

  private static final int noOfReRolls = 2;
  private int rollsDone;

  //private Class<T> boxTypes;
  private YatzyVariants yatzyVariant;

  private DiceHandler dice;
  private List<Player> players;
  private List<ScoreModel> scoreColumns;

  private Iterator<ScoreModel> turnIterator;
  private ScoreModel activeScoreColumn;

  private ScoreSelectionBehavior scoreSelectionBehavior;
  private RollingControlling rollingControlling;

  public YatzyAgent() {
     newGame();
  }

  private static YatzyVariants selectGameVariant() {
    String[] ruleSets;
    ruleSets = Stream.of(YatzyVariants.values()).map(YatzyVariants::name)
        .toArray(String[]::new);
    int retVal = YatzyGui.userInputFromMenu("What ruleset?", ruleSets);
    return YatzyVariants.valueOf(ruleSets[retVal]);
  }


  @Override
  public void newGame() {
    yatzyVariant = selectGameVariant();
    dice = yatzyVariant.getDice();

    addPlayers();
    turnIterator = scoreColumns.listIterator();
    activeScoreColumn = turnIterator.next();

    YatzyGui yatzyGui = yatzyVariant.getGui(this, dice);
    dice.deActivateAllDice();
  }

  @Override
  public void newGame(JFrame oldFrame) {
    oldFrame.dispatchEvent(new WindowEvent(oldFrame, WindowEvent.WINDOW_CLOSING));
    newGame();
  }

  //TODO check for uniqe names
  private void addPlayers() {
    players = new ArrayList<Player>();
    scoreColumns = new ArrayList<ScoreModel>();

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
        scoreColumns.add(yatzyVariant.getScoreModel(newPlayer));
        playerCounter++;
      }
    }
  }

  @Override
  public void toggleActiveDie(GameDie die) {
    dice.toggleActiveDie(die);
  }

  @Override
  public String setScore(Enum targetBox) {
    String messageString = "";
    if (rollsDone == 0) {
      messageString = "You have to roll at least once.";
    } else if (!activeScoreColumn.isScoreSet(targetBox)) {
      activeScoreColumn.clearTempScores();
      activeScoreColumn.setResult(targetBox, dice.getValues());
      dice.deActivateAllDice();
      rollsDone = 0;
      if (turnIterator.hasNext()) {
        activeScoreColumn = turnIterator.next();
      } else {
        if (activeScoreColumn.isAllScoreSet()) {
          messageString = getWinner() + " wins the game!";
          YatzyGui.gameMessage(messageString);
        } else {
          turnIterator = scoreColumns.listIterator();
          activeScoreColumn = (ScoreModel) turnIterator.next();
        }
      }
      messageString = "Score set on " + targetBox.name();

    } else {
      messageString = "This score is already set, try again";
    }
    return messageString;
  }

  @Override
  public String getWinner() {
    String winner = "";
    int highScore = 0;
    for (ScoreModel scoreModel: scoreColumns) {
      int totScore = scoreModel.getTotal();
      if (totScore > highScore) {
        highScore = totScore;
        winner = scoreModel.getPlayer().getName();
      }
    }
    return winner;
  }

  @Override
  public int rollsLeft() {
    return noOfReRolls - rollsDone;
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
  public String rollActiveDice() {
    String message;
    if (rollsDone == 0) {
      dice.setAllDiceActive();
      rollsDone++;
      dice.rollActiveDice();
      setTempScore();
      message = activeScoreColumn.getPlayer().getName() + " rolled some dice.";

    } else if (rollsDone < noOfReRolls) {
      rollsDone++;
      dice.rollActiveDice();
      setTempScore();
      message = activeScoreColumn.getPlayer().getName() + " rolled some dice.";

    } else if (rollsDone == noOfReRolls) {
      rollsDone++;
      dice.rollActiveDice();
      setTempScore();
      message = activeScoreColumn.getPlayer().getName() + " rolled some dice.";
      dice.deActivateAllDice();

    } else {
      message = "No more rolls alowed.";
    }
    return message;
  }



  @Override
  public void setTempScore() {
    activeScoreColumn.setTempScores(dice.getValues());
  }

  public void setScoreSelectionBehavior(ScoreSelectionBehavior scoreSelectionBehavior) {
    this.scoreSelectionBehavior = scoreSelectionBehavior;
  }

  public void setRollingControlling(RollingControlling rollingControlling) {
    this.rollingControlling = rollingControlling;
  }
}
