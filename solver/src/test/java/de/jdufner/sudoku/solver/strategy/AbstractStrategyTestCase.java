// $Id$

/*
 * Gudoku (http://sourceforge.net/projects/gudoku)
 * Sudoku-Implementierung auf Basis des Google Webtoolkit
 * (http://code.google.com/webtoolkit/). Die Lösungsalgorithmen in Java laufen
 * parallel. Die Sudoku-Rätsel werden mittels JDBC in einer Datenbank
 * gespeichert.
 *
 * Copyright (C) 2008 Jürgen Dufner
 *
 * Dieses Programm ist freie Software. Sie können es unter den Bedingungen der
 * GNU General Public License, wie von der Free Software Foundation
 * veröffentlicht, weitergeben und/oder modifizieren, entweder gemäß Version 3
 * der Lizenz oder (nach Ihrer Option) jeder späteren Version.
 *
 * Die Veröffentlichung dieses Programms erfolgt in der Hoffnung, daß es Ihnen
 * von Nutzen sein wird, aber OHNE IRGENDEINE GARANTIE, sogar ohne die
 * implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FÜR EINEN
 * BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
 *
 * Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem
 * Programm erhalten haben. Falls nicht, siehe <http://www.gnu.org/licenses/>.
 *
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
