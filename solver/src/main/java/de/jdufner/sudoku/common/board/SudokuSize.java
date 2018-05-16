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


package de.jdufner.sudoku.common.board;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public enum SudokuSize {

  VIER(2, 2), NEUN(3, 3), ZEHN(2, 5);

  public static final SudokuSize DEFAULT = NEUN;

  private final int boxWidth;

  private final int boxHeight;

  private final int houseSize;

  private final int totalSize;

  private final int unitChecksum;

  private SudokuSize(final int blockWidth, final int blockHeight) {
    this.boxWidth = blockWidth;
    this.boxHeight = blockHeight;
    this.houseSize = blockWidth * blockHeight;
    this.totalSize = houseSize * houseSize;
    this.unitChecksum = (houseSize * (houseSize + 1)) / 2;
  }

  /**
   * @return The width of one block.
   */
  public int getBoxWidth() {
    return boxWidth;
  }

  /**
   * @return The height of one block.
   */
  public int getBoxHeight() {
    return boxHeight;
  }

  /**
   * @return The number of cells in one unit.
   */
  public int getHouseSize() {
    return houseSize;
  }

  /**
   * @return The number of cells in the sudoku.
   */
  public int getTotalSize() {
    return totalSize;
  }

  /**
   * @return The checksum of all cells in one unit.
   */
  public int getCheckSum() {
    return unitChecksum;
  }

  /**
   * @param unitSize
   * @return The enumeration element of the unit size;
   */
  public static SudokuSize getByUnitSize(final int unitSize) {
    for (int i = 0; i < SudokuSize.values().length; i++) {
      if (SudokuSize.values()[i].houseSize == unitSize) {
        return SudokuSize.values()[i];
      }
    }
    throw new IllegalArgumentException("Sudoku in der gewünschten Größe (" + unitSize + ") nicht möglich!");
  }

  /**
   * @return A list of all candidates.
   */
  public Candidates<Literal> initializeCandidates() {
    final Candidates<Literal> candidates = new Candidates<Literal>();
    for (int i = 1; i <= getHouseSize(); i++) {
      candidates.add(Literal.getInstance(i));
    }
    return candidates;
  }

}
