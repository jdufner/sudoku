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
 */
public final class HandlerUtil {

  private HandlerUtil() {
  }

  public static void forEachBlock(final Grid sudoku, final BoxHandler blockHandler) {
    for (Box block : sudoku.getBlocks()) {
      blockHandler.handleBlock(block);
    }
  }

  public static void forEachCell(final Grid sudoku, final CellHandler cellHandler) {
    cellHandler.initialize();
    for (Cell cell : sudoku.getCells()) {
      cellHandler.handleCell(cell);
    }
  }

  public static void forEachColumn(final Grid sudoku, final ColumnHandler columnHandler) {
    for (Column column : sudoku.getColumns()) {
      columnHandler.handleColumn(column);
    }
  }

  public static void forEachRow(final Grid sudoku, final RowHandler rowHandler) {
    for (Row row : sudoku.getRows()) {
      rowHandler.handleRow(row);
    }
  }

  public static void forEachUnit(final Grid sudoku, final UnitHandler unitHandler) {
    for (Box block : sudoku.getBlocks()) {
      unitHandler.handleUnit(block);
    }
    for (Column column : sudoku.getColumns()) {
      unitHandler.handleUnit(column);
    }
    for (Row row : sudoku.getRows()) {
      unitHandler.handleUnit(row);
    }
  }

}
