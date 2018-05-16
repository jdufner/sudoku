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

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-01-15
 * @version $Revision$
 */
public final class XsudokuUtils {

  private XsudokuUtils() {
  }

  public static MainDiagonal buildMainDiagonal(final Grid sudoku) {
    final List<Cell> cells = new ArrayList<Cell>();
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      cells.add(sudoku.getCell(i, sudoku.getSize().getHouseSize() - 1 - i));
    }
    return new MainDiagonal(sudoku.getSize(), 0, cells);
  }

  public static SecondaryDiagonal buildSecondaryDiagonal(final Grid sudoku) {
    final List<Cell> cells = new ArrayList<Cell>();
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      cells.add(sudoku.getCell(i, i));
    }
    return new SecondaryDiagonal(sudoku.getSize(), 0, cells);
  }

  public static boolean isXsudoku(final Grid sudoku) {
    final MainDiagonal mainDiagonal = buildMainDiagonal(sudoku);
    if (!mainDiagonal.isSolved() || !mainDiagonal.isValid()) {
      return false;
    }
    final SecondaryDiagonal secondaryDiagonal = buildSecondaryDiagonal(sudoku);
    if (!secondaryDiagonal.isSolved() || !secondaryDiagonal.isValid()) {
      return false;
    }
    return true;
  }
}
