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
 * 
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
