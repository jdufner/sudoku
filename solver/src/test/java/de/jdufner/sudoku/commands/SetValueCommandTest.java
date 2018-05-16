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
package de.jdufner.sudoku.commands;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.SetValueCommand.SetValueCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

public class SetValueCommandTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(SetValueCommandTest.class);

  private Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.ING_DIBA);

  protected void setUp() throws Exception {
    LOG.debug(sudoku);
  }

  public void testSetValueCommand() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Command scc1 = new SetValueCommandBuilder(null, 0, 2, 2).build();
    LOG.debug(scc1.getFrozenString());
    assertNull(scc1.getFrozenString());
    scc1.execute(sudoku);
    assertTrue(scc1.isSuccessfully());
    LOG.debug(scc1.getFrozenString());
    LOG.debug(cell);
    assertEquals("null: Setze Wert 2 in Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8, 9]", scc1.getFrozenString());

    assertTrue(cell.isFixed());
    assertEquals(0, cell.getCandidates().size());

    Command scc2 = new SetValueCommandBuilder(null, cell, Literal.getInstance(6)).build();
    LOG.debug(scc2.getFrozenString());
    assertNull(scc2.getFrozenString());
    scc2.execute(sudoku);
    assertFalse(scc2.isSuccessfully());
    LOG.debug(scc2.getFrozenString());
    LOG.debug(cell);
    assertEquals("null: Setze Wert 6 in Zelle 2 (0, 2, 0) []", scc2.getFrozenString());

    assertTrue(cell.isFixed());
    assertEquals(0, cell.getCandidates().size());
  }

}
