package FreeCell.hw02;

/**
 * Created by spoonsrattling on 9/25/16.
 */
public enum Suit {


  HEART("red", "♥"),

  DIAMOND("red", "♦"),

  SPADE("black", "♠"),

  CLUB("black", "♣");

  private final String color;
  private final String symbol;

  Suit(String color, String symbol) {
    this.color = color;
    this.symbol = symbol;
  }

  /**
   * Compares the colors of two suits.
   * @param that The other suit.
   * @return true if they have the same color.
   */
  public boolean sameColor(Suit that) {

    return this.color.equals(that.color);

  }

  public String toString() {
    return this.symbol;
  }
}
