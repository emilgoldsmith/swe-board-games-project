package test.unit.ego225;

import api.Game;
import api.Chip;
import impl.game.ConnectFour;
import exc.GameIndexOutOfBoundsException;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import org.junit.rules.ExpectedException;

public class GetChipTest {
  private Game game;

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Before
  public void setup() {
    game = new ConnectFour();
  }

  @Test
  public void throwsOnNegativeColumn() {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.getChip(-1, 0);
  }

  @Test
  public void throwsOnNegativeRow() {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.getChip(0, -1);
  }

  @Test
  public void throwsOnTooHighRow() {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.getChip(0, 10);
  }

  @Test
  public void throwsOnTooHighColumn() {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.getChip(10, 0);
  }

  // This test assumes that the size of the board is 7x6 as using the
  // getColumns and getRows methods would make it more of an integration test
  @Test
  public void doesntThrowInsideBoard() {
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 6; j++) {
        game.getChip(j, i);
      }
    }
  }
}