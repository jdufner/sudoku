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
package de.jdufner.sudoku.common.cellhandlerimpls;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.Grid;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class LongString implements CellHandler {

  private static String lineSeparator = System.getProperty("line.separator");

  private final transient Grid sudoku;
  private final transient StringBuilder stringBuilder = new StringBuilder();
  private transient int countValues = 0;
  private transient int possibleValues = 0;

  public LongString(final Grid sudoku) {
    this.sudoku = sudoku;
  }

  @Override
  public void initialize() {
    // Nothing to do
  }

  @Override
  public void handleCell(final Cell cell) {
    if (cell.isFixed()) {
      countValues++;
    } else {
      possibleValues += cell.getCandidates().size();
    }
    stringBuilder.append(cell.toString());
    stringBuilder.append(lineSeparator);
  }

  @Override
  public String toString() {
    stringBuilder.append(countValues + " values found.").append(lineSeparator);
    stringBuilder.append(possibleValues + " values open.").append(lineSeparator);
    stringBuilder.append("Level: ").append(sudoku.getLevel()).append(lineSeparator);
    stringBuilder.append("Size: ").append(sudoku.getSize()).append(lineSeparator);
    if (sudoku.isSolved()) {
      stringBuilder.append("Sudoku solved!");
    }
    return stringBuilder.toString();
  }

}
