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

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.SudokuSize;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision: 7 $
 */
public final class NotStarted implements CellHandler {

  // private static final Logger LOG = Logger.getLogger(NotStarted.class);

  private transient final SudokuSize sudokuSize;
  private transient boolean booleanValue = true;

  public NotStarted(final SudokuSize sudokuSize) {
    this.sudokuSize = sudokuSize;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void handleCell(final Cell cell) {
    if (booleanValue && !cell.isFixed() && cell.getCandidates().size() != sudokuSize.getHouseSize()) {
      booleanValue = false;
    }
  }

  public boolean isBooleanValue() {
    return booleanValue;
  }

}
