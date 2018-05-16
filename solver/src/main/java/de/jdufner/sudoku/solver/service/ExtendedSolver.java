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
package de.jdufner.sudoku.solver.service;

import de.jdufner.sudoku.common.board.Grid;

/**
 * Erweiterter Solver, speziell für eine intelligente Lösung, keine Brute Force-Methode.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public interface ExtendedSolver extends Solver {

  /**
   * Returns a {@link Cell} of the solved Sudoku, which is set in the given Sudoku.
   * 
   * @param sudoku
   * @return A Cell of the solved Sudoku. TODO Use interceptor? In Backtracking I'll use a complete solution, in
   *         Strategy, one new solved Cell is enough.
   */
  // Cell getHint(Sudoku sudoku);

  /**
   * Liefert ein {@link Solution} zu einem Sudoku zurück.
   * 
   * @param sudoku
   * @return
   */
  Solution getSolution(Grid sudoku);

}
