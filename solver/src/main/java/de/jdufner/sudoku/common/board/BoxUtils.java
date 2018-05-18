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

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 * @see Box
 */
public final class BoxUtils {

  private BoxUtils() {
  }

  public static int[] getColumnsByBlock(final int block, final SudokuSize sudokuSize) {
    int[] columns = new int[sudokuSize.getBoxWidth()];
    for (int i = 0; i < sudokuSize.getBoxWidth(); i++) {
      columns[i] = block * sudokuSize.getBoxWidth() + i;
    }
    return columns;
  }

  /**
   * Liefert den Blockindex zu einer Zelle (genauer Zeilen- und Spaltenindex) in
   * Abhängigkeit der Größe zurück.
   * 
   * @param rowIndex
   * @param columnIndex
   * @param sudokuSize
   * @return
   */
  public static int getBlockIndexByRowIndexAndColumnIndex(final int rowIndex, final int columnIndex,
      final SudokuSize sudokuSize) {
    return (rowIndex / sudokuSize.getBoxHeight()) * sudokuSize.getBoxHeight()
        + (columnIndex / sudokuSize.getBoxWidth());
  }

  public static boolean isFirstColumnInBlock(final int columnIndex, final SudokuSize sudokuSize) {
    if (columnIndex % sudokuSize.getBoxWidth() == 0) {
      return true;
    }
    return false;
  }

  public static boolean isFirstRowInBlock(final int rowIndex, final SudokuSize sudokuSize) {
    if (rowIndex % sudokuSize.getBoxHeight() == 0) {
      return true;
    }
    return false;
  }

  public static boolean isLastColumnInRow(final int columnIndex, final SudokuSize sudokuSize) {
    if (columnIndex == sudokuSize.getHouseSize() - 1) {
      return true;
    }
    return false;
  }

  public static boolean isLastRowInColumn(final int rowIndex, final SudokuSize sudokuSize) {
    if (rowIndex == sudokuSize.getHouseSize() - 1) {
      return true;
    }
    return false;
  }

}
