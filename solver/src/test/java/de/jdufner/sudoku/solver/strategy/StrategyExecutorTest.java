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
public class StrategyExecutorTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(StrategyExecutorTest.class);

  /**
   * Dieses Sudoku ist nur mit Hilfe von Raten lösbar
   */
  private static final String SUDOKU_AS_STRING =
  //".5..9...2.6..2...8..7...9...81.............5...69.3......7.2..5...6..4..8.9.5...3";
  //".1...4..3.3..5..7.74....1.......25....1...6....35.......2....47.9..7..2.4..3...1.";
  //".....2..9..2...7.8..5..6...6.......342...3..7..7.1...4.71..........8......69.5.2.";
  //"..8..6.2..3.......1945..8.......31........5.2942............91.3.16...85...78...6";
  //"...4.9......1.84....5....3...6....71....7.....7.8...5.6....3.2..53..69..1...2....";
  //".7.49.2.....18.3..1....5.6.54.8......3..2..587............5..........64...6..2.3.";
  //".7.1.........24.5...8...91......3.2.3..41......9...17..42...39......2....17.9....";
  //".8.4.175.....8..4.7.........1..2..3.....1.4..35....9..8....2...5.6...32..427.....";
  //"..3.8...1.5.14..7.7..5..4...9.7....21.2.3............8.3...4.....8..........5.12.";
  "..2..3.7.7.62...1..4.17.2.3...4381....3...5....4651...3.1.84.5..9...73.1.5.3..9..";

  private transient Grid sudoku;

  public StrategyExecutorTest(final String name) {
    super(name);
  }

  @Override
  public void setUp() throws Exception {
    sudoku = SudokuFactory.INSTANCE.buildSudoku(SUDOKU_AS_STRING);
  }

  /*
   * public void testExecuteParallel() { final StrategyConfiguration configuration = new
   * StrategyConfiguration(StrategyThreadingEnum.PARALLEL) .add(StrategyNameEnum.values()); final StrategyExecutor
   * strategyExecutor = new StrategyExecutor(sudoku, configuration); LOG.debug(strategyExecutor);
   * strategyExecutor.execute(); // SolutionImpl solution = strategyExecutor.buildStrategySolution(); //
   * LOG.debug(solution); }
   */

  public void testExecuteSerial() {
    final StrategyConfiguration configuration = new StrategyConfiguration(StrategyThreadingEnum.SERIAL).add(
        StrategyNameEnum.values()).remove(StrategyNameEnum.BACKTRACKING);
    final StrategyExecutor strategyExecutor = new StrategyExecutor(sudoku, configuration);
    LOG.debug(strategyExecutor);
    strategyExecutor.execute();
    //    SolutionImpl solution = strategyExecutor.buildStrategySolution();
    //    LOG.debug(solution);
  }
}
