package FreeCell.hw02;

/**
 * Created by spoonsrattling on 9/27/16.
 */
public enum CardValue {
  ACE("A", 1), TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), SIX("6", 6), SEVEN("7", 7),
  EIGHT("8", 8), NINE("9", 9), TEN("10", 10), JACK("J", 11), QUEEN("Q", 12), KING("K", 13);

  private final String symbol;
  private final int val;

  CardValue(String symbol, int val) {
    this.symbol = symbol;
    this.val = val;
  }


  @Override
  public String toString() {
    return symbol;
  }

  public int getVal() {
    return val;
  }



}
