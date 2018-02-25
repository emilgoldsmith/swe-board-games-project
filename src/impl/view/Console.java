package impl.view;

import api.View;
import exc.GameStateException;
import api.Game;
import api.Chip;

import java.util.Observable;
import java.lang.System;
import java.util.Scanner;

public class Console extends View {
  private Game observableGame;
  private Random rng = new Random();

  public Console(Game game) {
    this.observableGame = game;
    this.observableGame.addObserver(this);
  }

  public void render(Game game) {
    for (int i = 0; i < game.getRows(); i++) {
      for (int j = 0; j < game.getColumns(); j++) {
        String token = ".";
        if (game.getChip(i, j) == Chip.BLUE) {
          token = "X";
        } else if (game.getChip(i, j) == Chip.RED) {
          token = "O";
        }
        System.out.print(token);
      }
      System.out.println();
    }
  }

  public void update(Observable observable, Object object) {
    Game gameWeAreObserving = (Game) observable;
    this.render(gameWeAreObserving);
    if (!gameWeAreObserving.isGameOver()) {
      // I tried not adding the newline at the end and using System.out.flush() but it just wasn't working so the only way
      // to flush the output seems to be adding the newline at the end
      System.out.printf("Please enter the column you would like to play in between 0 and %d:\n", gameWeAreObserving.getColumns() - 1);
      Scanner scanner = new Scanner(System.in);
      int columnToPlay = scanner.nextInt();
      System.out.printf("Please enter the row you would like to play in between 0 and %d:\n", gameWeAreObserving.getRows() - 1);
      int rowToPlay = scanner.nextInt();
      try {
        gameWeAreObserving.placeChip(rowToPlay, columnToPlay);
      } catch (GameStateException e) {
        // There was an error so we terminate the game
        this.observableGame.deleteObserver(this);
        System.err.println("There was an error while trying to place a chip, game terminating");
      }
    } else {
      // Game is over so stop observing
      this.observableGame.deleteObserver(this);
    }
  }
}
