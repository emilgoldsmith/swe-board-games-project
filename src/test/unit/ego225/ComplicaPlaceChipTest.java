package test.unit.ego225;

import api.Game;
import api.Chip;
import impl.game.Complica;
import exc.GameIndexOutOfBoundsException;
import exc.GameStateException;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class ComplicaPlaceChipTest {
  private Game game;

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Before
  public void setup() {
    game = new Complica();
  }

  @Test
  public void throwsOnNonZeroRowWithinBoard() throws GameStateException {
    exception.expect(GameIndexOutOfBoundsException.class);
    game.placeChip(1, 0);
  }

  @Test
  public void doesntThrowOnZeroRow() throws GameStateException {
    for (int i = 0; i < 4; i++) {
      game.placeChip(0, i);
    }
  }

  @Test
  public void queueWorksCorrectly() throws GameStateException {
    for (int testingColumn = 0; testingColumn < 4; testingColumn++) {
      // Make sure we don't get any accidental wins by testing all the columns
      this.game = new Complica();
      // We don't get any exceptions while placing many chips
      for (int i = 0; i < 50; i++) {
        game.placeChip(0, testingColumn);
      }
      Chip[] oldColumn = new Chip[7];
      for (int i = 0; i < 7; i++) {
        oldColumn[i] = game.getChip(i, testingColumn);
      }

      game.placeChip(0, testingColumn);
      for (int i = 1; i < 7; i++) {
        assertFalse("Chip in old column isn't empty in column " + String.valueOf(testingColumn), oldColumn[i - 1].isEmpty());
        assertEquals("Chip was moved down by one in column " + String.valueOf(testingColumn), oldColumn[i - 1], game.getChip(i, testingColumn));
      }
    }
  }
}
