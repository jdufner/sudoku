package de.jdufner.games.sudoku.server.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jürgen Dufner
 * @since 0.3
 */
@SpringBootApplication
public class SudokuApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SudokuApplication.class, args);
  }

}
