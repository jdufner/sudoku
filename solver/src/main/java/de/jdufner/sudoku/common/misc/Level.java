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


package de.jdufner.sudoku.common.misc;

/**
 * Schwierigkeitsgrad eines Sudokus.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public enum Level implements Comparable<Level> {

  UNBEKANNT("unbekannt", 0), //
  SEHR_LEICHT("sehr leicht", 1), //
  LEICHT("leicht", 2), //
  MITTEL("mittel", 3), //
  SCHWER("schwer", 4), //
  SEHR_SCHWER("sehr schwer", 5);

  private String name;
  private int value;

  private Level(final String name, final int value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public int getValue() {
    return value;
  }

  public static Level valueOf(final int value) {
    for (Level level : values()) {
      if (level.value == value) {
        return level;
      }
    }
    return UNBEKANNT;
  }

  @Override
  public String toString() {
    return name;
  }

}
