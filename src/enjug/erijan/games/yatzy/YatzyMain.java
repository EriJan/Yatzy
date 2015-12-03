package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.view.YatzyGui;

import javax.swing.*;
import java.util.EnumSet;
import java.util.stream.Stream;

/**
 * Created by Jan Eriksson on 08/11/15.
 */
public class YatzyMain {

  private static RulesetBuilder selectGameVariant() {
    EnumSet<RulesetBuilder> ruleSets = EnumSet.allOf(RulesetBuilder.class);
    RulesetBuilder[] ruleSetsArr = ruleSets.toArray(new RulesetBuilder[ruleSets.size()]);

    String[] ruleSetStrings;
    ruleSetStrings = Stream.of(RulesetBuilder.values()).map(RulesetBuilder::toString)
        .toArray(String[]::new);

    int retVal = YatzyGui.userInputFromMenu("What ruleset?", ruleSetStrings);
    return ruleSetsArr[retVal];
  }

  //  TODO mer meddelanden till användaren
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
    } catch (Exception e) {
      e.printStackTrace();
    }

    RulesetBuilder ruleSet = selectGameVariant();
    ruleSet.createAndPopulateGame();
  }
}
