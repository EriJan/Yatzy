package enjug.erijan.games.yatzy.model;

import enjug.erijan.games.util.GenericObserver;
import enjug.erijan.games.yatzy.rules.ScoreBox;

import java.util.*;


/**
 * Score sheet for any kind of Yatzy game
 *
 * The assumption is to have data represented in a matrix, with player names
 * as column keys and strings representing different score boxes on the rows.
 *
 * The term derived scores is used for sum, bonus and total,
 * since they are derived from a set of other scores.
 * By convention, the exact strings "sum", "bonus" and "total" need to be used as keys in the
 * HashMap defining the actual corresponding keys.
 *
 * The ScoreSheet needs to be created and populated all at the same time, at start of the game.
 * It depends on the usage of ScoreBoxes as representation and calculation of scores.
 *
 * Created by Jan Eriksson on 23/11/15.
 */

public class ScoreSheet implements ScoreInterface {
  private HashMap<String, HashMap> scoreSheetMap;

  private List<String> allScores;
  private HashMap<String, String> derivedScores;
  private List<String> sumRange;
  private List<String> totalRange;
  
  private String sumKey;
  private String bonusKey;
  private String totalKey;

  private List<GenericObserver> observerList;

  /**
   * ScoreSheet constructor need four defining paramters to set up its funktonality.
   * When players are added the same ScoreBox names has to be used.
   *
   * @param allScores A String list of all ScoreBoxes.
   * @param derivedScores A HashMap with the three dervied scores defined.
   * @param sumRange The score boxes used to calculated the bonus yielding sum.
   * @param totalRange The score boxes used to calcualte total score.
   */
  public ScoreSheet(List allScores, HashMap derivedScores, List sumRange, List totalRange) {
    this.allScores = allScores;
    this.derivedScores = derivedScores;
    this.sumRange = sumRange;
    this.totalRange = totalRange;
    
    sumKey = (String) derivedScores.get("sum");
    bonusKey = (String) derivedScores.get("bonus");
    totalKey = (String) derivedScores.get("total");
    
    scoreSheetMap = new HashMap<>();

    observerList = new ArrayList<>();
  }

  /**
   * Add player and a corresponding score column.
   *
   * @param playerName Player name as a String.
   * @param scoreColumn ScoreBoxes in a HashMap.
   */
  @Override
  public void addPlayer(String playerName, HashMap scoreColumn) {
    scoreSheetMap.put(playerName,scoreColumn);
  }


  /**
   *
   * Assign a dice result to a specific ScoreBox.
   *
   * @param playerName Owning player name as String.
   * @param boxId The box name as String.
   * @param result The dice result as an int array.
   */
  @Override
  public void setScore(String playerName, String boxId, int... result) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    ScoreBox scoreBox = (ScoreBox) scoreColumn.get(boxId);
    if (!scoreBox.isScoreSet()) {
      scoreBox.setScore(result);
      setSum(playerName);
    }
    notifyObservers();
  }

  /**
   *
   * Assign a temporary result to all ScoreBoxes owned by a specific player.
   *
   * @param playerName Player name.
   * @param result Result from dice roll.
   */
  @Override
  public void setTempScores(String playerName, int... result) {
    List<String> availableScores = getAvailableScores(playerName);

    HashMap scoreColumn = scoreSheetMap.get(playerName);
    for (String boxId : availableScores) {
      ScoreBox scoreBox = (ScoreBox) scoreColumn.get(boxId);
      scoreBox.setTempScore(result);
    }
    notifyObservers();
  }

  /**
   *
   * Returns the scores that are assignable.
   *
   * @param playerName Player name as String.
   * @return String List of available scores.
   */
  @Override
  public List<String> getAvailableScores(String playerName) {
    List<String> availableScores = new ArrayList<>();
    for (String id : allScores) {
      if (!isScoreSet(playerName, id) && !isDerivedScore(id)) {
        availableScores.add(id);
      }
    }
    return availableScores;
  }

  @Override
  public void clearTempScores(String playerName) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    Iterator<ScoreBox> scoreBoxIterator = scoreColumn.values().iterator();
    while (scoreBoxIterator.hasNext()) {
      ScoreBox scoreBox = scoreBoxIterator.next();
      if (!scoreBox.isDerivedScore()) {
        scoreBox.setTempScore(0);
      }
    }
    notifyObservers();
  }

  /**
   * Collects and assigns sum, bonus and total scores for a specific player.
   * Based on the ranges defined at construction.
   *
   * Total only calculated if all other scores set.
   *
   * @param playerName Name of the specific player.
   */
  private void setSum(String playerName) {
    int localSum = 0;
    ScoreBox scoreBox;
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    for(String boxId : sumRange) {
      scoreBox = (ScoreBox) scoreColumn.get(boxId);
      localSum += scoreBox.getScore();
    }
    scoreBox = (ScoreBox) scoreColumn.get(sumKey);
    scoreBox.setScore(localSum);

    scoreBox = (ScoreBox) scoreColumn.get(bonusKey);
    scoreBox.setScore(localSum);

    localSum = 0;

    if (isAllScoreSet(playerName)) {
      for (Object box : totalRange) {
        scoreBox = (ScoreBox) scoreColumn.get(box);
        localSum += scoreBox.getScore();
      }
      scoreBox = (ScoreBox) scoreColumn.get(totalKey);
      scoreBox.setScore(localSum);
    }
    notifyObservers();
  }

  @Override
  public int getScore(String playerName, String boxId) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    ScoreBox localBox = (ScoreBox) scoreColumn.get(boxId);
    return localBox.getScore();
  }

  @Override
  public int getTotal(String playerName) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    ScoreBox localBox = (ScoreBox) scoreColumn.get(totalKey);
    return localBox.getScore();
  }

  @Override
  public int getTempScore(String playerName, String scoreBox) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    ScoreBox localBox = (ScoreBox) scoreColumn.get(scoreBox);
    return localBox.getTempScore();
  }

  @Override
  public List<String> getAllScores() {
    return allScores;
  }

  @Override
  public boolean isAllScoreSet(String playerName) {
    boolean setScoreFound = true;
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    Iterator scoreKeyIterator = scoreColumn.keySet().iterator();

    // Loop unil a score is found to not be set
    while (scoreKeyIterator.hasNext() && setScoreFound) {
      String key = (String) scoreKeyIterator.next();
      // No check on the derived scores
      if (!derivedScores.containsValue(key)) {
        ScoreBox localBox = (ScoreBox) scoreColumn.get(key);
        setScoreFound = localBox.isScoreSet();
      }
    }
    // setScoreFound will be false as soon as an unset score found
    return setScoreFound;
  }

  @Override
  public boolean isScoreSet(String playerName, String scoreBox) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    ScoreBox localBox = (ScoreBox) scoreColumn.get(scoreBox);
    return localBox.isScoreSet();
  }

  @Override
  public boolean isDerivedScore(String boxId) {
    return derivedScores.containsValue(boxId);
  }

  @Override
  public String getWinner() {
    String winner = "";
    int highScore = 0;
    for (String playerName : scoreSheetMap.keySet()) {
      int totScore = getTotal(playerName);
      if (totScore > highScore) {
        highScore = totScore;
        winner = playerName;
      }
    }
    return winner;
  }

  // GenericObserver methods

  @Override
  public void registerObserver(GenericObserver o) {
    observerList.add(o);
  }

  @Override
  public void removeObserver(GenericObserver o) {
    observerList.remove(o);
  }

  @Override
  public void notifyObservers() {
    for (GenericObserver o : observerList) {
      o.update(this);
    }
  }
}
