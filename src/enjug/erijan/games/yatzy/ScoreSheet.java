package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.ScoreBox;

import java.util.*;


/** Score sheet for any kind of Yatzy game
 *
 *
 *
 * Created by Jan Eriksson on 23/11/15.
 */

public class ScoreSheet {
  private HashMap<String, HashMap> scoreSheetMap;

  private HashMap<String, String> derivedScores;
  private List<String> sumRange;
  private List<String> totalRange;
  
  private String sumKey;
  private String bonusKey;
  private String totalKey;

  public ScoreSheet(HashMap derivedScores, List sumRange, List totalRange) {
    this.derivedScores = derivedScores;
    this.sumRange = sumRange;
    this.totalRange = totalRange;
    
    sumKey = (String) derivedScores.get("sum");
    bonusKey = (String) derivedScores.get("bonus");
    totalKey = (String) derivedScores.get("total");
    
    scoreSheetMap = new HashMap<String,HashMap>();
  }

  public void addPlayer(String playerName, HashMap scoreColumn) {
    scoreSheetMap.put(playerName,scoreColumn);
  }

  public void setScore(String playerName, String boxId, int... result) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    ScoreBox scoreBox = (ScoreBox) scoreColumn.get(boxId);
    if (!scoreBox.isScoreSet()) {
      scoreBox.setScore(result);
      setSum(playerName);
    }
  }

  public void setTempScores(String playerName, List<String> availableScores, int... result) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    for (String boxId : availableScores) {
      ScoreBox scoreBox = (ScoreBox) scoreColumn.get(boxId);
      scoreBox.setTempScore(result);
    }
  }

  public void clearTempScores(String playerName) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    Iterator<ScoreBox> scoreBoxIterator = scoreColumn.values().iterator();
    while (scoreBoxIterator.hasNext()) {
      ScoreBox scoreBox = scoreBoxIterator.next();
      if (!scoreBox.isDerivedScore()) {
        scoreBox.setTempScore(0);
      }
    }
  }

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
  }

  public int getScore(String playerName, String boxId) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    ScoreBox localBox = (ScoreBox) scoreColumn.get(boxId);
    return localBox.getScore();
  }

  public int getTotal(String playerName) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    ScoreBox localBox = (ScoreBox) scoreColumn.get(totalKey);
    return localBox.getScore();
  }

  public int getTempScore(String playerName, String scoreBox) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    ScoreBox localBox = (ScoreBox) scoreColumn.get(scoreBox);
    return localBox.getTempScore();
  }

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

  public boolean isScoreSet(String playerName, String scoreBox) {
    HashMap scoreColumn = scoreSheetMap.get(playerName);
    ScoreBox localBox = (ScoreBox) scoreColumn.get(scoreBox);
    return localBox.isScoreSet();
  }

  public boolean isDerivedScore(String boxId) {
    return derivedScores.containsValue(boxId);
  }
}
