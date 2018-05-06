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
package de.jdufner.sudoku.common.factory;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SudokuFactoryTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(SudokuFactoryTest.class);

  public void testBuildFilledNeun() {
    Grid sudoku = SudokuFactory.INSTANCE.buildFilled(SudokuSize.NEUN);
    LOG.debug(sudoku.toLongString());
    LOG.debug(sudoku.toShortString());
    assertTrue(sudoku.isValid());
    assertTrue(sudoku.isSolvedByCheckSum());
    assertEquals(1, sudoku.getCell(0, 0).getValue().getValue());
    assertEquals(4, sudoku.getCell(0, 3).getValue().getValue());
    assertEquals(7, sudoku.getCell(0, 6).getValue().getValue());
    assertEquals(9, sudoku.getCell(3, 0).getValue().getValue());
    assertEquals(3, sudoku.getCell(3, 3).getValue().getValue());
    assertEquals(6, sudoku.getCell(3, 6).getValue().getValue());
    assertEquals(8, sudoku.getCell(6, 0).getValue().getValue());
    assertEquals(2, sudoku.getCell(6, 3).getValue().getValue());
    assertEquals(5, sudoku.getCell(6, 6).getValue().getValue());
  }

  public void testBuildFilledZehn() {
    Grid sudoku = SudokuFactory.INSTANCE.buildFilled(SudokuSize.ZEHN);
    LOG.debug(sudoku.toLongString());
    LOG.debug(sudoku.toShortString());
    assertTrue(sudoku.isValid());
    assertTrue(sudoku.isSolvedByCheckSum());
    assertEquals(1, sudoku.getCell(0, 0).getValue().getValue());
    assertEquals(3, sudoku.getCell(0, 2).getValue().getValue());
    assertEquals(5, sudoku.getCell(0, 4).getValue().getValue());
    assertEquals(7, sudoku.getCell(0, 6).getValue().getValue());
    assertEquals(9, sudoku.getCell(0, 8).getValue().getValue());
    assertEquals(10, sudoku.getCell(5, 0).getValue().getValue());
    assertEquals(2, sudoku.getCell(5, 2).getValue().getValue());
    assertEquals(4, sudoku.getCell(5, 4).getValue().getValue());
    assertEquals(6, sudoku.getCell(5, 6).getValue().getValue());
    assertEquals(8, sudoku.getCell(5, 8).getValue().getValue());
  }

  public void testBuildShuffled() {
    Grid sudoku = SudokuFactory.INSTANCE.buildShuffled(SudokuSize.NEUN);
    LOG.debug(sudoku);
  }

  public void testSudokuStringWithCandidates() {
    final Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.ING_DIBA);
    final String sudokuAsStringWithCandidates = sudoku.toShortStringWithCandidates();
    LOG.debug(sudokuAsStringWithCandidates);
    final Grid sudoku2 = SudokuFactory.INSTANCE.buildSudoku(sudokuAsStringWithCandidates);
    assertEquals(sudoku, sudoku2);
    assertNotSame(sudoku, sudoku2);
  }
}
