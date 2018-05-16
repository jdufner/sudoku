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
package de.jdufner.sudoku.common.board;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * Erstellt eine Map in der jedes Literal als Schlüssel dient und als Wert eine Liste der Zellen hat, in welchen das
 * Literal existiert.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class Literal2CellMap {

  private final Map<Literal, SortedSet<Cell>> map = new HashMap<Literal, SortedSet<Cell>>(); // NOPMD by Jürgen on 11.11.09 22:11

  /**
   * Erzeugt eine Map aus den übergebenen Zellen.
   * 
   * TODO Sollte der Parameter nicht besser Unit sein?
   * 
   * @param cells
   */
  public Literal2CellMap(final Collection<Cell> cells) {
    for (Cell cell : cells) {
      for (Literal literal : cell.getCandidates()) {
        if (map.get(literal) == null) {
          map.put(literal, new TreeSet<Cell>()); // NOPMD by Jürgen on 11.11.09 22:14
        }
        map.get(literal).add(cell);
      }
    }
  }

  /**
   * Gibt die Anzahl der Zellen zurück, die das übergebene Literal enthalten. Wenn das Literal nicht vorhanden ist, wird
   * der Wert 0 zurückgegeben. Es findet keine Prüfung statt, ob das übergeben Literal innerhalb definierter Grenzen
   * liegt.
   * 
   * @param literal
   *          Gesuchtes Literal. Keine Prüfung, ob das Literal innerhalb definierter Grenzen liegt.
   * @return Die Anzahl der Zellen, die das übergebene Literal enthalten. Wenn das Literal nicht vorhanden ist, wird der
   *         Wert 0 zurückgegeben.
   */
  public int getNumber(final Literal literal) {
    if (getCellsContainingLiteral(literal) == null) {
      return 0;
    }
    return getCellsContainingLiteral(literal).size();
  }

  public SortedSet<Cell> getCellsContainingLiteral(final Literal literal) {
    return map.get(literal);
  }

  /**
   * Gibt die Kandidaten zurück, die in der übergebenen Anzahl in der Map vorkommen.
   * 
   * @param number
   *          Die Häufigkeit, wie oft ein Literal vorkommen soll.
   * @return Die Kandidaten, die in der übergebenen Anzahl in der Map vorkommen.
   */
  public SortedSet<Literal> getCandidatesByNumber(final int number) {
    final SortedSet<Literal> literals = new TreeSet<Literal>();
    for (Literal literal : map.keySet()) {
      if (map.get(literal).size() == number) {
        literals.add(literal);
      }
    }
    return literals;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder(); // NOPMD by Jürgen on 11.11.09 22:52
    for (Literal literal : map.keySet()) {
      sb.append(literal).append(": ");
      for (Cell cell : map.get(literal)) {
        sb.append(cell);
        if (!map.get(literal).last().equals(cell)) {
          sb.append(", ");
        }
      }
      sb.append("; ");
    }
    return super.toString();
  }
}
