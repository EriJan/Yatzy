package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.rules.MaxiYatzyBoxes;
import enjug.erijan.games.yatzy.rules.ScoreBox;
import enjug.erijan.games.yatzy.rules.YatzyBoxes;
import enjug.erijan.games.yatzy.view.YatzyGui;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Jan Eriksson on 29/10/15.
 */
public enum RulesetFactory implements VariantFactory {
  YATZY {
    @Override
    public GameState createGameState() {

      HashMap<String, String> derivedScores = new HashMap<>();
      derivedScores.put("sum",YatzyBoxes.SUM.toString());
      derivedScores.put("bonus",YatzyBoxes.BONUS.toString());
      derivedScores.put("total",YatzyBoxes.TOTAL.toString());

      EnumSet<YatzyBoxes> derivedScoresSet = EnumSet.of(YatzyBoxes.SUM,
          YatzyBoxes.BONUS, YatzyBoxes.TOTAL);
      EnumSet<YatzyBoxes> sumRangeSet = EnumSet.range(YatzyBoxes.ONES,
          YatzyBoxes.SIXES);
      EnumSet<YatzyBoxes> totalRangeSet = EnumSet.range(YatzyBoxes.SUM,
          YatzyBoxes.YATZY);

      ArrayList<String> sumRange = enumSetToStringList(sumRangeSet);
      ArrayList<String> totalRange = enumSetToStringList(totalRangeSet);
      // From start availableScores contains all scores
      ArrayList<String> allScores =
          enumSetToStringList(EnumSet.allOf(YatzyBoxes.class));

      ScoreSheet scoreSheet = new ScoreSheet(derivedScores, sumRange, totalRange);

      ArrayList<Player> playerList = addPlayers();
      for (Player player : playerList) {
        HashMap<String, ScoreBox> scoreColumn = new HashMap<>();
        for (YatzyBoxes boxId : YatzyBoxes.values()) {
          ScoreBox newBox = boxId.getScoreBox();
          if (derivedScoresSet.contains(boxId)) {
            newBox.setDerivedScore(true);
          }
          scoreColumn.put(boxId.toString(), newBox);
        }
        scoreSheet.addPlayer(player.getName(), scoreColumn);
      }

      GameState gameState = new GameState(scoreSheet, playerList, allScores);

      return gameState;
    }

    @Override
    public DiceHandler createDice() {
      return new DiceHandlerImpl(5);
    }

    @Override
    public GameControl createGameControl(GameState gameState, DiceHandler diceHandler) {
      Scoring scoring = new SelectiveScoreSelection(gameState);
      RollControl rollControl = new RollControlYatzy(gameState, diceHandler);
      GameControl gameControl = new GameControl(scoring,rollControl);
      return gameControl;
    }

    @Override
    public YatzyGui getGui(GameControl yatzyAgent, DiceHandler dice, GameState stateInfo) {
      return new YatzyGui(yatzyAgent, dice, stateInfo);
    }
  };
//  YATZEE {
//    @Override
//    public ScoreSheet createScoreSheet() {
//      return null;
//    }
//
//    @Override
//    public DiceHandler createDice() {
//      return new DiceHandlerImpl(5);
//    }
//
//    @Override
//    public YatzyGui getGui(GameControl yatzyAgent, DiceHandler dice, StateInfo stateInfo) {
//      return null;
//    }
//  },
//  MAXI_YATZY {
//    @Override
//    public ScoreSheet createScoreSheet() {
//      return null;
//    }
//
//    @Override
//    public DiceHandler createDice() {
//      return new DiceHandlerImpl(6);
//    }
//
//    @Override
//    public YatzyGui getGui(GameControl yatzyAgent, DiceHandler dice, StateInfo stateInfo) {
//      return new YatzyGui<MaxiYatzyBoxes>(MaxiYatzyBoxes.class,yatzyAgent,dice,stateInfo);
//    }
//  };stateInfo

  public void newGame() {

    // Create scoresheet
    // Add parameters to scoresheet
    // Add players
    // Create gamestate
    // Add sheet to state
    // Create dice
    // create gamecontrol
    // add sheet and dice to control
    // Create gui
    //

    GameState gameState = createGameState();
    DiceHandler diceHandler = createDice();
    GameControl gameControl = createGameControl(gameState, diceHandler);
    YatzyGui yatzyGui = getGui(gameControl,diceHandler, gameState);
  }

  public void newGame(JFrame oldFrame) {
    oldFrame.dispatchEvent(new WindowEvent(oldFrame, WindowEvent.WINDOW_CLOSING));
    newGame();
  }

  private static ArrayList addPlayers() {
    ArrayList<Player> playerList = new ArrayList<>();
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
        playerList.add(new Player(playerInputStr));
        playerCounter++;
      }
    }
    return playerList;
  }


  // Takes a generics T enum and converts to ArrayList of String.
  private static <T extends Enum<T>> ArrayList enumSetToStringList(EnumSet<T> enumSet) {
    return enumSet.stream()
        .map(T::toString)
        .collect(Collectors.toCollection(ArrayList::new));
  }
}
