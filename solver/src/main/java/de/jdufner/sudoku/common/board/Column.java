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

import java.util.List;

/**
 * A column represents a unit of cells in one column.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * @see <a
 *      href="http://sudopedia.org/wiki/Column">http://sudopedia.org/wiki/Column</a>
 */
public final class Column extends Line {

  public Column(final SudokuSize sudokuSize, final int index, final List<Cell> cells) {
    super(sudokuSize, index, cells);
  }

  /**
   * @return
   */
  public int[] getBlockIndexes() {
    final int startIndex = (index / sudokuSize.getBoxWidth()) * sudokuSize.getBoxWidth();
    int[] blockIndexes = new int[sudokuSize.getBoxWidth()];
    for (int i = 0; i < blockIndexes.length; i++) {
      blockIndexes[i] = startIndex + i;
    }
    return blockIndexes;
  }

  @Override
  public boolean equals(final Object other) { // NOPMD by Jürgen on 16.11.09
                                              // 00:23
    if (other instanceof Column) {
      return super.equals(other);
    }
    return false;
  }

  @Override
  public String toString() {
    return "Column " + super.toString();
  }

}
