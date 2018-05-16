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
package de.jdufner.sudoku.common.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A House contains each {@link Literal} one time.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * @see <a
 *      href="http://sudopedia.org/wiki/House">http://sudopedia.org/wiki/House</a>
 */
public abstract class House implements Comparable<House> {
  /**
   * Die Größe ({@link SudokuSize}) des Sudokus.
   */
  protected transient SudokuSize sudokuSize;
  /**
   * Die Zellen, die in dieser Einheit enthalten sind.
   */
  protected transient List<Cell> cells;
  /**
   * Der Index dieser Einheit, beginnend mit 0.
   */
  protected transient int index;

  /**
   * @param sudokuSize
   *          Das Größe des Sudokus.
   * @param index
   *          Der Index der Einheit.
   * @param cells
   *          Eine Liste aller Zellen, die in dieser Einheit enthalten sind.
   */
  public House(final SudokuSize sudokuSize, final int index, final List<Cell> cells) {
    this.sudokuSize = sudokuSize;
    this.index = index;
    this.cells = cells;
  }

  /**
   * @return Der Index der Einheit.
   */
  public int getIndex() {
    return index;
  }

  /**
   * @return Eine Liste aller Zellen, die in dieser Einheit enthalten sind.
   */
  public List<Cell> getCells() {
    return cells;
  }

  /**
   * @return The fixed cells in the unit.
   */
  public SortedSet<Cell> getFixed() {
    final SortedSet<Cell> fixedCells = new TreeSet<Cell>();
    for (Cell cell : cells) {
      if (cell.isFixed()) {
        fixedCells.add(cell);
      }
    }
    return fixedCells;
  }

  /**
   * @return The fixed cells in the unit.
   */
  public Collection<Literal> getFixedAsLiteral() {
    final Collection<Literal> fixedCellLiterals = new ArrayList<Literal>();
    for (Cell cell : cells) {
      if (cell.isFixed()) {
        fixedCellLiterals.add(cell.getValue());
      }
    }
    return fixedCellLiterals;
  }

  /**
   * @return The open cells in the unit.
   */
  public SortedSet<Cell> getNonFixed() {
    final SortedSet<Cell> nonFixedCells = new TreeSet<Cell>();
    for (Cell cell : cells) {
      if (!cell.isFixed()) {
        nonFixedCells.add(cell);
      }
    }
    return nonFixedCells;
  }

  /**
   * @param numberCandidates
   *          Die Anzahl der Kandidaten in einer Zelle.
   * @return Die noch nicht gesetzen Zellen mit der übergebenen Anzahl an
   *         Kandidaten.
   */
  public SortedSet<Cell> getNonFixed(final int numberCandidates) {
    final SortedSet<Cell> nonFixedCells = new TreeSet<Cell>();
    for (Cell cell : cells) {
      if (!cell.isFixed() && cell.getCandidates().size() == numberCandidates) {
        nonFixedCells.add(cell);
      }
    }
    return nonFixedCells;
  }

  /**
   * @return The candidates of the unit.
   */
  public SortedSet<Literal> getCandidates() {
    final SortedSet<Literal> candidates = new TreeSet<Literal>();
    for (Cell cell : cells) {
      if (!cell.isFixed()) {
        candidates.addAll(cell.getCandidates());
      }
    }
    return candidates;
  }

  /**
   * @return <code>true</code>, wenn alle Zellen ({@link Cell}) gültig sind und
   *         jede gesetzte höchstens einmal gesetzt ist und jedes Literal
   *         mindestens einmal vorhanden ist, sonst <code>false</code>.
   * @see Cell#isValid()
   */
  public boolean isValid() {
    boolean candidates[] = new boolean[sudokuSize.getHouseSize()];
    final Map<Literal, Integer> fixedCounter = new HashMap<Literal, Integer>();
    for (Cell cell : cells) {
      if (!cell.isValid()) {
        return false;
      }
      if (cell.isFixed()) {
        if (fixedCounter.get(cell.getValue()) == null) {
          fixedCounter.put(cell.getValue(), 0);
        } else {
          // Einheit kann nicht gültig sein, wenn bereits eine
          // besetzte Zelle
          // mit diesem Wert vorhanden ist.
          return false;
        }
        fixedCounter.put(cell.getValue(), fixedCounter.get(cell.getValue()).intValue() + 1);
        candidates[cell.getValue().getValue() - 1] = true;
      } else {
        for (Literal candidate : cell.getCandidates()) {
          candidates[candidate.getValue() - 1] = true;
        }
      }
    }
    boolean result = true;
    for (int k = 0; k < candidates.length; k++) {
      result = result && candidates[k];
    }
    return result;
  }

  /**
   * Sums the fixed cells and returns the comparison to the checksum.
   * 
   * @return <code>true</code> if the sum of the fixed cell is equals to the
   *         checksum, else <code>false</code>
   */
  public boolean isSolved() {
    int sum = 0;
    for (Cell cell : getFixed()) {
      sum += cell.getValue().getValue();
    }
    if (sum == sudokuSize.getCheckSum()) {
      return true;
    }
    return false;
  }

  /**
   * @param literal
   * @return A list of cells that part of the unit and that candidates contains
   *         the given literal.
   */
  public SortedSet<Cell> getCellsThooseCandidatesContains(final Literal literal) {
    final SortedSet<Cell> cells2 = new TreeSet<Cell>();
    for (Cell cell : getNonFixed()) {
      if (cell.getCandidates().contains(literal)) {
        cells2.add(cell);
      }
    }
    return cells2;
  }

  /**
   * @param literals
   * @return A list of cells that part of the unit and that candidates contains
   *         the given literals.
   */
  public SortedSet<Cell> getCellsThooseCandidatesContains(final Collection<Literal> literals) {
    final SortedSet<Cell> cells2 = new TreeSet<Cell>();
    for (Cell cell : getNonFixed()) {
      if (cell.getCandidates().containsAll(literals)) {
        cells2.add(cell);
      }
    }
    return cells2;
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof House) {
      final House that = (House) other;
      return this.index == that.index;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.index;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder(String.valueOf(index));
    sb.append(" ").append(getCells());
    return sb.toString();
  }

  @Override
  public int compareTo(final House other) {
    return index - other.index;
  }

}
