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


package de.jdufner.sudoku.common.cellhandlerimpls;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.Literal;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public final class CellByValue implements CellHandler {

  private final transient Literal literal;
  private final transient Collection<Cell> cells = new ArrayList<Cell>();

  public CellByValue(final Literal literal) {
    this.literal = literal;
  }

  @Override
  public void handleCell(final Cell cell) {
    if (cell.getValue().equals(literal)) {
      cells.add(cell);
    }
  }

  @Override
  public void initialize() {
    // Nothing to do
  }

  public Collection<Cell> getCells() {
    return cells;
  }

}
