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
package de.jdufner.sudoku.solver.backtracking;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.service.Solver;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class FixNpeBacktrackingTest extends AbstractSolverTestCase {
  private static final Logger LOG = Logger.getLogger(Backtracking.class);

  public FixNpeBacktrackingTest(String name) {
    super(name);
  }

  public void testFirstSolution1() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:9,0,0,0,8,3,0,0,0,5,0,0,0,7,0,0,0,0,0,4,0,0,0,0,0,6,0,0,1,0,4,0,6,0,0,0,0,0,0,0,0,0,8,0,5,0,0,0,0,0,0,0,0,0,8,0,0,0,0,0,5,0,0,0,0,0,1,0,0,0,4,0,0,0,0,2,0,0,0,0,0");
    Solver solver = getBacktrackingSolver();
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
  }

  public void testFirstSolution2() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:9,2,1,6,8,3,0,5,0,5,8,6,9,7,4,1,3,2,3,4,7,5,0,0,9,6,8,7,1,8,4,5,6,2,9,3,4,6,0,0,0,0,8,0,5,2,0,5,8,0,0,0,0,0,8,0,4,0,6,0,5,2,1,6,5,2,1,0,8,0,4,0,1,7,0,2,4,5,0,8,0");
    Solver solver = getBacktrackingSolver();
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
  }

  public void testFirstSolution3() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:9,2,1,6,8,3,7,5,4,5,8,6,9,7,4,1,3,2,3,4,7,5,2,1,9,6,8,7,1,8,4,5,6,2,9,3,4,6,9,7,3,2,8,1,5,2,3,5,8,1,9,4,7,6,8,9,4,3,6,7,5,2,1,6,5,2,1,9,8,3,4,7,1,7,3,2,4,5,6,8,9");
    Solver solver = getBacktrackingSolver();
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
  }

}
