package enjug.erijan.games.yatzy;

import enjug.erijan.games.yatzy.view.YatzyGui;

import javax.swing.*;
import java.util.stream.Stream;

/**
 * Created by Jan Eriksson on 08/11/15.
 */
public class YatzyMain {


  private static RulesetFactory selectGameVariant() {
    String[] ruleSets;
    ruleSets = Stream.of(RulesetFactory.values()).map(RulesetFactory::name)
        .toArray(String[]::new);
    int retVal = YatzyGui.userInputFromMenu("What ruleset?", ruleSets);
    return RulesetFactory.valueOf(ruleSets[retVal]);
  }


  public static void main(String[] args) {

    try {
      UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
    } catch (Exception e) {
      e.printStackTrace();
    }

    //GameControl agent = new GameControl();
    RulesetFactory ruleSet = selectGameVariant();
    ruleSet.newGame();
  }
}
