/*
 * Sudoku is a puzzle game. It solves and generates puzzles in different
 * formats.
 * Copyright (C) 2008-2018  Juergen Dufner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package de.jdufner.sudoku.solver.backtracking;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.solver.service.Solver;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class BacktrackingTest extends AbstractSolverTestCase {
  private static final Logger LOG = Logger.getLogger(BacktrackingTest.class);
  private static final boolean SHORT_TEST = true;

  private Solver solver = null;

  public BacktrackingTest(String name) {
    super(name);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    solver = getBacktrackingSolver();
  }

  public void testProblem() {
    Grid sudoku1 = SudokuFactory.INSTANCE
        .buildSudoku("9:0,0,0,0,4,0,0,3,0,9,8,0,6,0,1,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,1,0,0,4,0,5,0,7,0,0,6,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,9,0,8,0,7,6,0,7,0,0,3,0,0,0,0");
    Backtracking backtracking = new Backtracking(sudoku1, 1);
    Grid result1 = backtracking.firstSolution();
    assertNotNull(result1);
    assertTrue(result1.isSolved());
    assertTrue(result1.isSolvedByCheckSum());
    assertTrue(result1.isValid());

    Solver s = getBacktrackingSolver();
    Grid sudoku2 = SudokuFactory.INSTANCE
        .buildSudoku("9:0,0,0,0,4,0,0,3,0,9,8,0,6,0,1,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,1,0,0,4,0,5,0,7,0,0,6,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,9,0,8,0,7,6,0,7,0,0,3,0,0,0,0");
    Grid result2 = s.solve(sudoku2);
    assertTrue(result2.isSolved());
    assertTrue(result2.isSolvedByCheckSum());
    assertTrue(result2.isValid());
  }

  public void testFirstSolution1() {
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.SCHWER);
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
    assertTrue(result.isSolvedByCheckSum());
    assertTrue(result.isValid());
    if (!SHORT_TEST) {
      assertTrue(solver.isSolvable(sudoku));
      assertTrue(solver.isUnique(sudoku));
    }
  }

  /**
   * Dauer: ca. 5 Sekunden
   * 
   * @throws Exception
   */
  public void testFirstSolution2() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:7,0,0,1,0,8,0,0,0,0,9,0,0,0,0,0,3,2,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,1,0,0,9,6,0,0,2,0,0,0,0,0,0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,1,0,0,0,3,2,0,0,0,0,0,0,6");
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
    assertTrue(result.isSolvedByCheckSum());
    assertTrue(result.isValid());
    if (!SHORT_TEST) {
      assertTrue(solver.isSolvable(sudoku));
      assertTrue(solver.isUnique(sudoku));
    }
  }

  public void testFirstSolution3() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:0,8,2,0,1,0,0,0,0,7,0,0,0,0,0,0,3,0,0,0,0,0,0,6,0,0,5,0,0,0,0,0,0,0,8,0,3,0,0,7,0,0,0,0,0,0,0,0,0,0,0,1,0,4,4,0,1,0,0,0,0,0,6,0,0,0,0,5,0,0,0,0,0,0,0,8,0,0,0,0,0");
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
    assertTrue(result.isSolvedByCheckSum());
    assertTrue(result.isValid());
    if (!SHORT_TEST) {
      assertTrue(solver.isSolvable(sudoku));
      assertTrue(solver.isUnique(sudoku));
    }
  }

  /**
   * Dauer: ca. 780 Sekunden / 13 Minuten
   * 
   * @throws Exception
   */
  public void testFirstSolution4() {
    if (!SHORT_TEST) {
      Grid sudoku = SudokuFactory.INSTANCE
          .buildSudoku("9:9,0,0,1,0,0,3,0,0,4,0,0,0,0,0,0,5,0,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,4,0,0,6,8,0,0,0,0,0,0,0,0,0,0,0,0,6,7,8,3,2,0,0,7,0,0,0,0,0,0,0,0,0,0,1,0,0");
      Grid result = solver.solve(sudoku);
      LOG.debug(result);
      assertTrue(result.isSolved());
      assertTrue(result.isSolvedByCheckSum());
      assertTrue(result.isValid());
      assertTrue(solver.isSolvable(sudoku));
      assertTrue(solver.isUnique(sudoku));
    }
  }

  public void testFirstSolution5() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:9,2,1,6,8,3,0,5,0,5,8,6,9,7,4,1,3,2,3,4,7,5,0,0,9,6,8,7,1,8,4,5,6,2,9,3,4,6,0,0,0,0,8,0,5,2,0,5,8,0,0,0,0,0,8,0,4,0,6,0,5,2,1,6,5,2,1,0,8,0,4,0,1,7,0,2,4,5,0,8,0");
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
    assertTrue(result.isSolvedByCheckSum());
    assertTrue(result.isValid());
    if (!SHORT_TEST) {
      assertTrue(solver.isSolvable(sudoku));
      assertTrue(solver.isUnique(sudoku));
    }
  }

  public void testCountSolutions() {
    if (!SHORT_TEST) {
      Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.SCHWER);
      Backtracking backtracking = new Backtracking(sudoku, 1);
      int numberSolutions = backtracking.countSolutions();
      LOG.debug("SCHWER numberSolutions=" + numberSolutions);
    }
  }

  public void testCountSolutions1() {
    if (!SHORT_TEST) {
      Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.KLEIN_EINDEUTIG);
      Backtracking backtracking = new Backtracking(sudoku, 1);
      int numberSolutions = backtracking.countSolutions();
      LOG.debug("KLEIN_EINDEUTIG numberSolutions=" + numberSolutions);
      assertEquals(1, numberSolutions);
    }
  }

  public void testCountSolutions2() {
    if (!SHORT_TEST) {
      Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.KLEIN_MEHRDEUTIG_1);
      Backtracking backtracking = new Backtracking(sudoku, 1);
      int numberSolutions = backtracking.countSolutions();
      LOG.debug("KLEIN_MEHRDEUTIG_1 numberSolutions=" + numberSolutions);
      assertEquals(2, numberSolutions);
    }
  }

  public void testCountSolutions3() {
    if (!SHORT_TEST) {
      Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.KLEIN_MEHRDEUTIG_2);
      Backtracking backtracking = new Backtracking(sudoku, 1);
      int numberSolutions = backtracking.countSolutions();
      LOG.debug("KLEIN_MEHRDEUTIG_2 numberSolutions=" + numberSolutions);
      assertEquals(4, numberSolutions);
    }
  }

  public void testCountSolutions4() {
    if (!SHORT_TEST) {
      Grid sudoku = SudokuFactory.INSTANCE.buildEmpty(SudokuSize.VIER);
      Backtracking backtracking = new Backtracking(sudoku, 1);
      int numberSolutions = backtracking.countSolutions();
      LOG.debug("EMPTY_VIER numberSolutions=" + numberSolutions);
      assertEquals(288, numberSolutions);
    }
  }

  public void testFirstSolutionOfShuffled() {
    Grid underDeterminedSudoku = SudokuFactory.INSTANCE.buildShuffled(SudokuSize.DEFAULT);
    LOG.debug(underDeterminedSudoku);
    Backtracking backtracking = new Backtracking(underDeterminedSudoku, 1);
    Grid firstSolution = backtracking.firstSolution();
    LOG.debug(firstSolution);
  }
}
