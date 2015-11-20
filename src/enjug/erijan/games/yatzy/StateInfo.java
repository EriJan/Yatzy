package enjug.erijan.games.yatzy;

/**
 * Created by Jan Eriksson on 20/11/15.
 */
public interface StateInfo extends StateInfoSubject {
  String getStateMessage();
  void setStateMessage(String stateMessage);
}
