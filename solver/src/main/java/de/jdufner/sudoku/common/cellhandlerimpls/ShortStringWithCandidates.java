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
package de.jdufner.sudoku.common.cellhandlerimpls;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.Literal;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-03-10
 * @version $Revision$
 */
public final class ShortStringWithCandidates implements CellHandler {

  private final static char CELL_SEPARATOR = ',';
  private final static char CANDIDATE_BEGIN = '(';
  private final static char CANDIDATE_END = ')';

  private transient StringBuilder stringBuilder = null;

  @Override
  public void initialize() {
    stringBuilder = new StringBuilder();
  }

  @Override
  public void handleCell(final Cell cell) {
    if (cell.getNumber() > 0) {
      stringBuilder.append(CELL_SEPARATOR);
    }
    if (cell.isFixed()) {
      stringBuilder.append(cell.getValue());
    } else {
      stringBuilder.append(CANDIDATE_BEGIN);
      for (Literal candidate : cell.getCandidates()) {
        stringBuilder.append(candidate);
      }
      stringBuilder.append(CANDIDATE_END);
    }
  }

  @Override
  public String toString() {
    return stringBuilder.toString();
  }

}
