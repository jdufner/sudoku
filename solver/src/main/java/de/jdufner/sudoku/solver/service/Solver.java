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


package de.jdufner.sudoku.solver.service;

import de.jdufner.sudoku.common.board.Grid;

/**
 * Einfache Service zum Lösen von Sudokus.
 * 
 * Es existieren zwei einfache Implementierungen dazu:
 * <ul>
 * <li>Backtracking-Variante (brute force)</li>
 * <li>Intelligente Variante (aktuell nur eine parallele Implementierung)</li>
 * </ul>
 * 
 * TODO Muss ich diese Liste selbst schreiben oder kann das javadoc selbst erledigen? Eigentlich gehört das ja nicht in
 * diese Klasse.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public interface Solver {

  /**
   * Liefert <code>true</code> zurück, wenn ein Sudoku überhaupt eine Lösung, sonst <code>false</code>. Der
   * Rechenaufwand ist derselbe wie bei {@link #solve(Grid)}
   * 
   * TODO throws InvalidSudokuException
   * 
   * @param sudoku
   * @return <code>true</code> if Sudoku is solvable, else <code>false</code>
   */
  boolean isSolvable(Grid sudoku);

  /**
   * Finds a solution of the Sudoku, if exists. Maybe there are more than one solution.
   * 
   * TODO throws InvalidSudokuException
   * 
   * TODO throws NoSolutionFoundException
   * 
   * @param sudoku
   * @return A solution, if exists.
   */
  Grid solve(Grid sudoku);

  /**
   * Liefert <code>true</code> zurück, wenn genau eine Lösung existiert, sonst <code>false</code>.
   * 
   * TODO throws InvalidSudokuException
   * 
   * TODO throws NoSolutionFoundException
   * 
   * @param sudoku
   * @return <code>true</code>, wenn das Sudoku genau eine Lösung hat, sonst <code>false</code>.
   */
  boolean isUnique(Grid sudoku);

}
