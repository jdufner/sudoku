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

import java.util.List;

/**
 * A column represents a unit of cells in one column.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 * @see <a
 *      href="http://sudopedia.org/wiki/Column">http://sudopedia.org/wiki/Column</a>
 */
public final class Column extends Line {

  public Column(final SudokuSize sudokuSize, final int index, final List<Cell> cells) {
    super(sudokuSize, index, cells);
  }

  /**
   * @return
   */
  public int[] getBlockIndexes() {
    final int startIndex = (index / sudokuSize.getBoxWidth()) * sudokuSize.getBoxWidth();
    int[] blockIndexes = new int[sudokuSize.getBoxWidth()];
    for (int i = 0; i < blockIndexes.length; i++) {
      blockIndexes[i] = startIndex + i;
    }
    return blockIndexes;
  }

  @Override
  public boolean equals(final Object other) { // NOPMD by Jürgen on 16.11.09
                                              // 00:23
    if (other instanceof Column) {
      return super.equals(other);
    }
    return false;
  }

  @Override
  public String toString() {
    return "Column " + super.toString();
  }

}
