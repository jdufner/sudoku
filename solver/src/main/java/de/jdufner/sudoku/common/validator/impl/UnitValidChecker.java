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

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import de.jdufner.sudoku.common.board.House;

/**
 * Die Klasse prüft ob die übergebenen Einheit gültig sind.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class UnitValidChecker implements Callable<Boolean> {
  // private static final Logger LOG = Logger.getLogger(UnitValidChecker.class);

  private final transient AtomicBoolean validity;
  private final transient Collection<? extends House> units;

  public UnitValidChecker(final AtomicBoolean validity, final Collection<? extends House> units) {
    this.validity = validity;
    this.units = units;
  }

  @Override
  public Boolean call() {
    for (House unit : units) {
      if (validity.get()) {
        final boolean unitValidity = unit.isValid();
        if (!unitValidity) {
          validity.set(unitValidity);
          return unitValidity;
        }
      } else {
        return false;
      }
    }
    return true;
  }

}
