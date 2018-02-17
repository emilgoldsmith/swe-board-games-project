package test.unit.ego225;

import api.Game;
import api.Chip;
import impl.game.ConnectFour;
import exc.GameStateException;


import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectFourIntegrationTest {

  private Game game;

  @Before
  public void setup() {
    this.game = new ConnectFour();
  }

  @Test
  public void testInitialState() {
    assertEquals("has 6 rows", 6, game.getRows());
    assertEquals("has 7 columns", 7, game.getColumns());

    boolean boardIsEmpty = true;
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 6; j++) {
        boardIsEmpty = boardIsEmpty && (game.getChip(j, i) == Chip.EMPTY);
      }
    }
    assertTrue("Board starts empty", boardIsEmpty);

    assertEquals("Red starts", Chip.RED, game.getCurrentPlayer());
  }

  @Test
  public void testMoveTransition() throws GameStateException {
    assertEquals("column 0 has empty bottom", Chip.EMPTY, game.getChip(game.getRows() - 1, 0));

    assertEquals("Initially RED players turn", Chip.RED, game.getCurrentPlayer());

    game.placeChip(0, 0);

    assertEquals("RED Chip was succesfully placed in column 0", Chip.RED, game.getChip(game.getRows() - 1, 0));

    assertEquals("Turn changed to BLUE player", Chip.BLUE, game.getCurrentPlayer());
  }
}
