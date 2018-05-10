package de.jdufner.games.sudoku.server.app;

import static org.assertj.core.api.Assertions.assertThat;

import de.jdufner.games.sudoku.server.controller.SudokuController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SudokuApplicationTest {

  @Autowired
  private SudokuController sudokuController;

  @Test
  public void contextLoads() {
    assertThat(sudokuController).isNotNull();
  }

}
