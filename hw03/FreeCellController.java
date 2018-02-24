package FreeCell.hw03;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import FreeCell.hw02.*;
/**
 * Created by spoonsrattling on 10/3/16.
 */
public class FreeCellController implements IFreeCellController {
  final InputStream in;
  final PrintStream out;

  /**
   * Constructs a new FreeCellController.
   *
   * @param in  the Input.
   * @param out the Output.
   */
  public FreeCellController(InputStream in, PrintStream out) {
    if (in == null || out == null) {
      throw new IllegalStateException("Empty input.");
    }
    this.in = in;
    this.out = out;
  }

  /**
   * Checks if pile input is well-formed.
   *
   * @param input String input.
   * @return True if input begins with C, O, or F, and is followed by only ints.
   */
  public boolean validPileInput(String input) {
    // at least of length 2
    if (input.length() > 1) {
      char firstChar = input.charAt(0);
      String numberString = input.substring(1);

      return ((firstChar == 'C' || firstChar == 'O' || firstChar == 'F')
              && validIndex(numberString));

    } else {
      return false;
    }
  }


  /**
   * Checks if this is a valid index.
   *
   * @param input String input.
   * @return True if input can be parsed as an int.
   */
  public boolean validIndex(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  @Override
  public void playGame(List deck, IFreeCellModel model, int numCascades,
                       int numOpens, boolean shuffle) {

    // first check if deck, model are null

    if (deck == null || model == null) {
      throw new IllegalArgumentException("Null deck and/or model.");
    }

    if (shuffle) {
      Collections.shuffle(deck);
    }
    try {
      try {
        model.startGame(deck, numCascades, numOpens, shuffle);
        out.append(model.getGameState() + "\n");
      } catch (IllegalArgumentException e) {
        out.append("Could not start game.");
        return;
      }

      Scanner scan = new Scanner(this.in);

      int sourcePileNumber;
      PileType sourceType;
      int cardIndex;
      int destPileNumber;
      PileType destType;


      // source pile
      while (scan.hasNext()) {
        if (model.isGameOver()) {
          out.append("You won!");
          return;
        }
        String firstInput = scan.next();

        // first input contains Q
        if (Character.toUpperCase(firstInput.charAt(0)) == 'Q') {
          out.append("Game quit prematurely.");
          return;
        }

        // first does not contain Q and is valid ; assign vars
        else if (validPileInput(firstInput)) {
          sourceType = getType(firstInput.charAt(0));
          sourcePileNumber = Integer.parseInt(firstInput.substring(1)) - 1;

          // second input exists
          if (scan.hasNext()) {
            String secondInput = scan.next();

            // second input contains Q
            if (Character.toUpperCase(secondInput.charAt(0)) == 'Q') {
              out.append("Game quit prematurely.");
              return;
            }

            // second input does not contain Q and is valid; assign var
            else if (validIndex(secondInput)) {
              cardIndex = Integer.parseInt(secondInput) - 1;

              // third input exists
              if (scan.hasNext()) {
                String thirdInput = scan.next();

                // third input contains Q
                if (Character.toUpperCase(thirdInput.charAt(0)) == 'Q') {
                  out.append("Game quit prematurely.");
                  return;
                }

                // third input does not contain Q and is valid
                else if (validPileInput(thirdInput)) {
                  destType = getType(thirdInput.charAt(0));
                  destPileNumber = Integer.parseInt(thirdInput.substring(1)) - 1;

                  try {
                    model.move(sourceType, sourcePileNumber, cardIndex, destType, destPileNumber);
                    out.append(model.getGameState() + "\n");
                  } catch (IllegalArgumentException e) {
                    out.append("Invalid move. Try again. \n");
                  }
                }
              }
            }
          }
        } else {
          out.append("Invalid input. Try again. \n");
        }
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
  }


  /**
   * Gets the type specified by the char.
   * @param initial Char C, O, or F.
   * @return Cascade for C, Open for O, Foundation for F.
   */
  public PileType getType(char initial) {
    switch (initial) {
      case 'C':
        return PileType.CASCADE;
      case 'O':
        return PileType.OPEN;
      case 'F':
        return PileType.FOUNDATION;
      default:
        assert (false);
        return null;
    }
  }
}
