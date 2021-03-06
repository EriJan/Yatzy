package enjug.erijan.games.util;

/**
 *
 * This is a enum factory to create
 * different kinds of dice.
 * The Dn classes are defined locally and
 * extends GameDie.
 *
 * Example:
 * To create a 8 sided die, D8:
 * GameDie d8 = DieTypes.D8.create();
 *
 * Created by Jan Eriksson on 27/10/15.
 *
 */
public enum DieTypes implements DieFactory {

  D4 {
    @Override
    public GameDie create() {
      return new D4();
    }
  },

  D6 {
    @Override
    public GameDie create() {
      return new D6();
    }
  },

  D8 {
    @Override
    public GameDie create() {
      return new D8();
    }
  },

  D10 {
    @Override
    public GameDie create() {
      return new D10();
    }
  },

  D12 {
    @Override
    public GameDie create() {
      return new D12();
    }
  },

  D20 {
    @Override
    public GameDie create() {
      return new D20();
    }
  };

  class D4 extends GameDie {
    private D4() {
      super(4);
    }
  }

  class D6 extends GameDie {
    private D6() {
      super(6);
    }
  }

  class D8 extends GameDie {
    private D8() {
      super(8);
    }
  }

  class D10 extends GameDie {
    private D10() {
      super(10);
    }
  }

  class D12 extends GameDie {
    private D12() {
      super(12);
    }
  }

  class D20 extends GameDie {
    private D20() {
      super(20);
    }
  }
}
