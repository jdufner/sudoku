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


package de.jdufner.sudoku.common;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Box;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Column;
import de.jdufner.sudoku.common.board.Row;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public final class Sudoku10x10Test extends AbstractSolverTestCase {
  private static final Logger LOG = Logger.getLogger(Sudoku10x10Test.class);

  public static final String EMPTY = "10:" + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0";

  public static final String FILLED = "10:" + //
      "1,2,3,4,5,6,7,8,9,10," + //
      "3,4,5,6,7,8,9,10,1,2," + //
      "5,6,7,8,9,10,1,2,3,4," + //
      "7,8,9,10,1,2,3,4,5,6," + //
      "9,10,1,2,3,4,5,6,7,8," + //
      "10,1,2,3,4,5,6,7,8,9," + //
      "2,3,4,5,6,7,8,9,10,1," + //
      "4,5,6,7,8,9,10,1,2,3," + //
      "6,7,8,9,10,1,2,3,4,5," + //
      "8,9,10,1,2,3,4,5,6,7";

  public static final String COMPLETE = "10:" + //
      "1,2,3,4,5,6,7,8,9,10," + //
      "3,4,5,6,7,8,9,10,1,2," + //
      "5,6,7,8,9,10,1,2,3,4," + //
      "7,8,9,10,1,2,3,4,5,6," + //
      "9,10,1,2,3,4,5,6,7,8," + //
      "2,3,4,5,6,7,8,9,10,1," + //
      "4,5,6,7,8,9,10,1,2,3," + //
      "6,7,8,9,10,1,2,3,4,5," + //
      "8,9,10,1,2,3,4,5,6,7," + //
      "10,1,2,3,4,5,6,7,8,9";

  public Sudoku10x10Test(String name) {
    super(name);
  }

  public void testBuildFromString() {
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(COMPLETE);
    LOG.debug(sudoku.toShortString());
    assertTrue(COMPLETE.equals(sudoku.getSize().getHouseSize() + ":" + sudoku.toShortString()));
    assertEquals(sudoku.getSize().getHouseSize(), sudoku.getBlock(0).getCells().size());
    assertEquals(sudoku.getSize().getHouseSize(), sudoku.getColumn(0).getCells().size());
    assertEquals(sudoku.getSize().getHouseSize(), sudoku.getRow(0).getCells().size());
  }

  public void testBuildEmpty() {
    Grid sudoku = SudokuFactory.INSTANCE.buildEmpty(SudokuSize.ZEHN);
    LOG.debug(sudoku.toLongString());
    LOG.debug(sudoku.toShortString());
    assertTrue(EMPTY.equals(sudoku.getSize().getHouseSize() + ":" + sudoku.toShortString()));
  }

  public void testBuildFilled() {
    Grid sudoku = SudokuFactory.INSTANCE.buildFilled(SudokuSize.ZEHN);
    assertTrue(sudoku.isValid());
    assertTrue(sudoku.isSolved());
    assertTrue(sudoku.isSolvedByCheckSum());
    LOG.debug(sudoku.toLongString());
    LOG.debug(sudoku.toShortString());
    assertTrue(FILLED.equals(sudoku.getSize().getHouseSize() + ":" + sudoku.toShortString()));
  }

  public void testCellMetadata() {
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(COMPLETE);
    LOG.debug(sudoku.toShortString());
    int k = 0;
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        Cell cell = sudoku.getCell(i, j);
        assertEquals(i, cell.getRowIndex());
        assertEquals(j, cell.getColumnIndex());
        assertEquals(k, cell.getNumber());
        if (i >= 0 && i <= 4 && j >= 0 && j <= 1) {
          assertEquals(0, cell.getBlockIndex());
        }
        k += 1;
      }
    }
    assertTrue(COMPLETE.equals(sudoku.getSize().getHouseSize() + ":" + sudoku.toShortString()));
  }

  public void testBlockMetadata() {
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(COMPLETE);
    LOG.debug(sudoku.toShortString());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      Box block = sudoku.getBlock(i);
      for (Cell cell : block.getCells()) {
        assertEquals(i, cell.getBlockIndex());
        if (i == 0) {
          assertTrue(containsValue(cell.getBlockIndex(), new int[] { 0, 1, 10, 11, 20, 21, 30, 31, 40, 41 }));
        }
      }
    }
  }

  private boolean containsValue(int value, int[] values) {
    for (int i = 0; i < values.length; i++) {
      if (values[i] == value) {
        return true;
      }
    }
    return false;
  }

  public void testColumnMetadata() {
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(COMPLETE);
    LOG.debug(sudoku.toShortString());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      Column column = sudoku.getColumn(i);
      for (Cell cell : column.getCells()) {
        assertEquals(i, cell.getColumnIndex());
      }
      if (column.getIndex() >= 0 && column.getIndex() <= 1) {
        assertTrue(column.getBlockIndexes().length == sudoku.getSize().getBoxWidth());
        assertEquals(0, column.getBlockIndexes()[0]);
        assertEquals(1, column.getBlockIndexes()[1]);
      }
      if (column.getIndex() >= 2 && column.getIndex() <= 3) {
        assertTrue(column.getBlockIndexes().length == sudoku.getSize().getBoxWidth());
        assertEquals(2, column.getBlockIndexes()[0]);
        assertEquals(3, column.getBlockIndexes()[1]);
      }
      if (column.getIndex() >= 4 && column.getIndex() <= 5) {
        assertTrue(column.getBlockIndexes().length == sudoku.getSize().getBoxWidth());
        assertEquals(4, column.getBlockIndexes()[0]);
        assertEquals(5, column.getBlockIndexes()[1]);
      }
      if (column.getIndex() >= 6 && column.getIndex() <= 7) {
        assertTrue(column.getBlockIndexes().length == sudoku.getSize().getBoxWidth());
        assertEquals(6, column.getBlockIndexes()[0]);
        assertEquals(7, column.getBlockIndexes()[1]);
      }
      if (column.getIndex() >= 8 && column.getIndex() <= 9) {
        assertTrue(column.getBlockIndexes().length == sudoku.getSize().getBoxWidth());
        assertEquals(8, column.getBlockIndexes()[0]);
        assertEquals(9, column.getBlockIndexes()[1]);
      }
    }
  }

  public void testRowMetadata() {
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(COMPLETE);
    LOG.debug(sudoku.toShortString());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      Row row = sudoku.getRow(i);
      for (Cell cell : row.getCells()) {
        assertEquals(i, cell.getRowIndex());
      }
      if (row.getIndex() >= 0 && row.getIndex() <= 4) {
        assertTrue(row.getBlockIndexes().length == sudoku.getSize().getBoxHeight());
        assertEquals(0, row.getBlockIndexes()[0]);
        assertEquals(1, row.getBlockIndexes()[1]);
        assertEquals(2, row.getBlockIndexes()[2]);
        assertEquals(3, row.getBlockIndexes()[3]);
        assertEquals(4, row.getBlockIndexes()[4]);
      }
      if (row.getIndex() >= 5 && row.getIndex() <= 9) {
        assertTrue(row.getBlockIndexes().length == sudoku.getSize().getBoxHeight());
        assertEquals(5, row.getBlockIndexes()[0]);
        assertEquals(6, row.getBlockIndexes()[1]);
        assertEquals(7, row.getBlockIndexes()[2]);
        assertEquals(8, row.getBlockIndexes()[3]);
        assertEquals(9, row.getBlockIndexes()[4]);
      }
    }
  }

}
