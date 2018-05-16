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


package de.jdufner.sudoku.solver.ddt.sudoku_org_uk_daily;

import static org.junit.Assert.assertEquals;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.ddt.AbstractSolverExcelTestCase;
import de.jdufner.sudoku.solver.service.Solver;
import java.util.Arrays;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
@RunWith(Parameterized.class)
public final class SudokuOrgUkDailyTest extends AbstractSolverExcelTestCase {

  private static final Logger LOG = Logger.getLogger(SudokuOrgUkDailyTest.class);

  private String sudokuAsString;
  private boolean solveable;

  public SudokuOrgUkDailyTest(final String sudokuAsString, final String solveable) {
    this.sudokuAsString = sudokuAsString;
    this.solveable = Boolean.parseBoolean(solveable);
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"9:0,6,0,0,0,0,0,9,0,4,0,0,0,0,0,0,0,2,0,0,0,4,5,7,0,0,1,0,8,0,5,0,0,0,1,0,2,0,0,9,0,6,0,0,8,0,1,0,0,0,8,0,6,0,5,0,0,2,4,1,0,0,0,9,0,0,0,0,0,0,0,5,0,2,0,0,0,0,0,7,0", "False"},
        {"9:5,0,0,0,8,0,0,0,4,0,8,0,7,0,9,0,5,0,4,2,0,0,0,0,0,7,0,0,0,9,0,0,0,7,0,0,2,1,0,0,0,0,0,4,6,0,0,4,0,0,0,8,0,0,0,4,0,0,0,0,0,6,7,0,3,0,8,0,1,0,2,0,9,0,0,0,3,0,0,0,5", "False"},
        {"9:8,6,0,0,0,0,0,3,9,1,0,0,0,0,0,2,0,6,0,0,9,0,0,0,8,0,0,0,0,5,8,0,9,0,0,0,0,0,0,7,0,6,0,0,0,0,0,0,3,0,4,5,0,0,0,0,4,0,0,0,1,0,0,9,0,8,0,0,0,0,0,2,3,5,0,0,0,0,0,7,4", "True"},
        {"9:0,0,4,0,0,0,9,0,0,2,3,0,0,0,0,0,7,8,0,0,0,3,6,7,0,0,0,4,0,0,6,0,2,0,8,0,0,0,6,0,0,0,2,0,0,0,2,0,8,0,9,0,0,3,0,0,0,7,9,1,0,0,0,6,5,0,0,0,0,0,9,4,0,0,8,0,0,0,3,0,0", "False"}
    });
  }

  @Test
  public void testStrategySolver() {
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(sudokuAsString);
    Solver solver = getStrategySolver();
    Grid result = solver.solve(sudoku);
    if (LOG.isDebugEnabled()) {
      LOG.debug(result.toString());
    }
    assertEquals(solveable, result.isSolved());
  }

  // public void testBacktrackingSolver() {
  // Sudoku sudoku = SudokuFactory.buildSudoku(sudokuAsString);
  // Solver solver = getBacktrackingSolver();
  // Sudoku result = solver.getSolution(sudoku);
  // if (log.isDebugEnabled()) {
  // log.debug(result.toString());
  // }
  // assertTrue(result.isSolved());
  // }

}
