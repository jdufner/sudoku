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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellUtils;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Ein Befehl ist eine Aktion, die auf einem Sudoku ausgeführt werden kann. Es sind mehrere Aktionen auf einem Sudoku
 * möglich. Setzen einer Zelle, Elemieren einer oder mehrerer Kandidaten. Ein Befehl muss nicht immer erfolgreich sein,
 * das bedeutet, dass die Aktion nicht tatsächlich zu einer Elimination führt oder ein Feld setzt.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractCommand implements Command {

  private static final Logger LOG = Logger.getLogger(AbstractCommand.class);

  private transient String frozenString = null;

  protected transient StrategyNameEnum strategyNameEnum = null;
  protected transient int rowIndex, columnIndex;
  protected transient Literal value;
  protected transient boolean successfully = false;

  /**
   * Konstruktor mit Information über den Erzeuger.
   * 
   * @param strategyName
   *          Der Erzeuger des Befehls.
   */
  protected AbstractCommand(final StrategyNameEnum strategyNameEnum) {
    this.strategyNameEnum = strategyNameEnum;
  }

  @Override
  public void execute(final Grid sudoku) {
    freeze(sudoku);
    executeCommand(sudoku);
    if (!getCell(sudoku).isValid()) {
      LOG.warn(getCell(sudoku) + " ist nach Ausführung von " + this + " nicht mehr gültig.");
    }
  }

  /**
   * Interne Implementierung der {@link #execute(Grid)}-Methode, die von den Subklassen implementiert werden muss.
   * 
   * @param sudoku
   */
  protected abstract void executeCommand(final Grid sudoku);

  @Override
  public void unexecute(final Grid sudoku) {
    unexecuteCommand(sudoku);
    if (!getCell(sudoku).isValid()) {
      LOG.warn(getCell(sudoku) + " ist nach Rücknahme von " + this + " nicht mehr gültig.");
    }
  }

  /**
   * Interne Implementierung der {@link #unexecute(Grid)}-Methode, die von den Subklassen implementiert werden muss.
   * 
   * @param sudoku
   */
  protected abstract void unexecuteCommand(final Grid sudoku);

  @Override
  public abstract boolean reversible();

  @Override
  public boolean isSuccessfully() {
    return successfully;
  }

  @Override
  public String getStrategyName() {
    if (strategyNameEnum == null) {
      return null;
    }
    return strategyNameEnum.name();
  }

  /**
   * Speichert den Inhalt der {@link #toString()}-Methode des Objekts im Attribut {@link #frozenString} ab und kann mit
   * {@link #getFrozenString()} abgerufen werden. Diese Methode ist nach Erzeugung des Objekts aufzurufen.
   */
  protected void freeze(final Grid sudoku) {
    if (frozenString == null || frozenString.length() <= 0) {
      frozenString = this.toString(sudoku);
    }
  }

  protected abstract String toString(Grid sudoku);

  @Override
  public String getFrozenString() {
    return frozenString;
  }

  @Override
  public int getColumnIndex() {
    return columnIndex;
  }

  @Override
  public int getRowIndex() {
    return rowIndex;
  }

  protected Cell getCell(final Grid sudoku) {
    return sudoku.getCell(getRowIndex(), getColumnIndex());
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof AbstractCommand) {
      final AbstractCommand that = (AbstractCommand) other;
      if ((this.strategyNameEnum == that.strategyNameEnum || //
          (this.strategyNameEnum == null ? false : this.strategyNameEnum.equals(that.strategyNameEnum)))
          && this.rowIndex == that.rowIndex && this.columnIndex == that.columnIndex && this.value == that.value) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 17;
    final int hashMultiplier = 31; // NOPMD Jürgen Dufner 14.03.2010
    hashCode *= hashMultiplier + (strategyNameEnum == null ? 0 : strategyNameEnum.hashCode());
    hashCode *= hashMultiplier + rowIndex;
    hashCode *= hashMultiplier + columnIndex;
    hashCode *= hashMultiplier + (value == null ? 0 : value.getValue());
    return hashCode;
  }

  protected boolean isEqual(final Collection<Literal> col1, final Collection<Literal> col2) {
    final Set<Literal> set1 = new HashSet<Literal>(col1);
    final Set<Literal> set2 = new HashSet<Literal>(col2);
    return set1.containsAll(set2) && set2.containsAll(set1);
  }

  protected String getJavascriptCellNumber() {
    return String.valueOf(CellUtils.getNumber(rowIndex, columnIndex, SudokuSize.NEUN) + 1);
  }

}
