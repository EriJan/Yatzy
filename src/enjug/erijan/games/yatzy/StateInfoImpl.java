package enjug.erijan.games.yatzy;

/**
 * Created by Jan Eriksson on 20/11/15.
 */
public class StateInfoImpl implements StateInfo {

  StringBuffer stateMessage;

  StateInfoImpl() {
    stateMessage = new StringBuffer();
  }

  @Override
  public String getStateMessage() {
    return stateMessage.toString();
  }

  @Override
  public void setStateMessage(String stateMessage) {
    this.stateMessage = new StringBuffer(stateMessage);
  }
}
