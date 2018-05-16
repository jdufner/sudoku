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
package de.jdufner.sudoku.builder.utils;

import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-28
 * @version $Revision$
 */
public final class Nachbarschaft {

  private static final Logger LOG = Logger.getLogger(Nachbarschaft.class);

  private Literal subjekt;
  private Collection<Literal> nachbarn;

  public Nachbarschaft(Cell subjekt, Collection<Cell> nachbarn) {
    this.subjekt = subjekt.getValue();
    this.nachbarn = new HashSet<Literal>();
    for (Cell cell : nachbarn) {
      this.nachbarn.add(cell.getValue());
    }
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof Nachbarschaft) {
      Nachbarschaft that = (Nachbarschaft) other;
      if (this.subjekt.equals(that.subjekt) //
          && this.nachbarn.size() == that.nachbarn.size() //
          && this.nachbarn.containsAll(that.nachbarn)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return subjekt.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(subjekt).append(" ").append(nachbarn);
    return sb.toString();
  }

}
