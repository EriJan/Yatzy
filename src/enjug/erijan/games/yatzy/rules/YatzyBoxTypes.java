package enjug.erijan.games.yatzy.rules;

/**
 * Created by Jan Eriksson on 27/10/15.
 *
 */

public enum YatzyBoxTypes implements ScoreBoxFactory {
  ONES {
    @Override
    public ScoreBox createScoreBox() {
       return new ScoreBox(result -> YatzyRuleBook.sumOfNs(1, result));
    }
  },
  TWOS {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.sumOfNs(2, result));
    }
  },
  THREES {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.sumOfNs(3, result));
    }
  },
  FOURS {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.sumOfNs(4, result));
    }
  },
  FIVES {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.sumOfNs(5, result));
    }
  },
  SIXES {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.sumOfNs(6, result));
    }
  },
  SUM {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(YatzyRuleBook::totalSum);
    }
  },
  BONUS {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.yatzyBonus(63, 50, result));
    }
  },
  ONE_PAIR {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.nSame(2, 6, result));
    }
  },
  TWO_PAIR {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.twoPair(result));
    }
  },
  THREE_OF_SAME {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.nSame(3, 6, result));
    }
  },
  FOUR_OF_SAME {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.nSame(4, 6, result));
    }
  },
  FULL_HOUSE {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.fullHouse(result));
    }
  },
  SMALL_STRAIGHT {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.smallStraight(result));
    }
  },
  BIG_STRAIGHT {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(YatzyRuleBook::bigStraight);
    }
  },
  CHANCE {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(YatzyRuleBook::totalSum);
    }
  },
  YATZY {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.nSame(5, 6, result));
    }
  },
  TOTAL {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(YatzyRuleBook::totalSum);
    }
  }
}
