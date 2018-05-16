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


package de.jdufner.sudoku.builder.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.service.Solver;
import de.jdufner.sudoku.test.AbstractGeneratorTestCase;
import org.apache.log4j.Logger;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-28
 * @version $Revision$
 */
public class NachbarschaftUtilsTest extends AbstractGeneratorTestCase {

  private static final Logger LOG = Logger.getLogger(NachbarschaftUtilsTest.class);

  private Solver solver = null;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    solver = getStrategySolverWithBacktracking();
  }

  public void testCheckNachbarschaft1() {
    Grid sudoku = SudokuFactory.INSTANCE.buildFilled(SudokuSize.DEFAULT);
    LOG.debug(sudoku);
    assertFalse(NachbarschaftUtils.checkNachbarschaft(sudoku));
  }

  public void testCheckNachbachschaft2() {
    Grid underDeterminedSudoku = SudokuFactory.INSTANCE.buildShuffled(SudokuSize.DEFAULT);
    Grid sudoku = solver.solve(underDeterminedSudoku);
    LOG.debug(sudoku);
    assertTrue(NachbarschaftUtils.checkNachbarschaft(sudoku));
  }

}
