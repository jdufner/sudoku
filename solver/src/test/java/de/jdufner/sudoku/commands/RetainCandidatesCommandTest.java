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

import de.jdufner.sudoku.commands.RetainCandidatesCommand.RetainCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

public final class RetainCandidatesCommandTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(RetainCandidatesCommandTest.class);

  private Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.ING_DIBA);

  public void setUp() {
    LOG.debug(sudoku);
  }

  public void testRetainCandidatesCommand() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    final Command rcc1 = new RetainCandidatesCommandBuilder(null, cell).addCandidate(2, 6).build();
    assertNull(rcc1.getFrozenString());
    rcc1.execute(sudoku);
    assertTrue(rcc1.isSuccessfully());
    assertTrue(cell.isValid());
    LOG.debug(rcc1.getFrozenString());
    assertEquals("null: Behalte Kandidaten [2, 6] in Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8, 9]", rcc1
        .getFrozenString());

    assertEquals(2, cell.getCandidates().size());
    assertTrue(cell.getCandidates().contains(Literal.getInstance(2)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(6)));
    assertFalse(cell.isFixed());
  }

  public void testRetainNone() {
    try {
      Cell cell = sudoku.getCell(0, 2);

      assertEquals(9, cell.getCandidates().size());

      Command rcc1 = new RetainCandidatesCommandBuilder(null, cell).build();
      assertNull(rcc1.getFrozenString());
      rcc1.execute(sudoku);
      assertTrue(rcc1.isSuccessfully());
      assertFalse(cell.isValid());
      LOG.debug(rcc1.getFrozenString());
      assertEquals("null: Behalte Kandidaten [] in Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8, 9]", rcc1
          .getFrozenString());

      assertEquals(0, cell.getCandidates().size());
      assertFalse(cell.isFixed());
      fail("IllegalStateException erwartet.");
    } catch (IllegalStateException ise) {
      LOG.debug(ise.getLocalizedMessage());
    }
  }

  public void testRetainAlle() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Command rcc1 = new RetainCandidatesCommandBuilder(null, cell).addCandidate(1, 2, 3, 4, 5, 6, 7, 8, 9).build();
    assertNull(rcc1.getFrozenString());
    rcc1.execute(sudoku);
    assertFalse(rcc1.isSuccessfully());
    assertTrue(cell.isValid());
    LOG.debug(rcc1.getFrozenString());
    assertEquals(
        "null: Behalte Kandidaten [1, 2, 3, 4, 5, 6, 7, 8, 9] in Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8, 9]", rcc1
            .getFrozenString());

    assertEquals(9, cell.getCandidates().size());
    assertTrue(cell.getCandidates().contains(Literal.getInstance(2)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(6)));
    assertFalse(cell.isFixed());
  }

  public void testRetainDuplicate() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Command rcc1 = new RetainCandidatesCommandBuilder(null, cell).addCandidate(2, 6).build();
    rcc1.execute(sudoku);
    assertTrue(rcc1.isSuccessfully());

    Command rcc2 = new RetainCandidatesCommandBuilder(null, cell).addCandidate(2, 6).build();
    rcc2.execute(sudoku);
    assertFalse(rcc2.isSuccessfully());

  }
}
