package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.view.YatzyGui;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.EnumSet;
import java.util.stream.Stream;

/**
 * Created by Jan Eriksson on 08/11/15.
 */
public class YatzyMain {

  private static RulesetFactory selectGameVariant() {
    EnumSet<RulesetFactory> ruleSets = EnumSet.allOf(RulesetFactory.class);
    RulesetFactory[] ruleSetsArr = ruleSets.toArray(new RulesetFactory[ruleSets.size()]);

    String[] ruleSetStrings;
    ruleSetStrings = Stream.of(RulesetFactory.values()).map(RulesetFactory::toString)
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

    RulesetFactory ruleSet = selectGameVariant();
    ruleSet.createAndPopulateGame();
  }
}
