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

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.service.ExtendedSolver;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractBuilder implements Builder {

  protected Grid sudoku;
  protected ExtendedSolver strategySolverWithBacktracking;

  public AbstractBuilder() {
  }

  public void setSize(SudokuSize sudokuSize) {
    Grid underDeterminedSudoku = SudokuFactory.INSTANCE.buildShuffled(sudokuSize);
    sudoku = getStrategySolverWithBacktracking().solve(underDeterminedSudoku);
  }

  //
  // Spring Wiring
  //

  public ExtendedSolver getStrategySolverWithBacktracking() {
    return strategySolverWithBacktracking;
  }

  public void setStrategySolverWithBacktracking(ExtendedSolver strategySolverWithBacktracking) {
    this.strategySolverWithBacktracking = strategySolverWithBacktracking;
  }

}
