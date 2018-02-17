package impl.game;

import java.util.List;
import java.util.ArrayList;

import api.Game;
import api.Chip;
import exc.GameIndexOutOfBoundsException;

public class ConnectFour extends Game {
  // We only store numRows as the global source of truth for the numColumns should
  // be the length of the board List
  private int numRows;
  private List<Chip[]> board;
  private int currentPlayerIndex;
  private final Chip[] players = {Chip.RED, Chip.BLUE};

  public ConnectFour() {
    // 6 rows and 7 columns by default, we represent the board as a List of columns
    this.numRows = 6;
    int numColumns = 7;
    this.board = new ArrayList<Chip[]>();
    for (int i = 0; i < numColumns; i++) {
      Chip[] singleColumn = new Chip[numRows];
      for (int j = 0; j < numRows; j++) {
        singleColumn[j] = Chip.EMPTY;
      }
      this.board.add(singleColumn);
    }

    // Initialize starting player
    currentPlayerIndex = 0;
  }

  public int getRows() {
    return this.numRows;
  }

  public int getColumns() {
    return this.board.size();
  }

  public Chip getChip(int row, int column) {
    return this.board.get(column)[row];
  }

  public void placeChip(int row, int column) {

  }

  public Chip getWinningPlayer() {
    return Chip.EMPTY;
  }

  public Chip getCurrentPlayer() {
    return this.players[this.currentPlayerIndex];
  }

  public boolean isGameOver() {
    return false;
  }
}
