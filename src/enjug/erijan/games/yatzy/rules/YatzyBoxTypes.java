package enjug.erijan.games.yatzy.rules;

/**
 * Created by Janne on 27/10/15.
 */
public enum YatzyBoxTypes implements ScoreBoxFactory {
  ONES {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new SumOfNsRule(1));
    }
  },
  TWOS {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new SumOfNsRule(2));
    }
  },
  THREES {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new SumOfNsRule(3));
    }
  },
  FOURS {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new SumOfNsRule(4));
    }
  },
  FIVES {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new SumOfNsRule(5));
    }
  },
  SIXES {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new SumOfNsRule(6));
    }
  },
  SUM {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new TotalSumRule());
    }
  },
  BONUS {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new TotalSumRule());
    }
  },
  ONE_PAIR {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new NSameRule(2));
    }
  },
  TWO_PAIR {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new TwoPairRule());
    }
  },
  THREE_OF_SAME {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new NSameRule(3));
    }
  },
  FOUR_OF_SAME {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new NSameRule(4));
    }
  },
  FULL_HOUSE {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new FullHouse());
    }
  },
  SMALL_STRAIGHT {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new SmallStraightRule());
    }
  },
  BIG_STRAIGHT {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new BigStraightRule());
    }
  },
  CHANCE {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new TotalSumRule());
    }
  },
  YATZY {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new YatzyRule());
    }
  },
  TOTAL {
    @Override
    public ScoreBox createScoreBox() {
      return new ScoreBox(new TotalSumRule());
    }
  };
}
