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
import org.junit.Before;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.lang.System;

@RunWith(Parameterized.class)
public class CommonGetChipTest {
  private Game game;

  public CommonGetChipTest(Game game) {
    this.game = game;
  }

  @Parameters
  public static Iterable<Game> parameters() {
      return Arrays.asList(new ConnectFour(),
                            new Complica(),
                            new TicTacToe());
  }

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void throwsOnNegativeColumn() {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.getChip(0, -1);
  }

  @Test
  public void throwsOnNegativeRow() {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.getChip(-1, 0);
  }

  @Test
  public void throwsOnTooHighRow() {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.getChip(game.getRows(), 0);
  }

  @Test
  public void throwsOnTooHighColumn() {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.getChip(0, game.getColumns());
  }

  @Test
  public void doesntThrowInsideBoard() {
    for (int i = 0; i < game.getRows(); i++) {
      for (int j = 0; j < game.getColumns(); j++) {
        game.getChip(i, j);
      }
    }
  }
}
