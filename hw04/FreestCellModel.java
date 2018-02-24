package FreeCell.hw04;

import FreeCell.hw02.*;
import FreeCell.hw03.*;

/**
 * Created by spoonsrattling on 10/13/16.
 */
public class FreestCellModel extends FreeCellModel {

  public FreestCellModel() {
    super();
  }

  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destType,
                   int destPileNumber) throws IllegalArgumentException {
    // check the indices
    if (sourcePileNumber < 0 || destPileNumber < 0
            || sourcePileNumber >= getPiles(sourceType).size()
            || destPileNumber >= getPiles(destType).size()) {
      throw new IllegalArgumentException("Out of bounds");
    }

    // you can't move from a foundation
    if (sourceType == PileType.FOUNDATION) {
      throw new IllegalArgumentException("You can't move that");
    }

    // assign piles
    Pile destPile = getPiles(destType).get(destPileNumber);
    Pile sourcePile = getPiles(sourceType).get(sourcePileNumber);

    // out of bounds
    if (cardIndex >= sourcePile.size() || cardIndex < 0) {
      throw new IllegalArgumentException("Out of bounds");
    }

    if (!sourcePile.validBuild(cardIndex) || !canMultimove(sourcePile.size() - cardIndex)) {
      throw new IllegalArgumentException("Invalid build or build is too long");
    }
    Card sourceCard = sourcePile.get(cardIndex);

    if ((destType == PileType.FOUNDATION || destType == PileType.OPEN)
            && cardIndex != sourcePile.size() - 1) {
      throw new IllegalArgumentException("Cannot move a build to a Foundation or Open pile");
    }

    // Open destination: has to be empty
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


  @Override
  public void moveHelp(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destType,
                       int destPileNumber) {
    Pile destPile = getPiles(destType).get(destPileNumber);
    Pile sourcePile = getPiles(sourceType).get(sourcePileNumber);
    Card sourceCard = sourcePile.get(cardIndex);

    if (sourcePile.isOnTop(sourceCard)) {
      sourcePile.removeFromTop();
      destPile.addToTop(sourceCard);
    } else {
      for (int i = cardIndex; i < sourcePile.size(); i++) {
        Card card = sourcePile.get(i);
        destPile.addToTop(card);
      }
      sourcePile.removeBuild(cardIndex);
    }
  }

  private boolean canMultimove(int buildLength) {
    int numEmptyOpen = 0; //n
    for (Pile p : openPiles) {
      if (p.isEmpty()) {
        numEmptyOpen += 1;
      }
    }

    int numEmptyCasc = 0; //k
    for (Pile p : cascadePiles) {
      if (p.isEmpty()) {
        numEmptyCasc += 1;
      }
    }
    return ((numEmptyOpen + 1) * Math.pow(2, numEmptyCasc)) >= buildLength;
  }
}
