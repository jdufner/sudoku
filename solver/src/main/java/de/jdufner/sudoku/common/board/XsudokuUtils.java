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

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-01-15
 * @version $Revision$
 */
public final class XsudokuUtils {

  private XsudokuUtils() {
  }

  public static MainDiagonal buildMainDiagonal(final Grid sudoku) {
    final List<Cell> cells = new ArrayList<Cell>();
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      cells.add(sudoku.getCell(i, sudoku.getSize().getHouseSize() - 1 - i));
    }
    return new MainDiagonal(sudoku.getSize(), 0, cells);
  }

  public static SecondaryDiagonal buildSecondaryDiagonal(final Grid sudoku) {
    final List<Cell> cells = new ArrayList<Cell>();
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      cells.add(sudoku.getCell(i, i));
    }
    return new SecondaryDiagonal(sudoku.getSize(), 0, cells);
  }

  public static boolean isXsudoku(final Grid sudoku) {
    final MainDiagonal mainDiagonal = buildMainDiagonal(sudoku);
    if (!mainDiagonal.isSolved() || !mainDiagonal.isValid()) {
      return false;
    }
    final SecondaryDiagonal secondaryDiagonal = buildSecondaryDiagonal(sudoku);
    if (!secondaryDiagonal.isSolved() || !secondaryDiagonal.isValid()) {
      return false;
    }
    return true;
  }
}
