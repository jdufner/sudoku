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


package de.jdufner.sudoku.solver.strategy;

import de.jdufner.sudoku.commands.AbstractCommand;
import de.jdufner.sudoku.common.misc.Level;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2009-12-27
 * 
 */
public interface Strategy {

  /**
   * @return Gibt den Schwierigkeitsgrad der Strategie zurück.
   */
  Level getLevel();

  /**
   * Führt eine konkrete Strategie.
   * 
   * @return Gibt beliebig viele {@link AbstractCommand}-Objekte zurück.
   */
  StrategyResult execute();

  /**
   * @return <code>true</code>, wenn für das bearbeitete Sudoku genau ein Lösung
   *         existiert, sonst <code>false</code>.
   */
  boolean isSudokuUnique();

}
