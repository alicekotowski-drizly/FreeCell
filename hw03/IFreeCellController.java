package FreeCell.hw03;

import java.util.List;

import FreeCell.hw02.*;

/**
 * Created by spoonsrattling on 10/3/16.
 */

/**
 * Controller for FreeCell.
 * @param <Card> Card.
 */
public interface IFreeCellController<Card> {

  /**
   * Start a new game of FreeCell with the given specifications. throw an IllegalStateException
   * only if the controller has not been initialized properly to receive input and transmit output
   * @param deck Provided deck.
   * @param model Provided model.
   * @param numCascades Number of Cascade piles.
   * @param numOpens Number of Open piles.
   * @param shuffle Shuffle deck if true.
   */
  void playGame(List<Card> deck, IFreeCellModel<Card> model, int numCascades,
                int numOpens, boolean shuffle);
}
