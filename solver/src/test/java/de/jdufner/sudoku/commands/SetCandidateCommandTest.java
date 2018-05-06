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

import de.jdufner.sudoku.commands.SetCandidateCommand.SetCandidateCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

public final class SetCandidateCommandTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(SetCandidateCommandTest.class);

  private Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.ING_DIBA);

  protected void setUp() throws Exception {
    LOG.debug(sudoku);
  }

  public void testSetCandidateCommand() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Command scc = new SetCandidateCommandBuilder(null, 0, 2, Literal.getInstance(2)).build();
    LOG.debug(scc.getFrozenString());
    assertEquals(null, scc.getFrozenString());
    scc.execute(sudoku);
    assertFalse(scc.isSuccessfully());
    LOG.debug(scc.getFrozenString());
    assertEquals("null: Setze Kandidat 2 in Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8, 9]", scc.getFrozenString());

    assertFalse(cell.isFixed());
    assertTrue(cell.getCandidates().contains(Literal.getInstance(2)));
    assertEquals(9, cell.getCandidates().size());
  }

}
