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
import de.jdufner.sudoku.common.board.SudokuSize;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class ShortString implements CellHandler {

  private final transient SudokuSize groesse;
  private transient StringBuilder stringBuilder = null;

  public ShortString(final Grid sudoku) {
    groesse = sudoku.getSize();
  }

  @Override
  public void initialize() {
    stringBuilder = new StringBuilder();
  }

  @Override
  public void handleCell(final Cell cell) {
    if (groesse.equals(SudokuSize.DEFAULT)) {
      if (cell.getValue().getValue() == 0) {
        stringBuilder.append(".");
      } else {
        stringBuilder.append(cell.getValue().getValue());
      }
    } else {
      stringBuilder.append(cell.getValue().getValue());
      if (!(cell.getRowIndex() >= groesse.getHouseSize() - 1 && cell.getColumnIndex() >= groesse.getHouseSize() - 1)) {
        stringBuilder.append(',');
      }
    }
  }

  @Override
  public String toString() {
    return stringBuilder.toString();
  }

}
