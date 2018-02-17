package test.unit.ego225;

import api.Game;
import api.Chip;
import impl.game.ConnectFour;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectFourTest {
    @Test
    public void testDefaultConstructor() {
        Game game = new ConnectFour();
        assertEquals("has 6 rows", 6, game.getRows());
        assertEquals("has 7 columns", 7, game.getColumns());
    }

    // This simultaneously tests that getChip functions (at least partially)
    // the way we want it and that the board starts empty
    @Test
    public void boardStartsEmpty() {
      Game game = new ConnectFour();
      boolean boardIsEmpty = true;
      for (int i = 0; i < 7; i++) {
        for (int j = 0; j < 6; j++) {
          boardIsEmpty = boardIsEmpty && (game.getChip(j, i) == Chip.EMPTY);
        }
      }
      assertTrue("Board starts empty", boardIsEmpty);
    }
}
