package enjug.erijan.games.yatzy.rules;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.control.ScoringBehavior;
import enjug.erijan.games.yatzy.control.SelectiveScoreSelection;
import enjug.erijan.games.yatzy.control.*;
import enjug.erijan.games.yatzy.model.GameState;
import enjug.erijan.games.yatzy.model.Player;
import enjug.erijan.games.yatzy.model.ScoreSheet;
import enjug.erijan.games.yatzy.model.YatzyDice;
import enjug.erijan.games.yatzy.view.YatzyGui;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Jan Eriksson on 29/10/15.
 */
public enum RulesetFactory implements VariantFactory {
  YATZY {
    @Override
    public GameState createGameState() {

      HashMap<String, String> derivedScores = new HashMap<>();
      derivedScores.put("sum", YatzyBoxes.SUM.toString());
      derivedScores.put("bonus", YatzyBoxes.BONUS.toString());
      derivedScores.put("total", YatzyBoxes.TOTAL.toString());

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
      return new YatzyDice(5);
    }

    @Override
    public GameControl createGameControl(GameState gameState, DiceHandler diceHandler) {
      ScoringBehavior scoringBehavior = new SelectiveScoreSelection(gameState);
      RollBehavior rollBehavior = new RollBehaviorYatzy(gameState, diceHandler);
      GameControl gameControl = new GameControlImpl(scoringBehavior, rollBehavior);
      return gameControl;
    }

    @Override
    public YatzyGui getGui(GameControl gameControl, DiceHandler dice, GameState stateInfo) {
      return new YatzyGui(gameControl, dice, stateInfo);
    }
  },

  YATZEE {
    @Override
    public GameState createGameState() {

      HashMap<String, String> derivedScores = new HashMap<>();
      derivedScores.put("sum", YahtzeeBoxes.SUM.toString());
      derivedScores.put("bonus", YahtzeeBoxes.BONUS.toString());
      derivedScores.put("total", YahtzeeBoxes.TOTAL.toString());

      EnumSet<YahtzeeBoxes> derivedScoresSet = EnumSet.of(YahtzeeBoxes.SUM,
          YahtzeeBoxes.BONUS, YahtzeeBoxes.TOTAL);
      EnumSet<YahtzeeBoxes> sumRangeSet = EnumSet.range(YahtzeeBoxes.ONES,
          YahtzeeBoxes.SIXES);
      EnumSet<YahtzeeBoxes> totalRangeSet = EnumSet.range(YahtzeeBoxes.SUM,
          YahtzeeBoxes.YATZY);

      ArrayList<String> sumRange = enumSetToStringList(sumRangeSet);
      ArrayList<String> totalRange = enumSetToStringList(totalRangeSet);
      // From start availableScores contains all scores
      ArrayList<String> allScores =
          enumSetToStringList(EnumSet.allOf(YahtzeeBoxes.class));

      ScoreSheet scoreSheet = new ScoreSheet(derivedScores, sumRange, totalRange);

      ArrayList<Player> playerList = addPlayers();
      for (Player player : playerList) {
        HashMap<String, ScoreBox> scoreColumn = new HashMap<>();
        for (YahtzeeBoxes boxId : YahtzeeBoxes.values()) {
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
      return new YatzyDice(5);
    }

    @Override
    public GameControl createGameControl(GameState gameState, DiceHandler diceHandler) {
      ScoringBehavior scoringBehavior = new SelectiveScoreSelection(gameState);
      RollBehavior rollBehavior = new RollBehaviorYatzy(gameState, diceHandler);
      GameControl gameControl = new GameControlImpl(scoringBehavior, rollBehavior);
      return gameControl;
    }

    @Override
    public YatzyGui getGui(GameControl gameControl, DiceHandler dice, GameState stateInfo) {
      return new YatzyGui(gameControl, dice, stateInfo);
    }
  },
  MAXI_YATZY {

    @Override
    public GameState createGameState() {

      HashMap<String, String> derivedScores = new HashMap<>();
      derivedScores.put("sum", MaxiYatzyBoxes.SUM.toString());
      derivedScores.put("bonus", MaxiYatzyBoxes.BONUS.toString());
      derivedScores.put("total", MaxiYatzyBoxes.TOTAL.toString());

      EnumSet<MaxiYatzyBoxes> derivedScoresSet = EnumSet.of(MaxiYatzyBoxes.SUM,
          MaxiYatzyBoxes.BONUS, MaxiYatzyBoxes.TOTAL);
      EnumSet<MaxiYatzyBoxes> sumRangeSet = EnumSet.range(MaxiYatzyBoxes.ONES,
          MaxiYatzyBoxes.SIXES);
      EnumSet<MaxiYatzyBoxes> totalRangeSet = EnumSet.range(MaxiYatzyBoxes.SUM,
          MaxiYatzyBoxes.YATZY);

      ArrayList<String> sumRange = enumSetToStringList(sumRangeSet);
      ArrayList<String> totalRange = enumSetToStringList(totalRangeSet);
      // From start availableScores contains all scores
      ArrayList<String> allScores =
          enumSetToStringList(EnumSet.allOf(MaxiYatzyBoxes.class));

      ScoreSheet scoreSheet = new ScoreSheet(derivedScores, sumRange, totalRange);

      ArrayList<Player> playerList = addPlayers();
      for (Player player : playerList) {
        HashMap<String, ScoreBox> scoreColumn = new HashMap<>();
        for (MaxiYatzyBoxes boxId : MaxiYatzyBoxes.values()) {
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
      return new YatzyDice(6);
    }

    @Override
    public GameControl createGameControl(GameState gameState, DiceHandler diceHandler) {
      ScoringBehavior scoringBehavior = new SelectiveScoreSelection(gameState);
      RollBehavior rollBehavior = new RollBehaviorMaxiYatzy(gameState, diceHandler);
      GameControl gameControl = new GameControlImpl(scoringBehavior, rollBehavior);
      return gameControl;
    }

    @Override
    public YatzyGui getGui(GameControl gameControl, DiceHandler dice, GameState stateInfo) {
      return new YatzyGui(gameControl,dice,stateInfo);
    }
  };

  public void newGame() {
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
