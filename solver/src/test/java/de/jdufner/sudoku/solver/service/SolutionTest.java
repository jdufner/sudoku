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
package de.jdufner.sudoku.solver.service;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

public class SolutionTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(SolutionTest.class);

  public void testToString() {
    Grid quest = SudokuFactory.INSTANCE
        .buildSudoku(".1...4..3.3..5..7.74....1.......25....1...6....35.......2....47.9..7..2.4..3...1.");
    Solution solution = new Solution(quest);
    solution.setLevel(Level.SEHR_SCHWER);
    solution.setUnique(true);
    String solutionString = solution.toString();
    LOG.debug(solutionString);
    assertTrue(solutionString.contains("Rätsel"));
    assertTrue(solutionString.contains("Lösung"));
    assertTrue(solutionString.contains("Eindeutigkeit"));
    assertTrue(solutionString.contains("Schwierigkeitsgrad"));
  }

}
