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
package de.jdufner.sudoku.solver.strategy.simple;

import java.util.Collection;
import java.util.concurrent.Callable;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Box;
import de.jdufner.sudoku.common.board.BoxHandler;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.exceptions.SudokuRuntimeException;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SimpleBlockStrategy extends AbstractSimpleStrategy implements BoxHandler,
    Callable<Collection<Command>> {

  // private static final Logger LOG = Logger.getLogger(SimpleBlockStrategy.class);

  public SimpleBlockStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Collection<Command> executeStrategy() {
    String exceptionInParallelTask = System.getProperty("test.solver.exceptionInParallelTask");
    if (exceptionInParallelTask != null && exceptionInParallelTask.equalsIgnoreCase("true")) {
      throw new SudokuRuntimeException("Systemproperty 'test.solver.exceptionInParallelTask' hat den Wert '"
          + exceptionInParallelTask + "', deshalb wird diese Exception geworfen.");
    }
    HandlerUtil.forEachBlock(getSudoku(), this);
    return getCommands();
  }

  public void handleBlock(final Box block) {
    handleUnit(block);
  }

  public Collection<Command> call() {
    return executeStrategy();
  }

}
