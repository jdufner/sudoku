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


package de.jdufner.sudoku.commands;

import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public final class UnsetValueCommand extends AbstractSingleValueCommand {

  private final static String JAVASCRIPT_COMMAND_NAME = "UV";

  private UnsetValueCommand(final StrategyNameEnum strategyNameEnum, final int row, final int column,
      final Literal value) {
    super(strategyNameEnum);
    this.rowIndex = row;
    this.columnIndex = column;
    this.value = value;
  }

  @Override
  public void executeCommand(final Grid sudoku) {
    if (sudoku.getCell(rowIndex, columnIndex).isFixed()) {
      sudoku.getCell(rowIndex, columnIndex).setValue(Literal.EMPTY);
      successfully = true;
    } else {
      successfully = false;
    }
  }

  @Override
  public void unexecuteCommand(final Grid sudoku) {
    sudoku.getCell(rowIndex, columnIndex).setValue(value);
  }

  @Override
  public boolean reversible() {
    return true;
  }

  @Override
  public String toString() {
    return getStrategyName() + ": Entferne Wert " + value + " aus Zelle (" + rowIndex + ", " + columnIndex + ")";
  }

  @Override
  protected String toString(final Grid sudoku) {
    return getStrategyName() + ": Entferne Wert " + value + " aus Zelle " + getCell(sudoku);
  }

  @Override
  public boolean equals(final Object other) { // NOPMD Jürgen Dufner 14.03.2010
    if (other instanceof SetCandidateCommand) {
      return super.equals(other);
    }
    return false;
  }

  @Override
  public String toJavascriptString() {
    return getJavascriptCellNumber() + JAVASCRIPT_COMMAND_NAME + value.getValue();
  }

  public static class UnsetValueCommandBuilder {

    private final transient StrategyNameEnum strategyNameEnum;
    private final transient int rowIndex;
    private final transient int columnIndex;
    private final transient Literal value;

    public UnsetValueCommandBuilder(final StrategyNameEnum strategyNameEnum, final int rowIndex, final int columnIndex,
        final Literal value) {
      this.strategyNameEnum = strategyNameEnum;
      this.rowIndex = rowIndex;
      this.columnIndex = columnIndex;
      this.value = value;
    }

    public UnsetValueCommand build() {
      return new UnsetValueCommand(strategyNameEnum, rowIndex, columnIndex, value);
    }

  }

}
