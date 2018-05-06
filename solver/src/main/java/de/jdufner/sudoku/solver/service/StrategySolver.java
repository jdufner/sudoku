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
package de.jdufner.sudoku.solver.service;

import java.util.List;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.StrategyExecutor;
import de.jdufner.sudoku.solver.strategy.StrategyResult;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyConfiguration;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyThreadingEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class StrategySolver implements ExtendedSolver {

  //  private static final Logger LOG = Logger.getLogger(StrategySolver.class);

  private transient boolean useBacktracking;

  // Solver
  @Override
  public Grid solve(final Grid sudoku) {
    final Grid sudokuResult = new Grid(sudoku);
    final StrategyExecutor executor = new StrategyExecutor(sudokuResult, getConfiguration());
    executor.execute();
    return sudokuResult;
  }

  // Solver
  public boolean isSolvable(final Grid sudoku) {
    return solve(sudoku).isSolved();
  }

  // Solver
  public boolean isUnique(final Grid sudoku) {
    final Grid sudokuResult = new Grid(sudoku);
    final StrategyConfiguration configuration = getConfiguration();
    final StrategyExecutor executor = new StrategyExecutor(sudokuResult, configuration);
    final List<StrategyResult> results = executor.execute();
    return isSudokuUnique(results);
  }

  // ExtendedSolver
  public Solution getSolution(final Grid sudoku) {
    final Grid sudokuQuest = new Grid(sudoku);
    final Grid sudokuResult = new Grid(sudoku);
    final StrategyConfiguration configuration = getConfiguration();
    final StrategyExecutor executor = new StrategyExecutor(sudokuResult, configuration);
    final List<StrategyResult> results = executor.execute();
    final Solution solution = new Solution(sudokuQuest);
    if (sudokuResult.isSolved()) {
      solution.setResult(sudokuResult);
      solution.setUnique(isSudokuUnique(results));
      solution.setLevel(getMaxLevel(results));
      solution.setResults(results);
    }
    return solution;
  }

  public Level getMaxLevel(final List<StrategyResult> results) {
    Level level = Level.SEHR_LEICHT;
    for (StrategyResult result : results) {
      if (level.compareTo(result.getLevel()) < 0) {
        level = result.getLevel();
      }
    }
    return level;
  }

  public boolean isSudokuUnique(final List<StrategyResult> results) {
    for (StrategyResult result : results) {
      if (!result.isSudokuUnique()) {
        return false;
      }
    }
    return true;
  }

  private StrategyConfiguration getConfiguration() {
    final StrategyConfiguration configuration = new StrategyConfiguration(StrategyThreadingEnum.SERIAL)
        .add(StrategyNameEnum.values());
    if (!useBacktracking) {
      configuration.remove(StrategyNameEnum.BACKTRACKING);
    }
    return configuration;
  }

  //
  // Spring Wiring
  //
  public void setUseBacktracking(final boolean useBacktracking) {
    this.useBacktracking = useBacktracking;
  }

}
