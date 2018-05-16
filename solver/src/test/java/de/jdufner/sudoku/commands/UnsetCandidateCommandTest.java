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


package de.jdufner.sudoku.commands;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.UnsetCandidateCommand.UnsetCandidateCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

public final class UnsetCandidateCommandTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(UnsetCandidateCommandTest.class);

  private Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.ING_DIBA);

  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testUnsetCandidateCommand() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Command ucc = new UnsetCandidateCommandBuilder(null, 0, 2, Literal.getInstance(1)).build();
    LOG.debug(ucc.getFrozenString());
    assertEquals(null, ucc.getFrozenString());
    ucc.execute(sudoku);
    assertTrue(ucc.isSuccessfully());
    LOG.debug(ucc.getFrozenString());
    assertEquals("null: Entferne Kandidat 1 aus Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8, 9]", ucc.getFrozenString());

    assertFalse(cell.isFixed());
    assertFalse(cell.getCandidates().contains(Literal.getInstance(1)));
    assertEquals(8, cell.getCandidates().size());
  }

}
