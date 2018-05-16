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
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class BacktrackingPerformanceTest extends AbstractSolverTestCase {
  private static final Logger LOG = Logger.getLogger(BacktrackingPerformanceTest.class);

  private Grid sudoku = null;
  private long startTime = 0;
  private long endTime = 0;

  @Override
  public void setUp() {
    // sudoku = SudokuFactory.buildSudoku(Examples.SCHWER);
    sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:0,0,0,0,4,0,0,3,0,9,8,0,6,0,1,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,1,0,0,4,0,5,0,7,0,0,6,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,9,0,8,0,7,6,0,7,0,0,3,0,0,0,0");
  }

  public void testFirstSolution() {
    startTime = System.currentTimeMillis();
    Backtracking backtracking = new Backtracking(sudoku, 1);
    Grid result1 = backtracking.firstSolution();
    assertNotNull(result1);
    assertTrue(result1.isSolved());
    assertTrue(result1.isSolvedByCheckSum());
    assertTrue(result1.isValid());
    endTime = System.currentTimeMillis();
    LOG.info("Dauer parallel: " + (endTime - startTime) + " ms");
  }

}
