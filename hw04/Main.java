package FreeCell.hw04;

import FreeCell.hw02.*;
import FreeCell.hw03.*;

import java.util.List;

public class Main {

    public static void main(String args[]) {
        FreeCellModelCreator creator = new FreeCellModelCreator();
        IFreeCellModel<Card> model = creator.create();
        List<Card> deck = model.getDeck();

        IFreeCellController controller = new FreeCellController(System.in, System.out);

        controller.playGame(deck, model, 8, 4, true);
    }
}
