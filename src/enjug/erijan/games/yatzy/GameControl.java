package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.util.GameDie;
import enjug.erijan.games.yatzy.view.YatzyGui;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class GameControl {

  private static final int noOfReRolls = 2;
  private int rollsDone;

  //private Class<T> boxTypes;
  private VariantsFactoryImpl yatzyVariant;

  private DiceHandler dice;
  private List<Player> players;
  private List<ScoreColumn> scoreColumns;

  private Iterator<ScoreColumn> turnIterator;
  private ScoreColumn activeScoreColumn;
  private StateInfo stateInfo;

  private Scoring scoring;
  private RollControl rollControl;

  public GameControl() {
     newGame();
  }

  private static VariantsFactoryImpl selectGameVariant() {
    String[] ruleSets;
    ruleSets = Stream.of(VariantsFactoryImpl.values()).map(VariantsFactoryImpl::name)
        .toArray(String[]::new);
    int retVal = YatzyGui.userInputFromMenu("What ruleset?", ruleSets);
    return VariantsFactoryImpl.valueOf(ruleSets[retVal]);
  }


  public void newGame() {
    yatzyVariant = selectGameVariant();
    dice = yatzyVariant.getDice();
    stateInfo = new GameStateInfo();

    addPlayers();
    turnIterator = scoreColumns.listIterator();
    activeScoreColumn = turnIterator.next();

    YatzyGui yatzyGui = yatzyVariant.getGui(this, dice, stateInfo);
    dice.deActivateAllDice();
  }

  public void newGame(JFrame oldFrame) {
    oldFrame.dispatchEvent(new WindowEvent(oldFrame, WindowEvent.WINDOW_CLOSING));
    newGame();
  }

  private void addPlayers() {
    players = new ArrayList<Player>();
    scoreColumns = new ArrayList<ScoreColumn>();

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

  public void toggleActiveDie(GameDie die) {
    dice.toggleActiveDie(die);
  }

  @Override
  public void setScore(Enum targetBox) {
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
          activeScoreColumn = (ScoreColumn) turnIterator.next();
        }
      }
      messageString = "Score set on " + targetBox.name();

    } else {
      messageString = "This score is already set, try again";
    }
    stateInfo.setStateMessage(messageString);
    //return messageString;
  }

  public String getWinner() {
    String winner = "";
    int highScore = 0;
    for (ScoreReader scoreColumn : scoreColumns) {
      int totScore = scoreColumn.getTotal();
      if (totScore > highScore) {
        highScore = totScore;
        winner = scoreColumn.getPlayer().getName();
      }
    }
    return winner;
  }

  public int rollsLeft() {
    return noOfReRolls - rollsDone;
  }

  public void yourTurn(DiceHandler dice) {
    this.dice = dice;

  }


  public ScoreReader getActiveScoreColumn() {
    return activeScoreColumn;
  }


  public Iterator getScoreColumns() {
    return scoreColumns.listIterator();
  }


  public void rollActiveDice() {
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
    //return message;
    stateInfo.setStateMessage(message);
  }

  public void setTempScore() {
    activeScoreColumn.setTempScores(dice.getValues());
  }

  public void setScoring(Scoring scoring) {
    this.scoring = scoring;
  }

  public void setRollControl(RollControl rollControl) {
    this.rollControl = rollControl;
  }
}
