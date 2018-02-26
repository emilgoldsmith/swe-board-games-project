package test.unit.ego225;

import api.Game;
import api.Chip;
import impl.game.ConnectFour;
import impl.game.Complica;
import impl.game.TicTacToe;
import exc.GameIndexOutOfBoundsException;
import exc.GameStateException;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CommonPlaceChipTest {
  private Game game;

  public CommonPlaceChipTest(Game game) {
    this.game = game;
  }

  @Parameters ( name = "{0}" )
  public static Iterable<Game> parameters() {
      return Arrays.asList(new ConnectFour(),
                            new Complica(),
                            new TicTacToe());
  }

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void throwsOnNegativeColumn() throws GameStateException {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.placeChip(0, -1);
  }

  @Test
  public void throwsOnTooHighColumn() throws GameStateException {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.placeChip(0, game.getColumns());
  }

  @Test
  public void throwsOnNegativeRow() throws GameStateException {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.placeChip(-1, 0);
  }

  @Test
  public void throwsOnTooHighRow() throws GameStateException {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.placeChip(game.getRows(), 0);
  }
}
