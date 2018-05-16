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

import org.apache.log4j.Logger;

import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public final class LevelTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(LevelTest.class);

  public void testCompareTo() {
    assertTrue(Level.SEHR_LEICHT.compareTo(Level.LEICHT) < 0);
    assertTrue(Level.SEHR_LEICHT.compareTo(Level.MITTEL) < 0);
    assertTrue(Level.SEHR_LEICHT.compareTo(Level.SCHWER) < 0);
    assertTrue(Level.SEHR_LEICHT.compareTo(Level.SEHR_SCHWER) < 0);
    assertTrue(Level.LEICHT.compareTo(Level.SEHR_LEICHT) > 0);
    assertTrue(Level.LEICHT.compareTo(Level.MITTEL) < 0);
    assertTrue(Level.LEICHT.compareTo(Level.SCHWER) < 0);
    assertTrue(Level.LEICHT.compareTo(Level.SEHR_SCHWER) < 0);
    assertTrue(Level.MITTEL.compareTo(Level.SEHR_LEICHT) > 0);
    assertTrue(Level.MITTEL.compareTo(Level.LEICHT) > 0);
    assertTrue(Level.MITTEL.compareTo(Level.SCHWER) < 0);
    assertTrue(Level.MITTEL.compareTo(Level.SEHR_SCHWER) < 0);
    assertTrue(Level.SCHWER.compareTo(Level.SEHR_LEICHT) > 0);
    assertTrue(Level.SCHWER.compareTo(Level.LEICHT) > 0);
    assertTrue(Level.SCHWER.compareTo(Level.MITTEL) > 0);
    assertTrue(Level.SCHWER.compareTo(Level.SEHR_SCHWER) < 0);
    assertTrue(Level.SEHR_SCHWER.compareTo(Level.SEHR_LEICHT) > 0);
    assertTrue(Level.SEHR_SCHWER.compareTo(Level.LEICHT) > 0);
    assertTrue(Level.SEHR_SCHWER.compareTo(Level.MITTEL) > 0);
    assertTrue(Level.SEHR_SCHWER.compareTo(Level.SCHWER) > 0);
  }

  public void testIntToEnum() {
    Level l;
    try {
      l = Level.valueOf(Level.LEICHT.getName());
      LOG.debug(l);
      fail();
    } catch (IllegalArgumentException iae) {
      LOG.debug(iae.getMessage(), iae);
    }
    l = Level.valueOf(2);
    assertEquals(Level.LEICHT, l);
  }

}
