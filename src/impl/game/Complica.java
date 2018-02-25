package impl.game;

import api.Chip;

import java.lang.Override;

public class Complica extends GameTemplate {
  public Complica() {
    this.initializeBoard(7, 4);
  }

  @Override
  protected boolean placeChipIsAllowed(int row, int column) {
    // In Complica it doesn't matter whether the tile is empty as long as it's row 0
    return this.isWithinBoard(row, column) && row == 0;
  }

  /**
   * This will return row -1 if the column is full, but this is okay as we'll handle it
   * in actuallyPlaceChip
   */
  @Override
  protected int[] getChipPlacement(int row, int column) {
    // Drop the chip
    int finalRow = row;
    for (; finalRow < this.getRows() && this.getChip(finalRow, column) == Chip.EMPTY; finalRow++);
    // Decrement since the one we arrived at was invalid
    finalRow--;
    return new int[] {finalRow, column};
  }

  @Override
  protected void handlePlaceChip(int row, int column, Chip chip) {
    if (row == -1) {
      // Special case for when column is full
      for (int i = this.getRows() - 1; i > 0; i--) {
        this.board.get(column)[i] = this.getChip(i - 1, column);
      }
      this.board.get(column)[0] = chip;
    } else {
      // Simple case, and getChipPlacement already handled making it drop
      this.board.get(column)[row] = chip;
    }
  }

  protected Chip[] hasNewWinner() {
    int[] score = {0, 0};
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
        if (hasRowWinner) {
          if (this.getChip(row, column) == this.players[0]) {
            score[0]++;
          } else {
            score[1]++;
          }
        }
        if (hasColumnWinner) {
          if (this.getChip(row, column) == this.players[0]) {
            score[0]++;
          } else {
            score[1]++;
          }
        }
        if (hasDiagonalOneWinner) {
          if (this.getChip(row, column) == this.players[0]) {
            score[0]++;
          } else {
            score[1]++;
          }
        }
        if (hasDiagonalTwoWinner) {
          if (this.getChip(row, column) == this.players[0]) {
            score[0]++;
          } else {
            score[1]++;
          }
        }
      }
    }

    if (score[0] != score[1]) {
      // We have a winner!
      int winnerIndex = 0;
      if (score[1] > score[0]) winnerIndex = 1;
      return new Chip[] {Chip.BLUE, this.players[winnerIndex]};
    }

    return new Chip[] {Chip.EMPTY, Chip.EMPTY};
  }
}
