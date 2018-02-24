package FreeCell.hw02;

import java.util.ArrayList;

/**
 * Created by spoonsrattling on 9/22/16.
 */

public class Pile {
  PileType type;
  ArrayList<Card> cards;

  /**
   * Creates an empty pile of this type.
   *
   * @param type the PileType.
   */
  public Pile(PileType type) {

    this.type = type;
    this.cards = new ArrayList<Card>();
  }

  /**
   * Is this pile empty.
   *
   * @return true if it is empty.
   */
  public boolean isEmpty() {
    return cards.isEmpty();
  }

  /**
   * Size of card arraylist.
   *
   * @return length.
   */
  public int size() {
    return cards.size();
  }

  /**
   * Adds this card to the end/top of this pile.
   *
   * @param c The card to add.
   */
  public void addToTop(Card c) {
    this.cards.add(c);
  }

  /**
   * Removes the last card from this pile.
   */
  public void removeFromTop() {
    this.cards.remove(cards.size() - 1);
  }

  /**
   * Returns the last card in this pile.
   *
   * @return the last card.
   */
  public Card onTop() {
    return get(cards.size() - 1);
  }

  public boolean isOnTop(Card c) {
    return onTop().cardEquals(c);
  }

  /**
   * Gets the card at index i.
   *
   * @param i index.
   * @return the card at i.
   */
  public Card get(int i) {
    return cards.get(i);
  }

  /**
   * Gives the string representation of this pile.
   *
   * @return the string representation.
   */
  public String printPile() {
    String result = "";
    if (isEmpty()) {
      result = result + "\n";
    } else {
      for (Card c : cards) {
        if (!onTop().cardEquals(c)) {
          result = result + " " + c.toString() + ",";
        } else {
          result = result + " " + c.toString() + "\n";
        }
      }
    }
    return result;
  }

  /**
   * Test if this is a validBuild starting at the specified index (starting from 0).
   * Each card in a valid build has the opposite color and value one less than the card before it.
   * Invariant: firstIndex will always be > 0 and <= to this.cards.size.
   * @param firstIndex The first index of the build (inclusive), starting from 0;
   * @return True if this is a valid build.
   */
  public boolean validBuild(int firstIndex) {
    for (int i = firstIndex + 1; i < size(); i++) {
      Card thisCard = get(i);
      Card cardBefore = get(i - 1);
      if (thisCard.canMoveCascade(cardBefore)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Invariant: the build being removed is valid.
   * Removes all cards start
   * @param firstIndex The first index of the build, starting from 0.
   */
  public void removeBuild(int firstIndex) {
    int buildLength = cards.size() - firstIndex - 1;
    for (int i = 0; i <= buildLength; i++) {
      cards.remove(i);
    }
  }
}

/**
 *
 */
