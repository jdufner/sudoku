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

// $Id$
package de.jdufner.sudoku.common.board;

import java.util.List;

/**
 * A block represents a unit of m x n cells. It spans about several columns and
 * rown and contains each literal one time.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * @see <a
 *      href="http://sudopedia.org/wiki/Box">http://sudopedia.org/wiki/Box</a>
 */
public final class Box extends House {

  /**
   * @param sudoku
   *          Die Größe des Sudokus.
   * @param index
   *          Der Index des Blocks.
   * @param cells
   *          Eine Liste aller Zellen, die in diesem Block enthalten sind.
   */
  public Box(final SudokuSize sudokuSize, final int index, final List<Cell> cells) {
    super(sudokuSize, index, cells);
  }

  /**
   * @return
   */
  public int[] getColumnIndexes() {
    final int startIndex = (index % sudokuSize.getBoxHeight()) + sudokuSize.getBoxWidth();
    int[] columnIndexes = new int[sudokuSize.getBoxWidth()];
    for (int i = 0; i < sudokuSize.getBoxWidth(); i++) {
      columnIndexes[i] = startIndex + i;
    }
    return columnIndexes;
  }

  /**
   * @return
   */
  public int[] getRowIndexes() {
    final int startIndex = (index / sudokuSize.getBoxHeight()) * sudokuSize.getBoxHeight();
    int[] rowIndexes = new int[sudokuSize.getBoxHeight()];
    for (int i = 0; i < sudokuSize.getBoxHeight(); i++) {
      rowIndexes[i] = startIndex + i;
    }
    return rowIndexes;
  }

  @Override
  public boolean equals(final Object other) { // NOPMD by Jürgen on 16.11.09
    // 00:23
    if (other instanceof Box) {
      return super.equals(other);
    }
    return false;
  }

  @Override
  public String toString() {
    return "Block " + super.toString();
  }

}
