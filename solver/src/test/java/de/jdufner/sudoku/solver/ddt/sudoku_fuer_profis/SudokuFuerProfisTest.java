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


package de.jdufner.sudoku.solver.ddt.sudoku_fuer_profis;

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
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since
 * 
 */
@RunWith(Parameterized.class)
public final class SudokuFuerProfisTest extends AbstractSolverExcelTestCase {

  private static final Logger LOG = Logger.getLogger(SudokuFuerProfisTest.class);

  private String sudokuAsString;
  private boolean solveable;

  public SudokuFuerProfisTest(final String sudokuAsString, final String solveable) {
    this.sudokuAsString = sudokuAsString;
    this.solveable = Boolean.parseBoolean(solveable);
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"79..523..34.....9....7....8.2.1957.....3....2..6..81....4..6..3.3........61..32..", "TRUE"}
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

}
