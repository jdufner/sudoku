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

// $Id: YWingSerialStrategy.java 54 2010-03-08 20:31:48Z jdufner $
package de.jdufner.sudoku.solver.strategy.ywing;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractParallelStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-02-20
 * @version $Revision: 54 $
 */
public final class YWingParallelStrategy extends AbstractParallelStrategy {

  private static final Logger LOG = Logger.getLogger(YWingParallelStrategy.class);

  public YWingParallelStrategy(final Grid sudoku) {
    super(sudoku);
    getCallables().add(new YWingColumnStrategy(sudoku));
    getCallables().add(new YWingRowStrategy(sudoku));
  }

  @Override
  public Level getLevel() {
    return Level.MITTEL;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return StrategyNameEnum.YWING;
  }

}
