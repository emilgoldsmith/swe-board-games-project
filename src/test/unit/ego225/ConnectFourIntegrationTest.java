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
import static org.junit.Assert.assertFalse;

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

  @Test
  public void testWinningHorizontally() throws GameStateException {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 2; j++) {
        if (j == 1 && i == 3) continue;
        assertFalse("Should not have finished yet", game.isGameOver());
        game.placeChip(0, i);
      }
    }
    assertTrue("Should have finished now", game.isGameOver());
    assertEquals("RED should have won", Chip.RED, game.getWinningPlayer());
  }

  @Test
  public void testWinningVertically() throws GameStateException {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 2; j++) {
        if (j == 1 && i == 3) continue;
        assertFalse("Should not have finished yet", game.isGameOver());
        game.placeChip(0, j);
      }
    }
    assertTrue("Should have finished now", game.isGameOver());
    assertEquals("RED should have won", Chip.RED, game.getWinningPlayer());
  }

  @Test
  public void testWinningDiagonalOne() throws GameStateException {
    for (int i = 5; i >= 2; i--) {
      for (int j = 0; j < 6; j++) {
        if (i == 2 && j == 1) break;
        assertFalse("Should not have finished yet", game.isGameOver());
        game.placeChip(0, i);
      }
      if (i > 2) {
        // Make sure we change turns to who goes first on the column
        game.placeChip(0, 0);
      }
    }
    assertTrue("Should have finished now", game.isGameOver());
    assertEquals("BLUE should have won", Chip.BLUE, game.getWinningPlayer());
  }

  @Test
  public void testWinningDiagonalTwo() throws GameStateException {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 6; j++) {
        if (i == 3 && j == 1) break;
        assertFalse("Should not have finished yet", game.isGameOver());
        game.placeChip(0, i);
      }
      if (i < 3) {
        // Make sure we change turns to who goes first on the column
        game.placeChip(0, 6);
      }
    }
    assertTrue("Should have finished now", game.isGameOver());
    assertEquals("BLUE should have won", Chip.BLUE, game.getWinningPlayer());
  }

  @Test
  public void testTie() throws GameStateException {
    // This is just implementing a simple "algorithm" that will result in a tie
    // Generally just alternate which player has chips in each column and swap it
    // for the upper half, and for the implementation that leaves a special case
    // for the last column
    int cnt = 0;
    for (int column = 0; column < 7; column += 2) {
      for (int row = 0; row < 6; row++) {
        int player1Col = column;
        int player2Col = column + 1;
        if (column < 6 && row >= 3) {
          player1Col = column + 1;
          player2Col = column;
        }

        for (int player = 0; player < 2; player++) {
          if (!(player == 1 && column == 6)) {
            assertFalse("Should not have finished yet", game.isGameOver());
          }
          if (player == 0) {
            game.placeChip(0, player1Col);
            cnt++;
          } else if (column < 6) {
            game.placeChip(0, player2Col);
            cnt++;
          }
        }
      }
    }

    assertTrue("Should have finished now", game.isGameOver());
    assertEquals("It should be a tie", Chip.EMPTY, game.getWinningPlayer());
  }
}
