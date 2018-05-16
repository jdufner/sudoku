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

// $Id$
package de.jdufner.sudoku.solver.strategy;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.solver.service.Solution;
import de.jdufner.sudoku.solver.service.Solver;
import de.jdufner.sudoku.solver.service.StrategySolver;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class HandelsblattSudokuTest extends AbstractSolverTestCase {
  private static final Logger LOG = Logger.getLogger(HandelsblattSudokuTest.class);

  public HandelsblattSudokuTest(String name) {
    super(name);
  }

  public void testMittel20071109() {
    final String mySudoku = "9:" + //
        "0,0,0,4,0,5,7,6,2," + //
        "0,7,0,3,6,1,0,0,4," + //
        "0,0,8,0,0,9,0,0,5," + //
        "0,0,0,0,4,0,0,5,0," + //
        "1,0,0,0,7,6,9,0,0," + //
        "0,0,0,2,0,8,0,0,0," + //
        "3,0,0,1,9,0,0,0,0," + //
        "0,4,1,0,0,0,6,0,0," + //
        "0,9,2,0,0,0,5,0,0"; //
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(mySudoku);
    Solver solver = new StrategySolver();
    Grid result = solver.solve(sudoku);
    LOG.debug(result.toString());
    assertTrue(result.isSolved());
  }

  public void testSchwer20071109() {
    final String mySudoku = "9:" + //
        "2,0,5,0,0,1,0,0,0," + //
        "0,0,0,0,0,9,0,0,0," + //
        "9,0,8,0,2,5,0,3,0," + //
        "0,0,0,8,3,0,9,4,0," + //
        "1,0,6,7,0,0,0,0,0," + //
        "0,3,0,0,1,2,0,0,0," + //
        "0,0,0,0,0,0,0,1,0," + //
        "7,0,0,0,6,3,8,9,2," + //
        "0,2,0,0,0,0,4,0,0"; //
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(mySudoku);
    Solver solver = new StrategySolver();
    Grid result = solver.solve(sudoku);
    LOG.debug(result.toString());
    assertTrue(result.isSolved());
  }

  public void testSolverSchwer20071130() {
    final String mySudoku = "9:" + //
        "0,0,0,1,3,0,0,2,0," + //
        "0,7,0,9,0,0,0,8,4," + //
        "0,2,0,4,0,8,9,5,1," + //
        "7,0,0,0,0,6,0,3,5," + //
        "0,0,0,0,0,0,0,4,9," + //
        "2,0,0,0,9,0,1,6,8," + //
        "8,0,0,0,0,7,0,0,0," + //
        "0,1,0,0,4,0,0,0,6," + //
        "0,0,0,3,0,0,5,0,0"; //
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(mySudoku);
    Solver solver = null;

    // ohne Backtracking
    solver = getStrategySolver();
    Grid result1 = solver.solve(sudoku);
    LOG.debug(result1.toString());
    assertTrue(result1.isSolved());

    // mit Backtracking
    solver = getStrategySolver();
    Grid result2 = solver.solve(sudoku);
    LOG.debug(result2.toString());
    assertTrue(result2.isSolved());
  }

  public void testExtendedSolverSchwer20071130() {
    final String mySudoku = "9:" + //
        "0,0,0,1,3,0,0,2,0," + //
        "0,7,0,9,0,0,0,8,4," + //
        "0,2,0,4,0,8,9,5,1," + //
        "7,0,0,0,0,6,0,3,5," + //
        "0,0,0,0,0,0,0,4,9," + //
        "2,0,0,0,9,0,1,6,8," + //
        "8,0,0,0,0,7,0,0,0," + //
        "0,1,0,0,4,0,0,0,6," + //
        "0,0,0,3,0,0,5,0,0"; //
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(mySudoku);
    ExtendedSolver extendedSolver = getStrategySolver();
    Solution solution = extendedSolver.getSolution(sudoku);
    LOG.debug(solution.toString());
    assertTrue(solution.getResult().isSolved());
  }

}
