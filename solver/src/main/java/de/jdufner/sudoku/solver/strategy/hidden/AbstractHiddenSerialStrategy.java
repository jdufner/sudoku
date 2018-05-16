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


package de.jdufner.sudoku.solver.strategy.hidden;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractSerialStrategy;

/**
 * Diese abstrakte Superklasse implementiert die Parallelsierung für <a
 * href="http://www.sudopedia.org/wiki/Hidden_Subset">Hidden Subset http://www.sudopedia.org/wiki/Hidden_Subset</a>.
 * 
 * Die Logik wird nicht redundant implementiert, sondern aus {@link AbstractHiddenStrategy} und Subklassen
 * wiederverwendet.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.2
 * @version $Revision$
 */
public abstract class AbstractHiddenSerialStrategy extends AbstractSerialStrategy implements Hidden {

  private static final Logger LOG = Logger.getLogger(AbstractHiddenSerialStrategy.class);

  protected AbstractHiddenSerialStrategy(final Grid sudoku) {
    super(sudoku);
    final HiddenBlockStrategy hiddenBlockStrategy = new HiddenBlockStrategy(sudoku);
    hiddenBlockStrategy.setSize(getSize());
    hiddenBlockStrategy.setStrategyName(getStrategyName());
    getStrategies().add(hiddenBlockStrategy);
    final HiddenColumnStrategy hiddenColumnStrategy = new HiddenColumnStrategy(sudoku);
    hiddenColumnStrategy.setSize(getSize());
    hiddenColumnStrategy.setStrategyName(getStrategyName());
    getStrategies().add(hiddenColumnStrategy);
    final HiddenRowStrategy hiddenRowStrategy = new HiddenRowStrategy(sudoku);
    hiddenRowStrategy.setSize(getSize());
    hiddenRowStrategy.setStrategyName(getStrategyName());
    getStrategies().add(hiddenRowStrategy);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Strategies erzeugt: " + getStrategies());
    }
  }

  @Override
  public Level getLevel() {
    return Level.MITTEL;
  }

  @Override
  public abstract int getSize();

}
