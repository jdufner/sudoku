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
package de.jdufner.sudoku.common.board;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * A cell is a field at the sudoku board. It has either a fixed value or a list
 * of at least two candidates. A cell has two coordinates: row index, column
 * index. Each cell has a unique pair of row and cell index. Out of the row and
 * column index it is possible to compute the block index.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * @see <a
 *      href="http://sudopedia.org/wiki/Cell">http://sudopedia.org/wiki/Cell</a>
 */
public final class Cell implements Comparable<Cell> {

  private static final Logger LOG = Logger.getLogger(Cell.class);
  private static final Random RANDOM = new Random();

  private final transient SudokuSize sudokuSize;
  private final transient int rowIndex;
  private final transient int columnIndex;
  private final transient int blockIndex;
  private Literal value;
  private Candidates<Literal> candidates = new Candidates<Literal>();

  public Cell(final int rowIndex, final int columnIndex, final Literal value, final SudokuSize sudokuSize) {
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;
    this.blockIndex = BoxUtils.getBlockIndexByRowIndexAndColumnIndex(rowIndex, columnIndex, sudokuSize);
    this.sudokuSize = sudokuSize;
    setValue(value, false);
  }

  public Cell(final Cell cell) {
    this.rowIndex = cell.rowIndex;
    this.columnIndex = cell.columnIndex;
    this.blockIndex = cell.blockIndex;
    this.sudokuSize = cell.sudokuSize;
    this.value = cell.value;
    this.candidates.addAll(cell.candidates);
  }

  public Cell(final int number, final Literal value, final List<Literal> candidates, final SudokuSize sudokuSize) {
    this.rowIndex = CellUtils.getRowIndex(number, sudokuSize);
    this.columnIndex = CellUtils.getColumnIndex(number, sudokuSize);
    this.blockIndex = BoxUtils.getBlockIndexByRowIndexAndColumnIndex(rowIndex, columnIndex, sudokuSize);
    this.sudokuSize = sudokuSize;
    if (value == null) {
      // if (candidates != null && candidates.length > 0) {
      setValue(Literal.EMPTY, false);
      setCandidates(new Candidates<Literal>(candidates));
      // }
    } else {
      setValue(value, false);
    }
  }

  public Literal getValue() {
    return value;
  }

  public Candidates<Literal> getCandidates() {
    return candidates;
  }

  public void setCandidates(final Candidates<Literal> candidates) {
    this.candidates = candidates;
  }

  public boolean removeCandidates(final Collection<Literal> values) {
    if (values.size() <= 0) {
      return false;
    }
    final boolean candidatesRemoved = candidates.removeAll(values);
    if (candidatesRemoved) {
      if (LOG.isInfoEnabled()) {
        LOG.info("Entfernte " + values.size() + " Kandidaten " + values + " aus " + this);
      }
    }
    assert isValid();
    return candidatesRemoved;
  }

  public boolean removeCandidatesAndSetIfOnlyOneRemains(final Collection<Literal> values) {
    if (values.size() <= 0) {
      return false;
    }
    final boolean candidatesRemoved = candidates.removeAll(values);
    if (candidatesRemoved) {
      if (LOG.isInfoEnabled()) {
        LOG.info("Entfernte " + values.size() + " Kandidaten " + values + " aus " + this);
      }
    }
    if (candidates.size() == 1) {
      setValue(candidates.first());
      if (LOG.isInfoEnabled()) {
        LOG.info("Set " + this);
      }
    }
    assert isValid();
    return candidatesRemoved;
  }

  /**
   * Setzt einen Wert in dieser Zelle und leert die Kandidaten.
   * 
   * @param digit
   *          Der Wert der gesetzt wird.
   * @param isInitialized
   *          <code>true</code>, wenn die Zelle bereits initialisiert ist.
   */
  public void setValue(final Literal digit, final boolean isInitialized) {
    assert digit != null : "Value in Cell.setValue() is null.";
    assert digit.getValue() >= 0 : digit + " is lower or equals than 0.";
    assert digit.getValue() <= sudokuSize.getHouseSize() : digit + " is greater or equals than "
        + sudokuSize.getHouseSize();
    // assert isAllowed(value) : value + " is " + this + " not allowed!";
    if (digit == null) {
      LOG.warn("Tried to set " + digit + " (null)");
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("Set " + digit + " to " + this);
    }
    this.value = digit;
    // sudoku.setNumberOfFixedDirty(true);
    if (digit.equals(Literal.EMPTY)) {
      resetCandidates();
    } else {
      if (candidates == null) {
        candidates = new Candidates<Literal>();
      } else {
        candidates.clear();
      }
    }
    assert isValid();
  }

  /**
   * Sets the value and clear candidates.
   * 
   * @param digit
   */
  public void setValue(final Literal digit) {
    setValue(digit, true);
  }

  /**
   * @return <code>true</code> value is set, else <code>false</code>
   */
  public boolean isFixed() {
    return value.getValue() > 0;
  }

  /**
   * @return <code>true</code> if either is fixed and has no candidates or is
   *         not fixed and has candidates, else <code>false</code>
   */
  public boolean isValid() {
    return (isFixed() && getCandidates().size() <= 0) || (!isFixed() && getCandidates().size() > 0);
  }

  public int getBlockIndex() {
    return blockIndex;
  }

  public int getColumnIndex() {
    return columnIndex;
  }

  public int getRowIndex() {
    return rowIndex;
  }

  @Override
  public String toString() {
    return String.valueOf(value + " (" + rowIndex + ", " + columnIndex + ", " + blockIndex + ") " + candidates);
  }

  /**
   * Two cells are equal if the row and columns index are equal.
   */
  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof Cell) {
      final Cell that = (Cell) other;
      if (getNumber() == that.getNumber() && getValue().equals(that.getValue())) {
        return true;
      }
    }
    return false;
  }

  /**
   * The hash code is the number of the cell.
   */
  @Override
  public int hashCode() {
    return getNumber();
  }

  public int compareTo(final Cell other) {
    return getNumber() - other.getNumber();
  }

  /**
   * Resets a cell to no fixed value and resets the candidates.
   */
  public void reset() {
    value = Literal.getInstance(0);
    resetCandidates();
  }

  /**
   * Resets the candidates by inserting all literals in the list of candidates.
   */
  public void resetCandidates() {
    if (value.getValue() <= 0 || value.getValue() > sudokuSize.getHouseSize()) {
      if (candidates == null) {
        candidates = new Candidates<Literal>();
      } else {
        candidates.clear();
      }
      for (int i = 0; i < sudokuSize.getHouseSize(); i++) {
        candidates.add(Literal.getInstance(i + 1));
      }
    }
  }

  /**
   * @return The number of a cell by rowwise counting, e.g.
   * 
   *         <pre>
   *  0 1  2  3  4  5  6  7  8
   *   9 10 11 12 13 14 15 16 17
   *   ...
   * </pre>
   */
  public int getNumber() {
    return CellUtils.getNumber(rowIndex, columnIndex, sudokuSize);
  }

  /**
   * @return <code>true</code>, genau dann wenn dieses Cell in der ersten Hälfte
   *         einschließlich des mittleren Felds ist, sonst <code>false</code>.
   */
  public boolean isInFirstHalf() {
    return getNumber() <= sudokuSize.getTotalSize() / 2;
  }

  /**
   * @return <code>true</code>, wenn die Zelle die letzte Zelle im Sudoku ist,
   *         sonst <code>false</code>.
   */
  public boolean isLastField() {
    if (getNumber() >= sudokuSize.getTotalSize() - 1) {
      return true;
    }
    return false;
  }

  public boolean isTop() {
    if (getRowIndex() % sudokuSize.getBoxHeight() == 0) {
      return true;
    }
    return false;
  }

  public boolean isRight() {
    if (getColumnIndex() % sudokuSize.getBoxWidth() == sudokuSize.getBoxWidth() - 1) {
      return true;
    }
    return false;
  }

  public boolean isBottom() {
    if (getRowIndex() % sudokuSize.getBoxHeight() == sudokuSize.getBoxHeight() - 1) {
      return true;
    }
    return false;
  }

  public boolean isLeft() {
    if (getColumnIndex() % sudokuSize.getBoxWidth() == 0) {
      return true;
    }
    return false;
  }

}
