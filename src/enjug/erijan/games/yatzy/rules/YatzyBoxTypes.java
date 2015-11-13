package enjug.erijan.games.yatzy.rules;

/**
 * Created by Jan Eriksson on 27/10/15.
 *
 */

public enum YatzyBoxTypes implements ScoreBoxFactory {
  ONES {
    @Override
    public ScoreBox createScoreBox() {
       return new ScoreBox(result -> YatzyRuleBook.SumOfNs(1, result));
    }
  },
  TWOS {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.SumOfNs(2, result));
    }
  },
  THREES {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.SumOfNs(3, result));
    }
  },
  FOURS {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.SumOfNs(4, result));
    }
  },
  FIVES {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.SumOfNs(5, result));
    }
  },
  SIXES {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.SumOfNs(6, result));
    }
  },
  SUM {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(YatzyRuleBook::TotalSum);
    }
  },
  BONUS {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.YatzyBonus(63, 50, result));
    }
  },
  ONE_PAIR {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.NSame(2, 6, result));
    }
  },
  TWO_PAIR {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.TwoPair(result));
    }
  },
  THREE_OF_SAME {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.NSame(3, 6, result));
    }
  },
  FOUR_OF_SAME {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.NSame(4, 6, result));
    }
  },
  FULL_HOUSE {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.FullHouse(result));
    }
  },
  SMALL_STRAIGHT {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.SmallStraight(result));
    }
  },
  BIG_STRAIGHT {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(YatzyRuleBook::BigStraight);
    }
  },
  CHANCE {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(YatzyRuleBook::TotalSum);
    }
  },
  YATZY {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(result -> YatzyRuleBook.NSame(5, 6, result));
    }
  },
  TOTAL {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(YatzyRuleBook::TotalSum);
    }
  }
}
