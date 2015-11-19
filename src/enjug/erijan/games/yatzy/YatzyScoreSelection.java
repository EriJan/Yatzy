package enjug.erijan.games.yatzy;

/**
 * Created by Jan Eriksson on 16/11/15.
 */
public class YatzyScoreSelection implements ScoreSelectionBehavior {
  @Override
  public String setScore(Enum targetBox) {
    String messageString = "";
//    if (rollsDone == 0) {
//      messageString = "You have to roll at least once.";
//    } else if (!activeScoreColumn.isScoreSet(targetBox)) {
//      activeScoreColumn.clearTempScores();
//      activeScoreColumn.setResult(targetBox, dice.getValues());
//      dice.deActivateAllDice();
//      rollsDone = 0;
//      if (turnIterator.hasNext()) {
//        activeScoreColumn = (ScoreModel) turnIterator.next();
//      } else {
//        if (activeScoreColumn.isAllScoreSet()) {
//          messageString = getWinner() + " wins the game!";
//          YatzyGui.gameMessage(messageString);
//        } else {
//          turnIterator = scoreColumns.listIterator();
//          activeScoreColumn = (ScoreModel) turnIterator.next();
//        }
//      }
//      messageString = "Score set on " + targetBox.name();
//
//    } else {
//      messageString = "This score is already set, try again";
//    }
    return messageString;
  }
}
