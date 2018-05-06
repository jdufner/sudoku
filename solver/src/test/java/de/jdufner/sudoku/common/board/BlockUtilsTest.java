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
package de.jdufner.sudoku.common.board;

import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 21.12.2009
 * @version $Revision$
 */
public final class BlockUtilsTest extends AbstractSolverTestCase {

  public void testIsFirstColumnInBlock() {
    assertTrue(BoxUtils.isFirstColumnInBlock(0, SudokuSize.NEUN));
    assertFalse(BoxUtils.isFirstColumnInBlock(1, SudokuSize.NEUN));
    assertFalse(BoxUtils.isFirstColumnInBlock(2, SudokuSize.NEUN));
    assertTrue(BoxUtils.isFirstColumnInBlock(3, SudokuSize.NEUN));
    assertFalse(BoxUtils.isFirstColumnInBlock(4, SudokuSize.NEUN));
    assertFalse(BoxUtils.isFirstColumnInBlock(5, SudokuSize.NEUN));
    assertTrue(BoxUtils.isFirstColumnInBlock(6, SudokuSize.NEUN));
    assertFalse(BoxUtils.isFirstColumnInBlock(7, SudokuSize.NEUN));
    assertFalse(BoxUtils.isFirstColumnInBlock(8, SudokuSize.NEUN));
  }

  public void testIsFirstRowInBlock() {
    assertTrue(BoxUtils.isFirstRowInBlock(0, SudokuSize.NEUN));
    assertFalse(BoxUtils.isFirstRowInBlock(1, SudokuSize.NEUN));
    assertFalse(BoxUtils.isFirstRowInBlock(2, SudokuSize.NEUN));
    assertTrue(BoxUtils.isFirstRowInBlock(3, SudokuSize.NEUN));
    assertFalse(BoxUtils.isFirstRowInBlock(4, SudokuSize.NEUN));
    assertFalse(BoxUtils.isFirstRowInBlock(5, SudokuSize.NEUN));
    assertTrue(BoxUtils.isFirstRowInBlock(6, SudokuSize.NEUN));
    assertFalse(BoxUtils.isFirstRowInBlock(7, SudokuSize.NEUN));
    assertFalse(BoxUtils.isFirstRowInBlock(8, SudokuSize.NEUN));
  }

  public void testIsLastColumnInRow() {
    assertFalse(BoxUtils.isLastColumnInRow(0, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastColumnInRow(1, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastColumnInRow(2, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastColumnInRow(3, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastColumnInRow(4, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastColumnInRow(5, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastColumnInRow(6, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastColumnInRow(7, SudokuSize.NEUN));
    assertTrue(BoxUtils.isLastColumnInRow(8, SudokuSize.NEUN));
  }

  public void testIsLastRowInColumn() {
    assertFalse(BoxUtils.isLastRowInColumn(0, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastRowInColumn(1, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastRowInColumn(2, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastRowInColumn(3, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastRowInColumn(4, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastRowInColumn(5, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastRowInColumn(6, SudokuSize.NEUN));
    assertFalse(BoxUtils.isLastRowInColumn(7, SudokuSize.NEUN));
    assertTrue(BoxUtils.isLastRowInColumn(8, SudokuSize.NEUN));
  }

}
