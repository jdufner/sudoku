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
package de.jdufner.sudoku.solver.strategy.configuration;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since
 * @version $Revision$
 * 
 */
public final class StrategyNameEnumTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(StrategyNameEnumTest.class);

  public void testAll() {
    for (StrategyNameEnum strategyName : StrategyNameEnum.values()) {
      LOG.debug(strategyName);
    }
    assertEquals(15, StrategyNameEnum.values().length);
  }

}
