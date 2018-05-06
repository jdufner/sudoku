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
 * A block represents a unit of m x n cells. It spans about several columns and
 * rown and contains each literal one time.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * @see <a
 *      href="http://sudopedia.org/wiki/Box">http://sudopedia.org/wiki/Box</a>
 */
public final class Box extends House {

  /**
   * @param sudoku
   *          Die Größe des Sudokus.
   * @param index
   *          Der Index des Blocks.
   * @param cells
   *          Eine Liste aller Zellen, die in diesem Block enthalten sind.
   */
  public Box(final SudokuSize sudokuSize, final int index, final List<Cell> cells) {
    super(sudokuSize, index, cells);
  }

  /**
   * @return
   */
  public int[] getColumnIndexes() {
    final int startIndex = (index % sudokuSize.getBoxHeight()) + sudokuSize.getBoxWidth();
    int[] columnIndexes = new int[sudokuSize.getBoxWidth()];
    for (int i = 0; i < sudokuSize.getBoxWidth(); i++) {
      columnIndexes[i] = startIndex + i;
    }
    return columnIndexes;
  }

  /**
   * @return
   */
  public int[] getRowIndexes() {
    final int startIndex = (index / sudokuSize.getBoxHeight()) * sudokuSize.getBoxHeight();
    int[] rowIndexes = new int[sudokuSize.getBoxHeight()];
    for (int i = 0; i < sudokuSize.getBoxHeight(); i++) {
      rowIndexes[i] = startIndex + i;
    }
    return rowIndexes;
  }

  @Override
  public boolean equals(final Object other) { // NOPMD by Jürgen on 16.11.09
    // 00:23
    if (other instanceof Box) {
      return super.equals(other);
    }
    return false;
  }

  @Override
  public String toString() {
    return "Block " + super.toString();
  }

}
