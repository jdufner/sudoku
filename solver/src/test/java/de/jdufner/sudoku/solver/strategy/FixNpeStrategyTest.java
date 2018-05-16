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
import de.jdufner.sudoku.solver.service.Solver;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class FixNpeStrategyTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(FixNpeStrategyTest.class);

  public FixNpeStrategyTest(String name) {
    super(name);
  }

  public void testFirstSolution1() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:0,2,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,3,0,7,4,0,8,0,0,0,0,0,0,0,0,0,3,0,0,2,0,8,0,0,4,0,0,1,0,6,0,0,5,0,0,0,0,0,0,0,0,0,1,0,7,8,0,5,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,4,0");
    Solver solver = getStrategySolver();
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
  }

  public void testFirstSolution2() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:0,0,0,0,0,9,7,0,0,0,1,0,0,0,0,9,0,0,4,0,0,0,5,3,0,2,0,0,0,0,0,0,7,0,0,0,0,3,0,0,0,0,0,4,0,0,0,0,8,0,0,0,0,0,0,6,0,4,1,0,0,0,2,0,0,9,0,0,0,0,5,0,0,0,8,6,0,0,0,0,0");
    Solver solver = getStrategySolver();
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
  }

  public void testFirstSolutionHelper() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:0,2,6,0,0,0,0,0,0,0,0,0,6,0,0,0,0,3,0,7,4,0,8,0,0,0,0,0,0,0,0,0,3,0,0,2,0,8,0,0,4,0,0,1,7,6,0,0,5,0,0,0,0,0,0,0,0,0,1,0,7,8,0,5,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,4,0");
    Solver solver = getStrategySolver();
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
  }

}
