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

import java.util.Map;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public interface Builder {

  /**
   * Bevor der {@link Builder} mittels {@link #build()} oder {@link #buildSudokus()} ausgeführt wird, muss die
   * gewünschte Größe gesetzt werden.
   * 
   * @param sudokuSize
   */
  public void setSize(SudokuSize sudokuSize);

  /**
   * @return Gibt das letzte und damit das schwierigste {@link Grid} zurück.
   */
  public Grid build();

  /**
   * Gibt das letzte jeweils zu einem Schwierigkeitsgrad ({@link Level}) gefundene {@link Grid} zurück. Wird zu einem
   * {@link Level} kein Sudoku gefunden, wird dafür eben auch keins zurückgegeben, mit anderen Worten es werden also
   * maximal soviele {@link Grid} zurückgegeben, wie es {@link Level} gibt.
   * 
   * @return Eine Map, bestehend aus dem {@link Level} und dem letzen zu diesem {@link Level} gefundenen
   *         {@link Solution} inkl. {@link Grid}.
   */
  public Map<Level, Solution> buildSudokus();

}
