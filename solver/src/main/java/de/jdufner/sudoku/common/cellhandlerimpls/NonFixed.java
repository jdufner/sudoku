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

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public final class NonFixed implements CellHandler {

  private static final Logger LOG = Logger.getLogger(NonFixed.class);

  private transient Collection<Cell> nonFixedCells = null;

  @Override
  public void initialize() {
    nonFixedCells = new ArrayList<Cell>();
  }

  @Override
  public void handleCell(final Cell cell) {
    if (!cell.isFixed()) {
      nonFixedCells.add(cell);
    }
  }

  public Collection<Cell> getNonFixedCells() {
    if (LOG.isDebugEnabled()) {
      LOG.debug(nonFixedCells.size() + " nicht gesetzte Zellen gefunden.");
    }
    return nonFixedCells;
  }

}
