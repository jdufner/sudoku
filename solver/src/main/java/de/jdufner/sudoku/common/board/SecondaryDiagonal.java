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
 * Die Nebendiagonale läuft von unten links nach oben rechts.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-01-15
 * 
 */
public final class SecondaryDiagonal extends House {

  public SecondaryDiagonal(final SudokuSize sudokuSize, final int index, final List<Cell> cells) {
    super(sudokuSize, index, cells);
  }

  @Override
  public boolean equals(Object other) { // NOPMD Jürgen Dufner 13.03.2010
    if (other instanceof SecondaryDiagonal) {
      return super.equals(other);
    }
    return false;
  }

  @Override
  public String toString() {
    return "SecondaryDiagonal " + super.toString();
  }

}
