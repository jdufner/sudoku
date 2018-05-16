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
package de.jdufner.sudoku.commands;

import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Dieser Befehl setzt mittels {@link Cell#setValue(Literal)} einen festen Wert {@link Literal} in eine {@link Cell}.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SetValueCommand extends AbstractSingleValueCommand {

  private final static String JAVASCRIPT_COMMAND_NAME = "SV";

  /**
   * Das sind die {@link Candidates} die beim setzen der Zelle gelöscht wurden.
   */
  private transient Candidates<Literal> candidates;

  private SetValueCommand(final StrategyNameEnum strategyNameEnum, final int row, final int column, final Literal value) {
    super(strategyNameEnum);
    this.rowIndex = row;
    this.columnIndex = column;
    this.value = value;
  }

  @Override
  public void executeCommand(final Grid sudoku) {
    if (sudoku.getCell(rowIndex, columnIndex).isFixed()) {
      successfully = false;
    } else {
      candidates = (Candidates<Literal>) getCell(sudoku).getCandidates().clone();
      sudoku.getCell(rowIndex, columnIndex).setValue(value);
      successfully = true;
    }
  }

  @Override
  public void unexecuteCommand(final Grid sudoku) {
    sudoku.getCell(rowIndex, columnIndex).setValue(Literal.EMPTY);
    sudoku.getCell(rowIndex, columnIndex).setCandidates(candidates);
  }

  @Override
  public boolean reversible() {
    return true;
  }

  @Override
  public String toString() {
    return getStrategyName() + ": Setze Wert " + value + " in Zelle (" + rowIndex + ", " + columnIndex + ")";
  }

  @Override
  protected String toString(final Grid sudoku) {
    return getStrategyName() + ": Setze Wert " + value + " in Zelle " + getCell(sudoku);
  }

  @Override
  public boolean equals(final Object other) { // NOPMD Jürgen Dufner 14.03.2010
    if (other instanceof SetValueCommand) {
      return super.equals(other);
    }
    return false;
  }

  @Override
  public String toJavascriptString() {
    return getJavascriptCellNumber() + JAVASCRIPT_COMMAND_NAME + value.getValue();
  }

  public static class SetValueCommandBuilder {

    private final transient StrategyNameEnum strategyNameEnum;
    private final transient int rowIndex;
    private final transient int columnIndex;
    private final transient Literal literal;

    public SetValueCommandBuilder(final StrategyNameEnum strategyNameEnum, final Cell cell, final Literal literal) {
      this.strategyNameEnum = strategyNameEnum;
      this.rowIndex = cell.getRowIndex();
      this.columnIndex = cell.getColumnIndex();
      this.literal = literal;
    }

    public SetValueCommandBuilder(final StrategyNameEnum strategyNameEnum, final int rowIndex, final int columnIndex,
        final int value) {
      this.strategyNameEnum = strategyNameEnum;
      this.rowIndex = rowIndex;
      this.columnIndex = columnIndex;
      this.literal = Literal.getInstance(value);
    }

    public SetValueCommand build() {
      return new SetValueCommand(strategyNameEnum, rowIndex, columnIndex, literal);
    }

  }

}
