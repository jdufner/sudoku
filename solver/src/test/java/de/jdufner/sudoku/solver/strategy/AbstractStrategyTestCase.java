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

import java.util.Collection;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandUtils;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-03-08
 * @version $Revision$
 */
public abstract class AbstractStrategyTestCase extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(AbstractStrategyTestCase.class);

  protected transient Grid sudoku;
  protected transient Strategy strategy;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    sudoku = SudokuFactory.INSTANCE.buildSudoku(getSudokuAsString());
    strategy = getStrategy();
    LOG.debug("Vorher: " + sudoku);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    LOG.debug("Nachher: " + sudoku);
  }

  protected abstract String getSudokuAsString();

  protected abstract Strategy getStrategy();

  protected abstract Collection<Command> getCommands();

  public void testAllgemein() {
    final StrategyResult strategyResult = strategy.execute();
    assertNotNull("Eine Strategie muss ein Ergebnis ungleich null liefern.", strategyResult);
    assertNotNull("Eine Strategie muss eine (leere) Liste von Befehlen liefern.", strategyResult.getCommands());
    assertTrue("Eine Strategie muss mindestens ein Befehl liefern.", strategyResult.getCommands().size() > 0);
    LOG.debug(strategyResult.getCommands());
    assertEquals("Eine Strategie muss diese Anzahl von Befehlen liefern.", getCommands().size(), strategyResult
        .getCommands().size());
    assertTrue("Eine Strategie muss diese Befehle liefern: " + getCommands() + " lieferte aber "
        + strategyResult.getCommands(), CommandUtils.isEqual(getCommands(), strategyResult.getCommands()));
  }
}
