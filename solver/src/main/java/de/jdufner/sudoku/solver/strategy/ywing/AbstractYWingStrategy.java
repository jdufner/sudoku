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


package de.jdufner.sudoku.solver.strategy.ywing;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.House;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-02-21
 * @version $Revision$
 * 
 */
public abstract class AbstractYWingStrategy extends AbstractStrategy {

  public AbstractYWingStrategy(final Grid sudoku) {
    super(sudoku);
  }

  private Collection<Cell> findCellsByCandidates(final Collection<Literal> searchCandidates, final House unit) {
    final Collection<Cell> foundCells = new ArrayList<Cell>();
    for (Cell cell : unit.getNonFixed(searchCandidates.size())) {
      if (cell.getCandidates().containsAll(searchCandidates)) {
        foundCells.add(cell);
      }
    }
    return foundCells;
  }

  protected Collection<Cell> findCellsByCandidatesInBlock(final Collection<Literal> searchCandidates,
      final int blockIndex) {
    return findCellsByCandidates(searchCandidates, getSudoku().getBlock(blockIndex));
  }

  protected Collection<Cell> findCellsByCandidatesInColumn(final Collection<Literal> searchCandidates,
      final int columnIndex) {
    return findCellsByCandidates(searchCandidates, getSudoku().getColumn(columnIndex));
  }

  protected Collection<Cell> findCellsByCandidatesInRow(final Collection<Literal> searchCandidates, final int rowIndex) {
    return findCellsByCandidates(searchCandidates, getSudoku().getRow(rowIndex));
  }

  protected Literal getCommonCandidate(final Cell firstCell, final Cell secondCell) {
    if (firstCell.getCandidates().containsAll(secondCell.getCandidates())) {
      // Wenn die Kandidatenpaare gleich sind
      return null;
    }
    for (Literal candidate : firstCell.getCandidates()) {
      if (secondCell.getCandidates().contains(candidate)) {
        return candidate;
      }
    }
    return null;
  }

  protected Literal getDifferentCandidate(final Cell cell, final Literal commonCandidate) {
    for (Literal candidate : cell.getCandidates()) {
      if (!candidate.equals(commonCandidate)) {
        return candidate;
      }
    }
    return null;
  }

}
