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
package de.jdufner.sudoku.common.cellhandlerimpls;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class ShortString implements CellHandler {

  private final transient SudokuSize groesse;
  private transient StringBuilder stringBuilder = null;

  public ShortString(final Grid sudoku) {
    groesse = sudoku.getSize();
  }

  @Override
  public void initialize() {
    stringBuilder = new StringBuilder();
  }

  @Override
  public void handleCell(final Cell cell) {
    if (groesse.equals(SudokuSize.DEFAULT)) {
      if (cell.getValue().getValue() == 0) {
        stringBuilder.append(".");
      } else {
        stringBuilder.append(cell.getValue().getValue());
      }
    } else {
      stringBuilder.append(cell.getValue().getValue());
      if (!(cell.getRowIndex() >= groesse.getHouseSize() - 1 && cell.getColumnIndex() >= groesse.getHouseSize() - 1)) {
        stringBuilder.append(',');
      }
    }
  }

  @Override
  public String toString() {
    return stringBuilder.toString();
  }

}
