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


package de.jdufner.sudoku.solver.strategy.backtracking;

import java.util.Collection;
import java.util.List;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.SetValueCommand.SetValueCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.backtracking.Backtracking;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public final class BacktrackingStrategy extends AbstractStrategy implements CellHandler {

  private transient Grid result = null;
  private transient boolean sudokuUnique = true;

  public BacktrackingStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.SEHR_SCHWER;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return StrategyNameEnum.BACKTRACKING;
  }

  @Override
  public boolean isSudokuUnique() {
    return sudokuUnique;
  }

  @Override
  public Collection<Command> executeStrategy() {
    final Backtracking backtracking = new Backtracking(getSudoku(), 1);
    final List<Grid> results = backtracking.firstSolutions(2);
    result = results.get(0);
    if (results.size() > 1) {
      sudokuUnique = false;
    }
    HandlerUtil.forEachCell(getSudoku(), this);
    return getCommands();
  }

  @Override
  public void initialize() {
    // nothing to do
  }

  @Override
  public void handleCell(final Cell cell) {
    if (!cell.isFixed()) {
      getCommands().add(
          new SetValueCommandBuilder(getStrategyName(), cell, result.getCell(cell.getNumber()).getValue()).build());
    }
  }

}
