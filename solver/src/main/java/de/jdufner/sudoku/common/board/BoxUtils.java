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

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * @see Box
 */
public final class BoxUtils {

  private BoxUtils() {
  }

  public static int[] getColumnsByBlock(final int block, final SudokuSize sudokuSize) {
    int[] columns = new int[sudokuSize.getBoxWidth()];
    for (int i = 0; i < sudokuSize.getBoxWidth(); i++) {
      columns[i] = block * sudokuSize.getBoxWidth() + i;
    }
    return columns;
  }

  /**
   * Liefert den Blockindex zu einer Zelle (genauer Zeilen- und Spaltenindex) in
   * Abhängigkeit der Größe zurück.
   * 
   * @param rowIndex
   * @param columnIndex
   * @param sudokuSize
   * @return
   */
  public static int getBlockIndexByRowIndexAndColumnIndex(final int rowIndex, final int columnIndex,
      final SudokuSize sudokuSize) {
    return (rowIndex / sudokuSize.getBoxHeight()) * sudokuSize.getBoxHeight()
        + (columnIndex / sudokuSize.getBoxWidth());
  }

  public static boolean isFirstColumnInBlock(final int columnIndex, final SudokuSize sudokuSize) {
    if (columnIndex % sudokuSize.getBoxWidth() == 0) {
      return true;
    }
    return false;
  }

  public static boolean isFirstRowInBlock(final int rowIndex, final SudokuSize sudokuSize) {
    if (rowIndex % sudokuSize.getBoxHeight() == 0) {
      return true;
    }
    return false;
  }

  public static boolean isLastColumnInRow(final int columnIndex, final SudokuSize sudokuSize) {
    if (columnIndex == sudokuSize.getHouseSize() - 1) {
      return true;
    }
    return false;
  }

  public static boolean isLastRowInColumn(final int rowIndex, final SudokuSize sudokuSize) {
    if (rowIndex == sudokuSize.getHouseSize() - 1) {
      return true;
    }
    return false;
  }

}
