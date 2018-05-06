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

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SudokuTest extends AbstractSolverTestCase {
  private static final Logger LOG = Logger.getLogger(SudokuTest.class);

  public SudokuTest(String name) {
    super(name);
  }

  public void testReadSudoku4() {
    final String mySudoku = "4:" + //
        "1,2,3,4," + // 4
        "3,4,1,2," + // 4
        "4,1,0,0," + // 2
        "0,0,0,0"; // 0
    final int myFixed = 10;

    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(mySudoku);
    assertEquals(1, sudoku.getCell(0, 0).getValue().getValue());
    assertEquals(0, sudoku.getCell(0, 0).getCandidates().size());
    assertEquals(myFixed, sudoku.getNumberOfFixed());
    LOG.debug(sudoku.getNumberOfCandidates());
    assertEquals((sudoku.getSize().getTotalSize() - myFixed) * sudoku.getSize().getHouseSize(), sudoku
        .getNumberOfCandidates());
    assertTrue(sudoku.isValid());
    assertEquals(false, sudoku.isSolved());
    assertEquals(false, sudoku.isSolvedByCheckSum());
  }

  public void testReadSudoku9() {
    final String mySudoku = "9:" + //
        "3,2,7,4,9,6,0,0,1," + // 7
        "0,6,8,0,7,1,4,0,0," + // 5
        "0,1,0,0,0,5,0,6,0," + // 3
        "7,9,0,5,0,0,8,3,4," + // 6
        "0,0,2,0,1,3,0,0,0," + // 3
        "0,0,5,0,4,0,1,0,0," + // 3
        "2,7,4,0,5,0,9,1,3," + // 7
        "9,0,0,0,2,0,6,7,8," + // 5
        "1,0,0,0,0,7,2,0,5"; // 4
    final int myFixed = 43;

    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(mySudoku);

    Cell cell = sudoku.getCell(0, 0);
    assertEquals(0, cell.getRowIndex());
    assertEquals(0, cell.getColumnIndex());
    assertEquals(0, cell.getBlockIndex());
    assertEquals(3, cell.getValue().getValue());
    assertEquals(0, cell.getCandidates().size());

    cell = sudoku.getCell(3, 4);
    assertEquals(3, cell.getRowIndex());
    assertEquals(4, cell.getColumnIndex());
    assertEquals(4, cell.getBlockIndex());
    assertEquals(0, cell.getValue().getValue());
    assertEquals(9, cell.getCandidates().size());

    assertEquals(myFixed, sudoku.getNumberOfFixed());
    LOG.debug(sudoku.getNumberOfCandidates());
    assertEquals((sudoku.getSize().getTotalSize() - myFixed) * sudoku.getSize().getHouseSize(), sudoku
        .getNumberOfCandidates());
    assertTrue(sudoku.isValid());
    assertEquals(false, sudoku.isSolved());
    assertEquals(false, sudoku.isSolvedByCheckSum());

    LOG.debug(sudoku.toString());
  }

  public void testReadSudokuEmpty() {
    Grid sudoku = SudokuFactory.INSTANCE.buildEmpty(SudokuSize.NEUN);
    final int myFixed = 0;

    assertEquals(myFixed, sudoku.getNumberOfFixed());
    LOG.debug(sudoku.getNumberOfCandidates());
    assertEquals((sudoku.getSize().getTotalSize() - myFixed) * sudoku.getSize().getHouseSize(), sudoku
        .getNumberOfCandidates());
    assertTrue(sudoku.isValid());
    assertEquals(false, sudoku.isSolved());
    assertEquals(false, sudoku.isSolvedByCheckSum());

    LOG.debug(sudoku.toString());
  }

  public void testReadSudoku9Filled() {
    Grid sudoku = SudokuFactory.INSTANCE.buildFilled(SudokuSize.NEUN);
    final int myFixed = 81;

    assertEquals(myFixed, sudoku.getNumberOfFixed());
    LOG.debug(sudoku.getNumberOfCandidates());
    assertEquals((sudoku.getSize().getTotalSize() - myFixed) * sudoku.getSize().getHouseSize(), sudoku
        .getNumberOfCandidates());
    assertTrue(sudoku.isValid());
    assertEquals(true, sudoku.isSolved());
    assertEquals(true, sudoku.isSolvedByCheckSum());

    LOG.debug(sudoku.toString());
  }

  public void testClone() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    Grid kopie = new Grid(original);
    assertNotSame(original, kopie);
    assertNotSame(original.getCell(0, 0), kopie.getCell(0, 0));
    assertEquals(original.getCell(0, 0).getValue(), kopie.getCell(0, 0).getValue());
    assertSame(original.getCell(0, 0).getValue(), kopie.getCell(0, 0).getValue());
    if (original.getCell(0, 0).isFixed()) {
      assertEquals(original.getCell(0, 0).getCandidates(), kopie.getCell(0, 0).getCandidates());
    } else {
      assertNotSame(original.getCell(0, 0).getCandidates(), kopie.getCell(0, 0).getCandidates());
    }
    assertEquals(original.getCell(1, 0), kopie.getCell(1, 0));
    assertNotSame(original.getCell(1, 0), kopie.getCell(1, 0));
    assertEquals(original.getCell(1, 0).getValue(), kopie.getCell(1, 0).getValue());
    assertSame(original.getCell(1, 0).getValue(), kopie.getCell(1, 0).getValue());
    if (original.getCell(1, 0).isFixed()) {
      assertSame(original.getCell(1, 0).getCandidates(), kopie.getCell(1, 0).getCandidates());
    } else {
      assertNotSame(original.getCell(1, 0).getCandidates(), kopie.getCell(1, 0).getCandidates());
    }
  }

}
