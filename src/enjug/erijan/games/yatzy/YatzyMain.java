package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.rules.YatzyVariants;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Janne on 08/11/15.
 */
public class YatzyMain {
  private static YatzyVariants selectGameVariant() {
    String[] ruleSets;
    ruleSets = Stream.of(YatzyVariants.values()).map(YatzyVariants::name)
        .toArray(String[]::new);
    int retVal = YatzyGui.userInputFromMenu("What ruleset?", ruleSets);
    return YatzyVariants.valueOf(ruleSets[retVal]);
  }

  public static void main(String[] args) {
    YatzyVariants yatzyVariants = selectGameVariant();
    YatzyAgentInterface yatzyAgentInterface = yatzyVariants.create();
  }
}
