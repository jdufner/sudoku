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


package de.jdufner.sudoku.solver.service;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2009-12-14
 * @version $Revision$
 */
public final class ExtendedSolverTest extends AbstractSolverTestCase {

  private final static Logger LOG = Logger.getLogger(ExtendedSolverTest.class);

  private Grid quest = SudokuFactory.INSTANCE
  //.buildSudoku(".1...4..3.3..5..7.74....1.......25....1...6....35.......2....47.9..7..2.4..3...1.");
      .buildSudoku("6..9..8..2.857..1..3.8..5....34..7.29...2...68.7..54....1..4.7..9..871.5..6..3..9");
  //      .buildSudoku(Examples.SEHR_SCHWER);
  private ExtendedSolver extendedSolver;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    extendedSolver = getStrategySolverWithBacktracking();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testSolve() {
    final Solution solution = extendedSolver.getSolution(quest);
    LOG.debug(solution);
  }
}
