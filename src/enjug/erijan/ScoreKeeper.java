package enjug.erijan;

/**
 * Created by Janne on 29/10/15.
 */
public interface ScoreKeeper<E extends ScoreBoxFactory> {
  void enterResult(E rule, int... result);
}
