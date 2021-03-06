package test.unit.ego225;

import api.Game;
import api.Chip;
import impl.game.ConnectFour;
import exc.GameIndexOutOfBoundsException;
import exc.GameStateException;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import org.junit.rules.ExpectedException;

public class ConnectFourPlaceChipTest {
  private Game game;

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Before
  public void setup() {
    game = new ConnectFour();
  }

  @Test
  public void throwsOnNonZeroRowWithinBoard() throws GameStateException {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.placeChip(1, 0);
  }

  @Test
  public void doesntThrowOnZeroRow() throws GameStateException {
    for (int i = 0; i < 7; i++) {
      game.placeChip(0, i);
    }
  }

  // This test assumes that the size of the board is 7x6 as using the
  // getRows method would make it more of an integration test
  @Test
  public void canPlaceExactlySix() throws GameStateException {
    // If it throws here the test will fail
    for (int i = 0; i < 6; i++) {
      game.placeChip(0, 0);
    }
    // Here we expect it to throw
    exception.expect(GameIndexOutOfBoundsException.class);
    game.placeChip(0, 0);
  }
}
