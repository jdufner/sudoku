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


package de.jdufner.sudoku.solver.strategy;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public class StrategyTest extends AbstractSolverTestCase {
  private static final Logger LOG = Logger.getLogger(StrategyTest.class);
  private static final boolean SHORT_TEST = true;

  private ExtendedSolver solver;

  public StrategyTest(String name) {
    super(name);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    solver = getStrategySolver();
  }

  public void testStrategySolver() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:0,0,0,0,0,4,0,0,7,2,0,0,0,0,0,8,0,6,0,0,0,0,0,3,0,0,0,0,3,0,0,0,0,0,5,0,0,0,4,0,8,0,0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,9,6,0,2,0,0,1,4,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0");
    Grid result = solver.solve(sudoku);
    if (LOG.isDebugEnabled()) {
      LOG.debug(result.toString());
    }
    assertFalse(sudoku.isSolved());
    // Solution solution = solver.getSolution2(sudoku);
    // if (LOG.isDebugEnabled()) {
    // LOG.debug(solution);
    // }
    // assertEquals(Level.SEHR_SCHWER, solution.getLevel());
  }

  public void testStrategySolver1() {
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.SCHWER);
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(solver.isSolvable(sudoku));
    assertTrue(solver.isUnique(sudoku));
  }

  public void testStrategySolver2() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:7,0,0,1,0,8,0,0,0,0,9,0,0,0,0,0,3,2,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,1,0,0,9,6,0,0,2,0,0,0,0,0,0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,1,0,0,0,3,2,0,0,0,0,0,0,6");
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(solver.isSolvable(sudoku));
    assertTrue(solver.isUnique(sudoku));
  }

  public void _testStrategySolver3() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:0,8,2,0,1,0,0,0,0,7,0,0,0,0,0,0,3,0,0,0,0,0,0,6,0,0,5,0,0,0,0,0,0,0,8,0,3,0,0,7,0,0,0,0,0,0,0,0,0,0,0,1,0,4,4,0,1,0,0,0,0,0,6,0,0,0,0,5,0,0,0,0,0,0,0,8,0,0,0,0,0");
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(solver.isSolvable(sudoku));
    assertTrue(solver.isUnique(sudoku));
    //    Sudoku sudoku1 = SudokuFactory
    //        .buildSudoku("9:0,8,2,0,1,0,0,0,0,7,0,0,0,0,0,0,3,0,0,0,0,0,0,6,0,0,5,0,0,0,0,0,0,0,8,0,3,0,0,7,0,0,0,0,0,0,0,0,0,0,0,1,0,4,4,0,1,0,0,0,0,0,6,0,0,0,0,5,0,0,0,0,0,0,0,8,0,0,0,0,0");
    //    assertTrue(backtrackingSolver.isSolvable(sudoku1));
    //    assertTrue(backtrackingSolver.isUnique(sudoku1));
  }

  public void testStrategySolver4() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:9,0,0,1,0,0,3,0,0,4,0,0,0,0,0,0,5,0,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,4,0,0,6,8,0,0,0,0,0,0,0,0,0,0,0,0,6,7,8,3,2,0,0,7,0,0,0,0,0,0,0,0,0,0,1,0,0");
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(solver.isSolvable(sudoku));
    assertTrue(solver.isUnique(sudoku));
  }

  public void _testStrategySolver5() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:9,2,1,6,8,3,0,5,0,5,8,6,9,7,4,1,3,2,3,4,7,5,0,0,9,6,8,7,1,8,4,5,6,2,9,3,4,6,0,0,0,0,8,0,5,2,0,5,8,0,0,0,0,0,8,0,4,0,6,0,5,2,1,6,5,2,1,0,8,0,4,0,1,7,0,2,4,5,0,8,0");
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(solver.isSolvable(sudoku));
    assertTrue(solver.isUnique(sudoku));
  }

  public void testStrategySolver6() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:8,0,0,0,0,3,9,6,2,0,6,2,8,0,0,7,0,0,0,9,0,0,0,0,5,0,8,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,9,0,0,5,1,8,0,6,2,0,3,0,0,4,0,9");
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    if (!SHORT_TEST) {
      assertTrue(solver.isSolvable(sudoku));
      assertFalse(solver.isUnique(sudoku));
    }
  }

  public void testStrategySolver7() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku(".8...97...1..8.9..49..53.28.2.5.738.83.92.547.7.83.2...6.415892249378.5.158692473");
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    if (!SHORT_TEST) {
      assertTrue(solver.isSolvable(sudoku));
      assertFalse(solver.isUnique(sudoku));
    }
  }

  public void testStrategySolver8() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("7.4..9.125.9..1.8782176....152347968697...134483196725245913876.16.7.25..78652..1");
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    if (!SHORT_TEST) {
      assertTrue(solver.isSolvable(sudoku));
      assertFalse(solver.isUnique(sudoku));
    }
  }

  public void testStrategySolver9() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("3.152..644.5...32.26.3.4851714239586.264.5.135..16.24.15..436.2..2...4..64...21..");
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    if (!SHORT_TEST) {
      assertTrue(solver.isSolvable(sudoku));
      assertFalse(solver.isUnique(sudoku));
    }
  }

}
