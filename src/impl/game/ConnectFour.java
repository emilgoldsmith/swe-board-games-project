package impl.game;

import api.Chip;

import java.lang.Override;

public class ConnectFour extends GameTemplate {
  public ConnectFour() {
    this.initializeBoard(6, 7);
  }

  @Override
  protected boolean placeChipIsAllowed(int row, int column) {
    // Connect four has the extra restriction that you can only place from the top
    return super.placeChipIsAllowed(row, column) && row == 0;
  }

  @Override
  protected int[] getChipPlacement(int row, int column) {
    // Drop the chip
    int finalRow = row;
    for (; finalRow < this.getRows() && this.getChip(finalRow, column) == Chip.EMPTY; finalRow++);
    // Decrement since the one we arrived at was invalid
    finalRow--;
    return new int[] {finalRow, column};
  }

  protected Chip[] hasNewWinner() {
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
          return new Chip[] {Chip.BLUE, this.getChip(row, column)};
        }
      }
    }

    // No one has won yet so we check whether it is a tie
    boolean boardIsFull = true;
    for (int row = 0; row < 6; row++) {
      for (int column = 0; column < 7; column++) {
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
