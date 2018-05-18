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


package de.jdufner.sudoku.solver.strategy.configuration;

/**
 * Aufzählung aller verwendbaren Lösungstechniken.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2009-11-24
 * 
 */
public enum StrategyNameEnum {

  // TODO Lösungstechniken dokumentieren mit Hinweis auf sudopedia.org ?
  SIMPLE(), //
  NAKED_SINGLE(), //
  HIDDEN_SINGLE(), //
  NAKED_PAIR(), NAKED_TRIPLE(), NAKED_QUAD(), //
  HIDDEN_PAIR(), HIDDEN_TRIPLE(), HIDDEN_QUAD, //
  //POINTING_PAIR(), // Pointing Pair ist eine Unterform von Intersection Removal
  //BOX_LINE_REDUCTION(), // Box Line Reduction ist eine Unterform von Intersection Removal
  INTERSECTION_REMOVAL(), //
  YWING(), //
  XWING(), //
  SWORDFISH(), //
  JELLYFISH(), //
  BACKTRACKING();

}
