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


package de.jdufner.sudoku.common.board;

import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 21.12.2009
 * 
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
