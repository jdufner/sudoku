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

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Grid;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since
 * 
 */
public abstract class AbstractSerialStrategy extends AbstractStrategy {

  //  private static final Logger LOG = Logger.getLogger(AbstractSerialStrategy.class);

  private final transient Collection<AbstractStrategy> strategies = new ArrayList<AbstractStrategy>();

  public AbstractSerialStrategy(final Grid sudoku) {
    super(sudoku);
  }

  protected Collection<AbstractStrategy> getStrategies() {
    return strategies;
  }

  protected void gatherCommandsFromStrategies() {
    for (AbstractStrategy strategy : strategies) {
      getCommands().addAll(strategy.executeStrategy());
    }
  }

  @Override
  public Collection<Command> executeStrategy() {
    gatherCommandsFromStrategies();
    return getCommands();
  }

}
