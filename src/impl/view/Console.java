package impl.view;

import api.View;
import exc.GameStateException;
import api.Game;
import api.Chip;

import java.util.Observable;
import java.util.Random;
import java.lang.System;
import java.lang.Thread;

public class Console extends View {
  private Game observableGame;
  private Random rng = new Random();

  public Console(Game game) {
    this.observableGame = game;
    this.observableGame.addObserver(this);
  }

  public void render(Game game) {
    // Clear screen before showing board
    for (int i = 0; i < 100; i++) {
      System.out.println();
    }
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 7; j++) {
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
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // Do nothing, we don't care about this
      }
      int columnToPlay = rng.nextInt(7);
      while (gameWeAreObserving.getChip(0, columnToPlay) != Chip.EMPTY) {
        columnToPlay = rng.nextInt(7);
      }
      try {
        gameWeAreObserving.placeChip(0, columnToPlay);
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
