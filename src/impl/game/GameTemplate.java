package impl.game;

import java.util.List;
import java.util.ArrayList;

import api.Game;
import api.Chip;
import exc.GameIndexOutOfBoundsException;
import exc.GameStateException;

public abstract class GameTemplate extends Game {
  // We only store numRows as the global source of truth for the numColumns should
  // be the length of the board List
  protected int numRows;
  protected List<Chip[]> board;
  protected int currentPlayerIndex;
  protected final Chip[] players = {Chip.RED, Chip.BLUE};
  protected Chip winner;
  protected boolean gameOver = false;

  /**
   * A nice method for constructors to use, since classes don't inherit constructors
   * we can't really use the template pattern here
   */
  protected final void initializeBoard(int rows, int columns) {
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

  public final int getRows() {
    return this.numRows;
  }

  public final int getColumns() {
    return this.board.size();
  }

  /**
   * A helper function that can be useful to most board games
   */
  protected final boolean isWithinBoard(int row, int column) {
    return !(row < 0 || row >= this.getRows() || column < 0 || column >= this.getColumns());
  }

  public final Chip getChip(int row, int column) {
    if (!this.isWithinBoard(row, column)) {
      throw new GameIndexOutOfBoundsException(row, column);
    }
    return this.board.get(column)[row];
  }

  /**
   * We use the template pattern here as you are allowed to override this method if
   * the rules of your game differs, but we provide this as a default as it is very common
   */
  protected boolean placeChipIsAllowed(int row, int column) {
    return this.isWithinBoard(row, column) && this.board.get(column)[row] == Chip.EMPTY;
  }

  /**
   * Again providing default, the return value should be an array of size 2 in the form
   * of {row, column}
   */
  protected int[] getChipPlacement(int row, int column) {
    return new int[] {row, column};
  }

  public final void placeChip(int row, int column) throws GameStateException {
    if (this.isGameOver()) {
      throw new GameStateException();
    }

    if (!this.placeChipIsAllowed(row, column)) {
      throw new GameIndexOutOfBoundsException(row, column);
    }

    int[] finalRowAndColumn = this.getChipPlacement(row, column);
    int finalRow = finalRowAndColumn[0];
    int finalColumn = finalRowAndColumn[1];

    // Place chip for current player
    this.board.get(finalColumn)[finalRow] = this.getCurrentPlayer();

    // Make it the next players turn
    this.currentPlayerIndex = (this.currentPlayerIndex + 1) % 2;

    // Notify the view
    this.setChanged();
    this.notifyObservers();
  }

  public final Chip getWinningPlayer() throws GameStateException {
    if (!this.isGameOver()) {
      throw new GameStateException();
    }
    // Game is over
    if (winner == Chip.EMPTY) {
      // It was a tie so we throw exception as per specification
      throw new GameStateException();
    }
    return winner;
  }

  public final Chip getCurrentPlayer() {
    if (this.isGameOver()) {
      return Chip.EMPTY;
    }
    return this.players[this.currentPlayerIndex];
  }

  /**
   * This should check whether a new winner has been established, assuming no winner
   * has been found yet, if the first returned chip is empty it means no winner has
   * been found, else the second returned chip specifies the winner, where empty means
   * a tie.
   */
  protected abstract Chip[] hasNewWinner();

  public final boolean isGameOver() {
    if (this.gameOver) {
      return true;
    }

    Chip[] hasNewWinnerResult = this.hasNewWinner();
    boolean gameFinished = hasNewWinnerResult[0] != Chip.EMPTY;
    Chip newWinner = hasNewWinnerResult[1];

    if (gameFinished) {
      this.gameOver = true;
      this.winner = newWinner;
    }

    return this.gameOver;
  }
}
