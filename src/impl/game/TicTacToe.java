package impl.game;

import api.Chip;

import java.lang.Override;

public class TicTacToe extends GameTemplate {
  public TicTacToe() {
    this.initializeBoard(3, 3);
  }

  @Override
  protected boolean placeChipIsAllowed(int row, int column) {
    return this.isWithinBoard(row, column);
  }

  protected Chip[] hasNewWinner() {
    for (int row = 0; row < this.getRows(); row++) {
      for (int column = 0; column < this.getColumns(); column++) {
        // If it's empty no ones going to have a winning combination including this square
        if (this.getChip(row, column) == Chip.EMPTY) continue;
        boolean hasRowWinner = row + 2 < this.getRows();
        boolean hasColumnWinner = column + 2 < this.getColumns();
        boolean hasDiagonalOneWinner = hasRowWinner && hasColumnWinner;
        boolean hasDiagonalTwoWinner = hasRowWinner && column - 2 >= 0;
        for (int inc = 1; inc < 3; inc++) {
          hasRowWinner = hasRowWinner && this.getChip(row + inc, column) == this.getChip(row, column);
          hasColumnWinner = hasColumnWinner && this.getChip(row, column + inc) == this.getChip(row, column);
          hasDiagonalOneWinner = hasDiagonalOneWinner && this.getChip(row + inc, column + inc) == this.getChip(row, column);
          hasDiagonalTwoWinner = hasDiagonalTwoWinner && this.getChip(row + inc, column - inc) == this.getChip(row, column);
        }
        if (hasRowWinner || hasColumnWinner || hasDiagonalOneWinner || hasDiagonalTwoWinner) {
          return new Chip[] {Chip.BLUE, this.getChip(row, column)};
        }
      }
    }

    // No one has won yet so we check whether it is a tie
    boolean boardIsFull = true;
    for (int row = 0; row < this.getRows(); row++) {
      for (int column = 0; column < this.getColumns(); column++) {
        if (this.getChip(row, column) == Chip.EMPTY) {
          // No winner
          return new Chip[] {Chip.EMPTY, Chip.EMPTY};
        }
      }
    }
    if (boardIsFull) {
      return new Chip[] {Chip.BLUE, Chip.EMPTY};
    }
    return new Chip[] {Chip.EMPTY, Chip.EMPTY};
  }

}
