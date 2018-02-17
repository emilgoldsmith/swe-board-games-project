package test.unit.ego225;

import api.Game;
import api.Chip;
import impl.game.ConnectFour;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectFourTest {
    @Test
    public void testInitialState() {
      Game game = new ConnectFour();
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
}
