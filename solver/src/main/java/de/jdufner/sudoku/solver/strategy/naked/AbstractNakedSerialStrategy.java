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
package de.jdufner.sudoku.solver.strategy.naked;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractSerialStrategy;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractNakedSerialStrategy extends AbstractSerialStrategy implements Naked {

  //  private static final Logger LOG = Logger.getLogger(AbstractSerialStrategy.class);

  protected AbstractNakedSerialStrategy(final Grid sudoku) {
    super(sudoku);
    final NakedBlockStrategy nakedBlockStrategy = new NakedBlockStrategy(sudoku);
    nakedBlockStrategy.setSize(getSize());
    nakedBlockStrategy.setStrategyNameEnum(getStrategyName());
    getStrategies().add(nakedBlockStrategy);
    final NakedColumnStrategy nakedColumnStrategy = new NakedColumnStrategy(sudoku);
    nakedColumnStrategy.setSize(getSize());
    nakedColumnStrategy.setStrategyNameEnum(getStrategyName());
    getStrategies().add(nakedColumnStrategy);
    final NakedRowStrategy nakedRowStrategy = new NakedRowStrategy(sudoku);
    nakedRowStrategy.setSize(getSize());
    nakedRowStrategy.setStrategyNameEnum(getStrategyName());
    getStrategies().add(nakedRowStrategy);
  }

  @Override
  public Level getLevel() {
    return Level.LEICHT;
  }
}
