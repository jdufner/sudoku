// $Id$

/*
 * Gudoku (http://sourceforge.net/projects/gudoku)
 * Sudoku-Implementierung auf Basis des Google Webtoolkit
 * (http://code.google.com/webtoolkit/). Die Lösungsalgorithmen in Java laufen
 * parallel. Die Sudoku-Rätsel werden mittels JDBC in einer Datenbank
 * gespeichert.
 *
 * Copyright (C) 2008 Jürgen Dufner
 *
 * Dieses Programm ist freie Software. Sie können es unter den Bedingungen der
 * GNU General Public License, wie von der Free Software Foundation
 * veröffentlicht, weitergeben und/oder modifizieren, entweder gemäß Version 3
 * der Lizenz oder (nach Ihrer Option) jeder späteren Version.
 *
 * Die Veröffentlichung dieses Programms erfolgt in der Hoffnung, daß es Ihnen
 * von Nutzen sein wird, aber OHNE IRGENDEINE GARANTIE, sogar ohne die
 * implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FÜR EINEN
 * BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
 *
 * Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem
 * Programm erhalten haben. Falls nicht, siehe <http://www.gnu.org/licenses/>.
 *
 */
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
