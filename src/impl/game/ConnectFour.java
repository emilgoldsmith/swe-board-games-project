package impl.game;

import java.util.List;
import java.util.ArrayList;

import api.Game;
import api.Chip;
import exc.GameIndexOutOfBoundsException;
import exc.GameStateException;

public class ConnectFour extends Game {
  // We only store numRows as the global source of truth for the numColumns should
  // be the length of the board List
  private int numRows;
  private List<Chip[]> board;
  private int currentPlayerIndex;
  private final Chip[] players = {Chip.RED, Chip.BLUE};
  private Chip winner;
  private boolean gameOver = false;

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
    if (row < 0 || row >= this.getRows() || column < 0 || column >= this.getColumns()) {
      throw new GameIndexOutOfBoundsException(row, column);
    }
    return this.board.get(column)[row];
  }

  public void placeChip(int row, int column) throws GameStateException {
    if (this.isGameOver()) {
      throw new GameStateException();
    }

    if (row < 0 || row >= this.getRows() || column < 0 || column >= this.getColumns()) {
      throw new GameIndexOutOfBoundsException(row, column);
    }

    if (this.getChip(row, column) != Chip.EMPTY) {
      throw new GameStateException();
    }

    // Drop the chip
    int finalRow = row;
    for (; finalRow < this.getRows() && this.getChip(finalRow, column) == Chip.EMPTY; finalRow++);
    // Decrement since the one we arrived at was invalid
    finalRow--;

    // Place chip for current player
    this.board.get(column)[finalRow] = this.getCurrentPlayer();

    // Make it the next players turn
    this.currentPlayerIndex = (this.currentPlayerIndex + 1) % 2;

    // Notify the view
    this.setChanged();
    this.notifyObservers();
  }

  public Chip getWinningPlayer() throws GameStateException {
    if (!this.isGameOver()) {
      throw new GameStateException();
    }
    return winner;
  }

  public Chip getCurrentPlayer() {
    if (this.isGameOver()) {
      return Chip.EMPTY;
    }
    return this.players[this.currentPlayerIndex];
  }

  public boolean isGameOver() {
    if (this.gameOver) {
      return true;
    }
    for (int row = 0; row < this.getRows(); row++) {
      for (int column = 0; column < this.getColumns(); column++) {
        // If it's empty no ones going to have a winning combination including this square
        if (this.getChip(row, column) == Chip.EMPTY) continue;
        boolean hasRowWinner = row + 3 < this.getRows();
        boolean hasColumnWinner = column + 3 < this.getColumns();
        boolean hasDiagonalOneWinner = hasRowWinner && hasColumnWinner;
        boolean hasDiagonalTwoWinner = hasRowWinner && column - 3 >= 0;
        for (int inc = 1; inc < 4; inc++) {
          hasRowWinner = hasRowWinner && this.getChip(row + inc, column) == this.getChip(row, column);
          hasColumnWinner = hasColumnWinner && this.getChip(row, column + inc) == this.getChip(row, column);
          hasDiagonalOneWinner = hasDiagonalOneWinner && this.getChip(row + inc, column + inc) == this.getChip(row, column);
          hasDiagonalTwoWinner = hasDiagonalTwoWinner && this.getChip(row + inc, column - inc) == this.getChip(row, column);
        }
        if (hasRowWinner || hasColumnWinner || hasDiagonalOneWinner || hasDiagonalTwoWinner) {
          this.winner = this.getChip(row, column);
          this.gameOver = true;
          return true;
        }
      }
    }

    // No one has won yet so we check whether it is a tie
    boolean boardIsFull = true;
    for (int row = 0; row < 6; row++) {
      for (int column = 0; column < 7; column++) {
        if (this.getChip(row, column) == Chip.EMPTY) {
          boardIsFull = false;
          break;
        }
      }
      if (!boardIsFull) break;
    }

    // Game is over if board is full, otherwise we can still play on
    if (boardIsFull) {
      this.gameOver = true;
      this.winner = Chip.EMPTY;
    }
    return this.gameOver;
  }
}
