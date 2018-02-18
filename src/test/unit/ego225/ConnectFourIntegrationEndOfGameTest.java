package test.unit.ego225;

import api.Game;
import api.Chip;
import impl.game.ConnectFour;
import exc.GameStateException;


import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectFourIntegrationEndOfGameTest {

  private Game game;

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Before
  public void setup() throws GameStateException {
    this.game = new ConnectFour();
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 2; j++) {
        if (i == 3 && j == 1) break;
        game.placeChip(0, i);
      }
    }
  }

  @Test
  public void testPlaceChipAtGameOver() throws GameStateException {
    exception.expect(GameStateException.class);
    game.placeChip(0, 0);
  }

  @Test
  public void testGetCurrentPlayerAtGameOver() {
    assertEquals("Current Player should be EMPTY", Chip.EMPTY, game.getCurrentPlayer());
  }

  @Test
  public void testIsActuallyGameOver() {
    assertTrue("Is game over", game.isGameOver());
  }
}
