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


package de.jdufner.sudoku.common.validator.impl;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.validator.SudokuValidator;

/**
 * Prüft mit einem seriellen Algorithmus ob das Sudoku gültig ist.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public final class SerialSudokuValidator implements SudokuValidator {

  @Override
  public boolean isValid(final Grid sudoku) {
    boolean result = true;
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      result = result && sudoku.getBlock(i).isValid() && sudoku.getColumn(i).isValid() && sudoku.getRow(i).isValid();
      if (!result) {
        break;
      }
    }
    return result;
  }

}
