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


package de.jdufner.sudoku.common.board;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Typesafe number for content of a cell.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public final class Literal implements Comparable<Literal> {

  private static final Logger LOG = Logger.getLogger(Literal.class);

  public static final Literal EMPTY = getInstance(0);

  private final transient int value;

  private static class SingletonHolder {
    private static Map<Integer, Literal> instances = new HashMap<Integer, Literal>();
  }

  public static Literal getInstance(final int value) {
    Literal literal = null;
    if ((literal = SingletonHolder.instances.get(value)) == null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Erzeuge neues Literal " + value);
      }
      literal = new Literal(value);
      SingletonHolder.instances.put(value, literal);
    }
    return literal;
  }

  private Literal(final int value) {
    this.value = value;
  }

  @Override
  public int compareTo(final Literal other) {
    Literal myOther = other;
    if (myOther == null) {
      myOther = getInstance(0);
    }
    return value - myOther.value;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof Literal) {
      final Literal that = (Literal) other;
      if (value == that.value) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public int getValue() {
    return value;
  }

}
