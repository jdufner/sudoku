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
package de.jdufner.sudoku.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.builder.transformation.TransformationUtil;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class EleminationBuilder extends AbstractBuilder {

  private static final Logger LOG = Logger.getLogger(EleminationBuilder.class);
  private static final int NUMBER_TRANSFORMATIONS = 123;

  protected Map<Level, Solution> level2SudokuMap = new HashMap<Level, Solution>();
  private int cellCounter = 0;
  private int eleminatedCellCounter = 0;

  public EleminationBuilder() {
    super();
  }

  protected void shuffleSudoku() {
    if (sudoku == null) {
      setSize(SudokuSize.DEFAULT);
    }
    // TODO Soll das noch gemacht werden?
    sudoku = TransformationUtil.arbitraryTransformation(sudoku, NUMBER_TRANSFORMATIONS);
    if (LOG.isDebugEnabled()) {
      LOG.debug(sudoku.toShortString());
    }
  }

  protected List<Cell> buildRandomPermutationOfFixed() {
    List<Cell> felder = new ArrayList<Cell>();
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        if (sudoku.getCell(i, j).isFixed()) {
          felder.add(sudoku.getCell(i, j));
        }
      }
    }
    Object[] objs = SudokuFactory.INSTANCE.getRandomData().nextSample(felder, felder.size());
    List<Cell> arbitraryPermution = new ArrayList<Cell>();
    for (int i = 0; i < objs.length; i++) {
      arbitraryPermution.add((Cell) objs[i]);
    }
    return arbitraryPermution;
  }

  protected List<Cell> buildRandomPermutationOfFixedOfFirstHalf() {
    List<Cell> felder = new ArrayList<Cell>();
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        if (sudoku.getCell(i, j).isInFirstHalf() && sudoku.getCell(i, j).isFixed()) {
          felder.add(sudoku.getCell(i, j));
        }
      }
    }
    Object[] objs = SudokuFactory.INSTANCE.getRandomData().nextSample(felder, felder.size());
    List<Cell> arbitraryPermution = new ArrayList<Cell>();
    for (int i = 0; i < objs.length; i++) {
      arbitraryPermution.add((Cell) objs[i]);
    }
    return arbitraryPermution;
  }

  protected void eleminateIfPossible(Cell cell) {
    Cell cellCandidate = new Cell(cell);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Leere Zelle " + cell);
    }
    sudoku.getCell(cell.getRowIndex(), cell.getColumnIndex()).reset();
    cellCounter++;
    Solution solution = strategySolverWithBacktracking.getSolution(sudoku);
    if (solution.isUnique()) {
      level2SudokuMap.put(solution.getLevel(), solution);
      eleminatedCellCounter++;
      if (LOG.isInfoEnabled()) {
        LOG.info("(" + eleminatedCellCounter + "/" + cellCounter + ") Zelle " + cellCandidate
            + " erfolgreich geleert. Neues Sudoku: " + sudoku.toShortString());
      }
    } else {
      sudoku.getCell(cellCandidate.getRowIndex(), cellCandidate.getColumnIndex()).setValue(cellCandidate.getValue());
      if (LOG.isInfoEnabled()) {
        LOG.info("(" + eleminatedCellCounter + "/" + cellCounter + ") Zelle " + cellCandidate
            + " konnte nicht geleert werden, setze deshalb zurück und mache weiter.");
      }
    }
  }

  protected abstract void executeBuilder();

  public Grid build() {
    shuffleSudoku();
    executeBuilder();
    if (LOG.isInfoEnabled()) {
      LOG.info("New Sudoku: " + sudoku);
    }
    return sudoku;
  }

  public Map<Level, Solution> buildSudokus() {
    shuffleSudoku();
    executeBuilder();
    return level2SudokuMap;
  }

}
