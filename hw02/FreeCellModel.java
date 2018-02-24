package FreeCell.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by spoonsrattling on 9/22/16.
 */
public class FreeCellModel implements IFreeCellModel<Card> {
  protected ArrayList<Pile> foundationPiles;
  protected ArrayList<Pile> openPiles;
  protected ArrayList<Pile> cascadePiles;
  protected boolean isStarted;

  /**
   * Creates a new FreeCellModel with 4 Open piles.
   */
  public FreeCellModel() {
    this.foundationPiles = initializePiles(PileType.FOUNDATION, 4);
    this.openPiles = new ArrayList<Pile>();
    this.cascadePiles = new ArrayList<Pile>();
    this.isStarted = false;
  }

  /**
   * Creates the appropriate number and type of piles.
   *
   * @param type     The PileType .
   * @param numPiles The number of piles.
   * @return ArrayList of type type and length numPiles.
   */
  public ArrayList<Pile> initializePiles(PileType type, int numPiles) {
    ArrayList<Pile> temp = new ArrayList<Pile>();
    for (int i = 0; i < numPiles; i++) {
      temp.add(new Pile(type));
    }
    return temp;
  }

  @Override
  public ArrayList<Card> getDeck() throws IllegalArgumentException {
    ArrayList<Card> result = new ArrayList<Card>();
    CardValue[] cardVals = CardValue.values();
    for (int i = 0; i < 13; i++) {
      CardValue iVal = cardVals[i];
      result.add(new Card(Suit.CLUB, iVal));
      result.add(new Card(Suit.DIAMOND, iVal));
      result.add(new Card(Suit.HEART, iVal));
      result.add(new Card(Suit.SPADE, iVal));
    }
    if (!validDeck(result)) {
      throw new IllegalArgumentException("Invalid deck (getDeck)");
    }
    return result;
  }

  /**
   * Checks if this deck is valid.
   * @param deck Given deck.
   * @return True if this deck contains 52 cards, 13 of each Suit, and 4 of each Value.
   * @throws IllegalArgumentException If this deck is not valid.
   */
  protected boolean validDeck(List<Card> deck) throws IllegalArgumentException {
    if (deck.size() != 52) {
      return false;
    }
    List<Card> floorPile = new ArrayList<Card>();
    Card firstCard = deck.get(0);
    floorPile.add(firstCard);
    for (int i = 1; i < deck.size(); i++) {
      Card thisCard = deck.get(i);
      for (Card c : floorPile) {
        if (thisCard.cardEquals(c)) {
          return false;
        }
      }
      floorPile.add(thisCard);
    }
    return true;
  }


  @Override
  public void startGame(List deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
          throws IllegalArgumentException {
//    if (numCascadePiles < 4 || numCascadePiles > 8 || numOpenPiles < 1 || numOpenPiles > 4) {
//      throw new IllegalArgumentException("Could not start game");
//    }
    if (shuffle) {
      Collections.shuffle(deck);
    }
    if (!validDeck(deck)) {
      throw new IllegalArgumentException("Invalid deck (startGame)");
    }

    openPiles = initializePiles(PileType.OPEN, numOpenPiles);
    cascadePiles = deal(deck, numCascadePiles);
    isStarted = true;

  }

  /**
   * Deals the cards in a round-robin fashion.
   *
   * @param deck            The 52-card deck.
   * @param numCascadePiles The number of piles to deal.
   * @return an ArrayList of cascade piles with the cards dealth in them.
   */
  ArrayList<Pile> deal(List<Card> deck, int numCascadePiles)
          throws IllegalArgumentException {

    if (!validDeck(deck)) {
      throw new IllegalArgumentException("Invalid deck (deal)");
    }
    ArrayList<Pile> tempCascade = initializePiles(PileType.CASCADE, numCascadePiles);
    for (int i = 0; i < deck.size(); i++) {
      Card c = deck.get(i);
      tempCascade.get(i % numCascadePiles).addToTop(c);
    }
    return tempCascade;
  }

  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destType,
                   int destPileNumber) throws IllegalArgumentException {
    if (sourcePileNumber < 0 || destPileNumber < 0
            || sourcePileNumber >= getPiles(sourceType).size()
            || destPileNumber >= getPiles(destType).size()) {
      throw new IllegalArgumentException("Out of bounds");
    }

    // you can't move from a foundation
    if (sourceType == PileType.FOUNDATION) {
      throw new IllegalArgumentException("You can't move that");
    }

    Pile destPile = getPiles(destType).get(destPileNumber);
    Pile sourcePile = getPiles(sourceType).get(sourcePileNumber);

    // out of bounds
    if (cardIndex >= sourcePile.size() || cardIndex < 0) {
      throw new IllegalArgumentException("Out of bounds");
    }

    Card sourceCard = sourcePile.get(cardIndex);

    // you can only move the last card in a pile
    if (!sourcePile.isOnTop(sourceCard)) {
      throw new IllegalArgumentException("This card is not on top.");
    }

    if ((destType == PileType.OPEN && destPile.isEmpty())
            || (destType == PileType.CASCADE
            && (destPile.isEmpty() || sourceCard.canMoveCascade(destPile.onTop())))
            || (destType == PileType.FOUNDATION
            && ((destPile.isEmpty() && sourceCard.value == CardValue.ACE)
            || (!destPile.isEmpty() && sourceCard.canMoveFoundation(destPile.onTop()))))) {

      moveHelp(sourceType, sourcePileNumber, cardIndex, destType, destPileNumber);

    } else {
      throw new IllegalArgumentException("You can't put that there!");
    }
  }

  /**
   * Removes the card from the sourcePile and adds it to the top of the destination pile.
   *
   * @param sourceType       type of sourcepile.
   * @param sourcePileNumber which sourcepile.
   * @param cardIndex        the index of the card in the sourcepile.
   * @param destType         the type of destinationpile.
   * @param destPileNumber   which destinationpile.
   */
  protected void moveHelp(PileType sourceType, int sourcePileNumber,
                          int cardIndex, PileType destType, int destPileNumber) {
    Pile destPile = getPiles(destType).get(destPileNumber);
    Pile sourcePile = getPiles(sourceType).get(sourcePileNumber);
    Card sourceCard = sourcePile.get(cardIndex);

    sourcePile.removeFromTop();
    destPile.addToTop(sourceCard);
  }

  /**
   * Returns this model's list of that type of pile.
   *
   * @param type the PileType.
   * @return an ArrayList of that type of pile.
   */
  protected ArrayList<Pile> getPiles(PileType type) {
    switch (type) {
      case CASCADE:
        return cascadePiles;
      case FOUNDATION:
        return foundationPiles;
      case OPEN:
        return openPiles;
      default:
        return null;
    }
  }

  @Override
  public boolean isGameOver() {
    for (Pile c : cascadePiles) {
      if (!c.isEmpty()) {
        return false;
      }
    }
    for (Pile o : openPiles) {
      if (!o.isEmpty()) {
        return false;
      }
    }

    return (foundationPiles.get(0).size() == 13)
            && (foundationPiles.get(1).size() == 13)
            && (foundationPiles.get(2).size() == 13)
            && (foundationPiles.get(3).size() == 13);

  }

  @Override
  public String getGameState() {
    if (!isStarted) {
      return "";
    }
    String result = "";
    for (int i = 0; i < foundationPiles.size(); i++) {
      result = result + "F" + Integer.toString(i + 1) + ":"
              + foundationPiles.get(i).printPile();
    }
    for (int i = 0; i < openPiles.size(); i++) {
      result = result + "O" + Integer.toString(i + 1) + ":"
              + openPiles.get(i).printPile();
    }
    for (int i = 0; i < cascadePiles.size(); i++) {
      result = result + "C" + Integer.toString(i + 1) + ":"
              + cascadePiles.get(i).printPile();

    }
    return result.trim();
  }
}
