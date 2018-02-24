package FreeCell.hw04;



import FreeCell.hw02.*;



/**
 * Created by spoonsrattling on 10/13/16.
 */
public class FreeCellModelCreator {

  /**
   * Creates a new IFreeCellModel of the given type.
   * @return The model.
   */
  public static IFreeCellModel create() {
      return new FreestCellModel();
    }
  }
