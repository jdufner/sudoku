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


package de.jdufner.sudoku.solver.ddt.sudoku_sf_net;

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
public final class SudokuSfNetTest extends AbstractSolverExcelTestCase {

  private static final Logger LOG = Logger.getLogger(SudokuSfNetTest.class);

  private String sudokuAsString;
  private boolean solveable;

  public SudokuSfNetTest(final String sudokuAsString, final String solveable) {
    this.sudokuAsString = sudokuAsString;
    this.solveable = Boolean.parseBoolean(solveable);
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"9:0,0,0,0,0,0,0,1,0,4,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,5,0,4,0,7,0,0,8,0,0,0,3,0,0,0,0,1,0,9,0,0,0,0,3,0,0,4,0,0,2,0,0,0,5,0,1,0,0,0,0,0,0,0,0,8,0,6,0,0,0", "True"},
        {"9:0,0,0,0,0,0,0,1,2,0,0,0,0,3,5,0,0,0,0,0,0,6,0,0,0,7,0,7,0,0,0,0,0,3,0,0,0,0,0,4,0,0,8,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,2,0,0,0,0,0,8,0,0,0,0,0,4,0,0,5,0,0,0,0,6,0,0", "True"},
        {"9:0,0,0,0,4,0,0,3,0,9,8,0,6,0,1,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,1,0,0,4,0,5,0,7,0,0,6,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,9,0,8,0,7,6,0,7,0,0,3,0,0,0,0", "False"},
        {"9:0,0,0,0,3,0,0,0,0,0,2,0,5,0,1,0,0,0,4,0,0,0,0,0,9,7,0,0,0,0,0,0,0,0,0,5,0,0,8,0,7,0,6,0,0,3,0,0,0,0,0,0,0,0,0,5,9,0,0,0,0,0,1,0,0,0,6,0,4,0,3,0,0,0,0,0,8,0,0,0,0", "False"},
        {"9:0,0,0,0,0,9,0,0,0,0,0,0,0,1,4,7,0,0,0,0,2,0,0,0,0,0,0,7,0,0,0,0,0,0,8,6,5,0,0,0,3,0,0,0,2,9,4,0,0,0,0,0,0,1,0,0,0,0,0,0,4,0,0,0,0,6,2,5,0,0,0,0,0,0,0,8,0,0,0,0,0", "True"},
        {"9:0,2,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,3,0,7,4,0,8,0,0,0,0,0,0,0,0,0,3,0,0,2,0,8,0,0,4,0,0,1,0,6,0,0,5,0,0,0,0,0,0,0,0,0,1,0,7,8,0,5,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,4,0", "True"},
        {"9:0,0,0,0,0,9,7,0,0,0,1,0,0,0,0,9,0,0,4,0,0,0,5,3,0,2,0,0,0,0,0,0,7,0,0,0,0,3,0,0,0,0,0,4,0,0,0,0,8,0,0,0,0,0,0,6,0,4,1,0,0,0,2,0,0,9,0,0,0,0,5,0,0,0,8,6,0,0,0,0,0", "False"},
        {"9:0,0,9,6,0,0,0,5,0,6,0,0,0,0,0,0,0,0,3,0,0,0,2,1,0,8,0,0,0,0,0,0,5,0,0,0,0,7,0,0,0,0,0,2,0,0,0,0,9,0,0,0,0,0,0,8,0,5,6,0,0,0,3,0,0,0,0,0,0,0,0,4,0,1,0,0,0,8,7,0,0", "True"},
        {"9:0,0,0,0,0,0,6,0,3,0,0,0,0,4,0,2,0,0,0,5,0,9,7,0,0,0,8,0,0,0,1,0,0,0,0,0,0,9,0,0,0,0,0,4,0,0,0,0,0,0,3,0,0,0,1,0,0,0,9,4,0,7,0,0,0,8,0,5,0,0,0,0,6,0,4,0,0,0,0,0,0", "True"},
        {"9:0,0,8,0,4,0,0,0,5,0,0,6,3,0,0,0,0,0,7,2,0,0,0,0,0,0,0,5,0,0,1,0,0,0,0,0,3,0,0,0,0,0,0,0,4,0,0,0,0,0,6,0,0,8,0,0,0,0,0,0,0,1,3,0,0,0,0,0,5,9,0,0,4,0,0,0,7,0,2,0,0", "True"},
        {"9:0,0,0,0,0,0,0,6,0,1,0,0,9,0,4,0,0,0,0,3,0,7,0,0,8,0,0,0,0,6,0,0,0,9,0,0,0,8,0,0,3,0,0,7,0,0,0,2,0,0,0,5,0,0,0,0,9,0,0,2,0,4,0,0,0,0,1,0,5,0,0,3,0,7,0,0,0,0,0,0,0", "False"},
        {"9:0,0,0,0,0,0,0,7,0,4,0,0,0,9,0,1,0,0,0,0,3,6,0,2,0,0,0,0,5,0,0,0,0,6,3,0,0,0,0,0,8,0,0,0,0,0,1,9,0,0,0,0,2,0,0,0,0,4,0,3,5,0,0,0,0,2,0,1,0,0,0,8,0,7,0,0,0,0,0,0,0", "True"}
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
