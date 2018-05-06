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
package de.jdufner.sudoku.commands;

import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
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
