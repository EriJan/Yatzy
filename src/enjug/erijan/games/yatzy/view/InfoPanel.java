package enjug.erijan.games.yatzy.view;

import enjug.erijan.games.util.GenericObserver;
import enjug.erijan.games.yatzy.model.GameState;

import javax.swing.*;

/**
 * Listen to gamestate and display current game message.
 *
 * @author Jan Eriksson
 * @Version 1.0
 * @since 10/12/15
 */
public class InfoPanel extends JPanel implements GenericObserver<GameState> {

  JLabel jLabel;

  public InfoPanel(GameState gameState) {
    jLabel = new JLabel("Lets play some Yatzy!");
    this.add(jLabel);
    gameState.registerObserver(this);
  }


  @Override
  public void update(GameState subjectRef) {
    jLabel.setText(subjectRef.getStateMessage());
    if (subjectRef.isGameEnd()) {
      String winner = subjectRef.getWinner();
      YatzyGui.gameMessage("Game ends with " + winner + " as the winner!");
    }
  }
}

