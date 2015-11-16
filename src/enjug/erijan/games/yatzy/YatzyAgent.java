package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.rules.YatzyBoxTypes;
import enjug.erijan.games.yatzy.rules.YatzyBoxTypesVariant;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.*;

/**
 * Created by Jan Eriksson on 27/10/15.
 */
public class YatzyAgent implements YatzyAgentInterface {
  private DiceHandler dice;
  private List<Player> players;

  private Iterator turnIterator;
  private List<ScoreModel> scoreColumns;
  private ScoreModel activeScoreColumn;
  private static final int noOfReRolls = 2;
  private int rollsDone;

  public YatzyAgent() {
    newGame();
  }

  @Override
  public void newGame() {
    players = new ArrayList<Player>();
    scoreColumns = new ArrayList<ScoreModel>();
    dice = new YatzyDice();
    addPlayers();
    turnIterator = scoreColumns.listIterator();
    activeScoreColumn = (ScoreModel) turnIterator.next();
    YatzyGui<YatzyBoxTypesVariant> yatzyGui = new YatzyGui(YatzyBoxTypesVariant.class,this,dice);
    dice.deActivateAllDice();
  }

  @Override
  public void newGame(JFrame oldFrame) {
    oldFrame.dispatchEvent(new WindowEvent(oldFrame, WindowEvent.WINDOW_CLOSING));
    newGame();
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
        activeScoreColumn = (ScoreModel) turnIterator.next();
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
      int totScore = scoreModel.getScore(YatzyBoxTypes.TOTAL);
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
  public void setTempScore() {
    Iterator<GameDie> dieIterator = dice.getDice();
    int[] result = new int[5];
    int i = 0;
    while (dieIterator.hasNext()) {
      GameDie die = dieIterator.next();
      result[i] = die.getFace();
      i++;
    }
    activeScoreColumn.setTempScores(result);
  }
}
