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
import de.jdufner.sudoku.common.board.Grid;

/**
 * Dieser {@link CellHandler} setzt alle ungesetzten Felder eines Sudokus zurück
 * und entfernt die Kandidaten, die bereits in gleichem Quadrant, Spalte oder
 * Zeile gesetzt sind. Der {@link CellHandler} setzt nicht das Cell, auch wenn
 * nur ein Kandidat übrig bleibt.
 * 
 * @see Cell#resetCandidates()
 * @see Cell#removeCandidatesAndSetIfOnlyOneRemains(java.util.Collection)
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public final class ResetAndRemoveCandidates implements CellHandler {

  private Grid sudoku;

  public ResetAndRemoveCandidates(Grid sudoku) {
    this.sudoku = sudoku;
  }

  @Override
  public void initialize() {
    // Nothing to do
  }

  // TODO Performance Tuning: Ist es besser 3x removeAll aufzurufen oder nur 1x
  // removeAll mit allen Literals auf einmal
  @Override
  public void handleCell(final Cell cell) {
    if (!cell.isFixed()) {
      cell.resetCandidates();
      cell.getCandidates().removeAll(sudoku.getBlock(cell.getBlockIndex()).getFixedAsLiteral());
      cell.getCandidates().removeAll(sudoku.getColumn(cell.getColumnIndex()).getFixedAsLiteral());
      cell.getCandidates().removeAll(sudoku.getRow(cell.getRowIndex()).getFixedAsLiteral());
    }
  }

}
