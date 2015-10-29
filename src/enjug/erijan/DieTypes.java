package enjug.erijan;

/**
 * Created by Janne on 27/10/15.
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
    public D4() {
      super(4);
    }
  }

  class D6 extends GameDie {
    public D6() {
      super(6);
    }
  }

  class D8 extends GameDie {
    public D8() {
      super(8);
    }
  }

  class D10 extends GameDie {
    public D10() {
      super(10);
    }
  }

  class D12 extends GameDie {
    public D12() {
      super(12);
    }
  }

  class D20 extends GameDie {
    public D20() {
      super(20);
    }
  }
}
