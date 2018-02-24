package FreeCell.hw02;

/**
 * Created by spoonsrattling on 9/22/16.
 */
public class Card {
  public Suit suit;
  public CardValue value;

  /**
   * Constructs a card with this suit and value.
   * @param suit the Suit.
   * @param value the CardValue.
   */
  public Card(Suit suit, CardValue value) {
    this.suit = suit;
    this.value = value;
  }

  /**
   * Returns TRUE if and only if this card has a different color and value 1 less than other.
   * @param other The card in a CASCADE this card wants to move to.
   * @return True if the card can move.
   */
  public boolean canMoveCascade(Card other) {
    return !this.suit.sameColor(other.suit) && (this.value.getVal() == (other.value.getVal() - 1));
  }

  /**
   * Returns TRUE if and only if this card has the same color and value 1 greater than other.
   * @param other The card in a FOUNDATION this card wants to move to.
   * @return True if the card can move.
   */
  public boolean canMoveFoundation(Card other) {
    return (this.suit.equals(other.suit)) && (this.value.getVal() == (other.value.getVal() + 1));
  }

  /**
   * True if these cards are the same.
   * @param other The other card to compare.
   * @return True if they are equal.
   */
  public boolean cardEquals(Card other) {
    return (this.suit == other.suit) && (this.value == other.value);
  }

  /**
   * Prints the string representation of this Card.
   * @return a String representing this card.
   */
  @Override
  public String toString() {
    return value.toString() + suit.toString();
  }


}
