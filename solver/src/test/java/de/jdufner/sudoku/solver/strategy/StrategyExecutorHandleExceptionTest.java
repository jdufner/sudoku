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

import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyConfiguration;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyThreadingEnum;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class StrategyExecutorHandleExceptionTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(StrategyExecutorHandleExceptionTest.class);

  /**
   * Dieses Sudoku ist nur mit Hilfe von Raten lösbar
   */
  private static final String SUDOKU_AS_STRING = ".5..9...2.6..2...8..7...9...81.............5...69.3......7.2..5...6..4..8.9.5...3";

  private Grid sudoku;

  public StrategyExecutorHandleExceptionTest(String name) {
    super(name);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    sudoku = SudokuFactory.INSTANCE.buildSudoku(SUDOKU_AS_STRING);
    System.setProperty("test.solver.exceptionInParallelTask", "true");
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    System.clearProperty("test.solver.exceptionInParallelTask");
  }

  public void testExecute() {
    try {
      StrategyConfiguration configuration = new StrategyConfiguration(StrategyThreadingEnum.PARALLEL)
          .add(StrategyNameEnum.values());
      StrategyExecutor strategyExecutor = new StrategyExecutor(sudoku, configuration);
      LOG.debug(strategyExecutor);
      strategyExecutor.execute();
      fail("SudokuRuntimeException erwartet.");
    } catch (Exception e) {
      assertTrue(contains(e, ExecutionException.class));
    }
  }

  private boolean contains(final Exception container, final Class<? extends Exception> subject) {
    Throwable exception = container;
    while (exception != null && !(exception.getClass().equals(subject))) {
      exception = exception.getCause();
    }
    if (exception != null && exception.getClass().equals(subject)) {
      return true;
    }
    return false;
  }
}
