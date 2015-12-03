package enjug.erijan.games.yatzy;

import enjug.erijan.games.util.DiceHandler;
import enjug.erijan.games.yatzy.control.ScoringBehavior;
import enjug.erijan.games.yatzy.control.SelectiveScoreSelection;
import enjug.erijan.games.yatzy.control.*;
import enjug.erijan.games.yatzy.model.*;
import enjug.erijan.games.yatzy.rules.*;
import enjug.erijan.games.yatzy.view.YatzyGui;

import java.util.*;
import java.util.stream.Collectors;

/**
 * One enum value for each defined Yatzy ruleset.
 *
 * Factory method is called on the enum to start a specific game.
 *
 * This is actually a builder.
 *
 * The sequence is the same for all:
 * 1. Define the specific ranges and key Strings for derived scores
 * 2. Create a ScoreSheet from the derived parameters.
 * 3. Add players
 * 4. Create a score column for each player and add to ScoreSheet
 * 5. Create the rest of the model: dice and state
 * 6. Roll and Score behavior are added to GameControl.
 * 7. Return a new Gui populated GUI.
 *
 * Created by Jan Eriksson on 29/10/15.
 */
public enum RulesetBuilder implements RulesetBuilderInterface {
  YATZY {
    @Override
    public YatzyGui createAndPopulateGame() {

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

      ScoreInterface scoreSheet = new ScoreSheet(allScores, derivedScores, sumRange, totalRange);

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

      GameState state = new GameStateImpl(playerList);

      DiceHandler dice = new YatzyDice(5);

      RollBehavior rolling = new RollBehaviorYatzy(state, dice);
      ScoringBehavior scoring = new SelectiveScoreSelection(state, scoreSheet, dice);
      GameControl control = GameControlImpl.getEmptyGameControl();
      control.setRollBehavior(rolling);
      control.setScoringBehavior(scoring);
      return new YatzyGui(control, scoreSheet, dice, state);
    }
  },

  YAHTZEE {
    @Override
    public YatzyGui createAndPopulateGame() {

      HashMap<String, String> derivedScores = new HashMap<>();
      derivedScores.put("sum", YahtzeeBoxes.SUM.toString());
      derivedScores.put("bonus", YahtzeeBoxes.BONUS.toString());
      derivedScores.put("total", YahtzeeBoxes.TOTAL.toString());

      EnumSet<YahtzeeBoxes> derivedScoresSet = EnumSet.of(YahtzeeBoxes.SUM,
          YahtzeeBoxes.BONUS, YahtzeeBoxes.TOTAL);
      EnumSet<YahtzeeBoxes> sumRangeSet = EnumSet.range(YahtzeeBoxes.ONES,
          YahtzeeBoxes.SIXES);
      EnumSet<YahtzeeBoxes> totalRangeSet = EnumSet.range(YahtzeeBoxes.SUM,
          YahtzeeBoxes.YAHTZEE);

      ArrayList<String> sumRange = enumSetToStringList(sumRangeSet);
      ArrayList<String> totalRange = enumSetToStringList(totalRangeSet);
      ArrayList<String> allScores = enumSetToStringList(EnumSet.allOf(YahtzeeBoxes.class));

      ScoreInterface scoreSheet = new ScoreSheet(allScores, derivedScores, sumRange, totalRange);

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

      GameState state = new GameStateImpl(playerList);

      DiceHandler dice = new YatzyDice(5);

      RollBehavior rolling = new RollBehaviorYatzy(state, dice);
      ScoringBehavior scoring = new SelectiveScoreSelection(state, scoreSheet, dice);
      GameControl control = GameControlImpl.getEmptyGameControl();
      control.setRollBehavior(rolling);
      control.setScoringBehavior(scoring);

      return new YatzyGui(control, scoreSheet, dice, state);
    }
  },

  MAXI_YATZY {
    @Override
    public YatzyGui createAndPopulateGame() {
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
      ArrayList<String> allScores = enumSetToStringList(EnumSet.allOf(MaxiYatzyBoxes.class));

      ScoreSheet scoreSheet = new ScoreSheet(allScores, derivedScores, sumRange, totalRange);

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

      GameState state = new GameStateImpl(playerList);

      DiceHandler dice = new YatzyDice(6);

      RollBehavior rolling = new RollBehaviorMaxiYatzy(state, dice);
      ScoringBehavior scoring = new SelectiveScoreSelection(state, scoreSheet, dice);
      GameControl control = GameControlImpl.getEmptyGameControl();
      control.setRollBehavior(rolling);
      control.setScoringBehavior(scoring);

      return new YatzyGui(control, scoreSheet, dice, state);
    }
  };


  private static ArrayList addPlayers() {
    ArrayList<Player> playerList = new ArrayList<>();
    int playerCounter = 1;
    boolean morePlayers = true;
    while (morePlayers) {
      String playerInputStr = YatzyGui.userInput(
          "What is the name of player " + playerCounter + " ?");

      if (playerInputStr.isEmpty()) {
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

  /**
   * Takes a generic enum of type T and converts to ArrayList of String.
   *
   * @param enumSet Set to convert.
   * @param <T> Enum type.
   * @return Returns ArrayList of Strings.
   */

  private static <T extends Enum<T>> ArrayList enumSetToStringList(EnumSet<T> enumSet) {
    return enumSet.stream()
        .map(T::toString)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public String toString() {
    String string;

    switch (this) {
      case YATZY : string = "Yatzy";
        break;
      case MAXI_YATZY : string = "Maxi Yatzy";
        break;
      case YAHTZEE: string = "Yahtzee";
        break;
      default : string = this.name();
        break;
    }

    return string;
  }
}