package enjug.erijan.games.yatzy.model;

import enjug.erijan.games.yatzy.rules.ScoreBox;

import java.util.*;


/**
 * Score sheet for any kind of Yatzy game
 *
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

  private List<ScoreObserver> observerList;

  public ScoreSheet(List allScores, HashMap derivedScores, List sumRange, List totalRange) {
    this.allScores = allScores;
    this.derivedScores = derivedScores;
    this.sumRange = sumRange;
    this.totalRange = totalRange;
    
    sumKey = (String) derivedScores.get("sum");
    bonusKey = (String) derivedScores.get("bonus");
    totalKey = (String) derivedScores.get("total");
    
    scoreSheetMap = new HashMap<String,HashMap>();

    observerList = new ArrayList<>();
  }

  @Override
  public void addPlayer(String playerName, HashMap scoreColumn) {
    scoreSheetMap.put(playerName,scoreColumn);
  }

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

  // Observer methods

  @Override
  public void registerObserver(ScoreObserver o) {
    observerList.add(o);
  }

  @Override
  public void removeObserver(ScoreObserver o) {
    observerList.remove(o);
  }

  @Override
  public void notifyObservers() {
    for (ScoreObserver o : observerList) {
      o.update(this);
    }
  }
}
