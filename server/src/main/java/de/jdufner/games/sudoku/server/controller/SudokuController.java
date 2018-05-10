package de.jdufner.games.sudoku.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@RestController
public class SudokuController {

  @GetMapping
  public String helloWorld() {
    return "Hello World!";
  }

}
