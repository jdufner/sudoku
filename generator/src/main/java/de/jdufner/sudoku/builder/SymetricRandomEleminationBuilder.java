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


package de.jdufner.sudoku.builder;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * 
 */
public final class SymetricRandomEleminationBuilder extends EleminationBuilder {

  private static final Logger LOG = Logger.getLogger(SymetricRandomEleminationBuilder.class);

  public SymetricRandomEleminationBuilder() {
    super();
  }

  public Cell symetricCell(Cell cell) {
    return sudoku.getCell(//
        sudoku.getSize().getHouseSize() - 1 - cell.getRowIndex(), //
        sudoku.getSize().getHouseSize() - 1 - cell.getColumnIndex());
  }

  @Override
  protected void executeBuilder() {
    int cellCounter = 0;
    int eleminatedCellCounter = 0;
    for (Cell cell : buildRandomPermutationOfFixedOfFirstHalf()) {
      Cell cellCandidate1 = new Cell(cell);
      Cell cellCandidate2 = new Cell(symetricCell(cell));
      if (LOG.isDebugEnabled()) {
        LOG.debug("Leere Zellen " + cell + " und " + symetricCell(cell));
      }
      sudoku.getCell(cellCandidate1.getRowIndex(), cellCandidate1.getColumnIndex()).reset();
      cellCounter++;
      if (!cellCandidate1.equals(cellCandidate2)) {
        sudoku.getCell(cellCandidate2.getRowIndex(), cellCandidate2.getColumnIndex()).reset();
        cellCounter++;
      }
      Solution solution = strategySolverWithBacktracking.getSolution(sudoku);
      if (solution.isUnique()) {
        level2SudokuMap.put(solution.getLevel(), solution);
        eleminatedCellCounter++;
        if (!cellCandidate1.equals(cellCandidate2)) {
          eleminatedCellCounter++;
        }
        if (LOG.isInfoEnabled()) {
          LOG.info("(" + eleminatedCellCounter + "/" + cellCounter + ") Zellen " + cellCandidate1 + " und "
              + cellCandidate2 + " erfolgreich geleert. Neues Sudoku: " + sudoku.toShortString());
        }
      } else {
        sudoku.getCell(cellCandidate1.getRowIndex(), cellCandidate1.getColumnIndex()).setValue(
            cellCandidate1.getValue());
        if (!cellCandidate1.equals(cellCandidate2)) {
          sudoku.getCell(cellCandidate2.getRowIndex(), cellCandidate2.getColumnIndex()).setValue(
              cellCandidate2.getValue());
        }
        if (LOG.isInfoEnabled()) {
          LOG.info("(" + eleminatedCellCounter + "/" + cellCounter + ") Zelle " + cellCandidate1 + " und "
              + cellCandidate2 + " konnten nicht geleert werden, setze deshalb zurück und mache weiter.");
        }
      }
    }
  }

}
