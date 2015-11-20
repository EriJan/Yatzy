package enjug.erijan.games.yatzy;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan Eriksson on 20/11/15.
 */
public class GameStateInfo implements StateInfo {

  List<StateInfoObserver> observerList;
  StringBuffer stateMessage;

  GameStateInfo() {
    stateMessage = new StringBuffer();
    observerList = new ArrayList<StateInfoObserver>();
  }

  @Override
  public String getStateMessage() {
    return stateMessage.toString();
  }

  @Override
  public void setStateMessage(String stateMessage) {
    this.stateMessage = new StringBuffer(stateMessage);
  }

  @Override
  public void registerObserver(StateInfoObserver o) {
    observerList.add(o);
  }

  @Override
  public void removeObserver(StateInfoObserver o) {
    observerList.remove(o);
  }

  @Override
  public void notifyObservers() {
    for(StateInfoObserver o : observerList) {
      o.update(this);
    }
  }
}
