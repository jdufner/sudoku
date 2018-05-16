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
package de.jdufner.sudoku.common.exceptions;

public class SudokuRuntimeException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 8083222351578824111L;

  public SudokuRuntimeException() {
    super();
  }

  public SudokuRuntimeException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public SudokuRuntimeException(final String message) {
    super(message);
  }

  public SudokuRuntimeException(final Throwable cause) {
    super(cause);
  }

}
