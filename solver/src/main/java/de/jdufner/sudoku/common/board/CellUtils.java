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
 * @since 2010-03-10
 * 
 * @see Cell
 */
public final class CellUtils {

  private CellUtils() {
  }

  public static int getRowIndex(final int number, final SudokuSize sudokuSize) {
    return number / sudokuSize.getHouseSize();
  }

  public static int getColumnIndex(final int number, final SudokuSize sudokuSize) {
    return number % sudokuSize.getHouseSize();
  }

  public static int getNumber(final int rowIndex, final int columnIndex, final SudokuSize sudokuSize) {
    return rowIndex * sudokuSize.getHouseSize() + columnIndex;
  }
}
