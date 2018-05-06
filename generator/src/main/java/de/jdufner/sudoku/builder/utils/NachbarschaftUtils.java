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
package de.jdufner.sudoku.builder.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Grid;

/**
 * Stellt Funktionen zur Prüfung gleicher Nachbarschaften in einem Sudoku zur Verfügung.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-28
 * @version $Revision$
 */
public final class NachbarschaftUtils {

  private static final Logger LOG = Logger.getLogger(NachbarschaftUtils.class);

  /**
   * Prüft, ob in einem Sudoku eine Nachbarschaft mehr als 2-mal existiert.
   * 
   * @param sudoku
   *          Das zu prüfende Sudoku.
   * @return <code>true</code>, wenn eine Nachbarschaft maximal 2-mal im Sudoku existiert, sonst <code>false</code>.
   */
  public static boolean checkNachbarschaft(final Grid sudoku) {
    final int maxAllowedSimilarNeighbors = 6;
    return checkColumnNachbarschaft(sudoku, maxAllowedSimilarNeighbors)
        && checkRowNachbarschaft(sudoku, maxAllowedSimilarNeighbors);
  }

  /**
   * Prüft, ob in einem Sudoku eine Nachbarschaft in der gleichen Spalte im gleichen Block häufiger als erlaubt
   * existiert.
   * 
   * @param sudoku
   *          Das zu prüfende Sudoku.
   * @param maxAllowedSimilarNeighbors
   *          Die maximale Anzahl, wie häufig gleiche Nachbarn in Spalten in unterschiedlichen Blöcken existieren
   *          dürfen.
   * @return <code>true</code>, ein jede existierende Nachbarschaft maximal so häufig wie erlaubt existiert, sonst
   *         <code>false</code>.
   */
  private static boolean checkColumnNachbarschaft(final Grid sudoku, final int maxAllowedSimilarNeighbors) {
    Map<Nachbarschaft, Integer> column = new HashMap<Nachbarschaft, Integer>();
    for (Cell cell : sudoku.getCells()) {
      Nachbarschaft columnNachbarschaft = new Nachbarschaft(cell, getColumnNachbarschaft(sudoku, cell));
      LOG.debug(columnNachbarschaft);
      if (column.get(columnNachbarschaft) == null) {
        column.put(columnNachbarschaft, Integer.valueOf(1));
      } else {
        column.put(columnNachbarschaft, Integer.valueOf(column.get(columnNachbarschaft).intValue() + 1));
      }
    }
    LOG.debug(column);
    for (Integer similarNeighbors : column.values()) {
      if (similarNeighbors.intValue() > maxAllowedSimilarNeighbors) {
        return false;
      }
    }
    return true;
  }

  /**
   * Prüft, ob in einem Sudoku eine Nachbarschaft in der gleichen Zeile im gleichen Block häufiger als erlaubt
   * existiert.
   * 
   * @param sudoku
   *          Das zu prüfende Sudoku.
   * @param maxAllowedSimilarNeighbors
   *          Die maximale Anzahl, wie häufig gleiche Nachbarn in Zeilen in unterschiedlichen Blöcken existieren dürfen.
   * @return <code>true</code>, ein jede existierende Nachbarschaft maximal so häufig wie erlaubt existiert, sonst
   *         <code>false</code>.
   */
  private static boolean checkRowNachbarschaft(final Grid sudoku, final int maxAllowedSimilarNeighbors) {
    Map<Nachbarschaft, Integer> row = new HashMap<Nachbarschaft, Integer>();
    for (Cell cell : sudoku.getCells()) {
      Nachbarschaft rowNachbarschaft = new Nachbarschaft(cell, getRowNachbarschaft(sudoku, cell));
      LOG.debug(rowNachbarschaft);
      if (row.get(rowNachbarschaft) == null) {
        row.put(rowNachbarschaft, Integer.valueOf(1));
      } else {
        row.put(rowNachbarschaft, Integer.valueOf(row.get(rowNachbarschaft).intValue() + 1));
      }
    }
    LOG.debug(row);
    for (Integer similarNeighbors : row.values()) {
      if (similarNeighbors.intValue() > maxAllowedSimilarNeighbors) {
        return false;
      }
    }
    return true;
  }

  /**
   * Liefert zu einer Zelle aus dem Sudoku die Nachbarn in der gleichen Reihe im gleichen Block.
   * 
   * Beispiel: Sudoku und Zelle mit Wert 4 liefert 5 und 6.
   * 
   * <pre>
   *   123 ... ...
   *   456 ... ...
   *   789 ... ...
   *   
   *   ... ... ...
   * </pre>
   * 
   * @param sudoku
   *          Das Sudoku aus dem die Nachbarn in der gleichen Reihe im gleichen Block gesucht werden.
   * @param cell
   *          Die Zelle aus dem Sudoku.
   * @return Eine Sammlung von Nachbar-Zellen.
   */
  private static Collection<Cell> getRowNachbarschaft(final Grid sudoku, final Cell cell) {
    Collection<Cell> nachbarn = new HashSet<Cell>(sudoku.getSize().getHouseSize() - 1);
    int columnIndexIncrementer = 1;
    while (cell.getColumnIndex() + columnIndexIncrementer >= 0
        && cell.getColumnIndex() + columnIndexIncrementer < sudoku.getSize().getHouseSize()
        && cell.getBlockIndex() == sudoku.getCell(cell.getRowIndex(), cell.getColumnIndex() + columnIndexIncrementer)
            .getBlockIndex()) {
      nachbarn.add(sudoku.getCell(cell.getRowIndex(), cell.getColumnIndex() + columnIndexIncrementer));
      columnIndexIncrementer++;
    }
    int columnIndexDecrementer = 1;
    while (cell.getColumnIndex() - columnIndexDecrementer >= 0
        && cell.getColumnIndex() - columnIndexDecrementer < sudoku.getSize().getHouseSize()
        && cell.getBlockIndex() == sudoku.getCell(cell.getRowIndex(), cell.getColumnIndex() - columnIndexDecrementer)
            .getBlockIndex()) {
      nachbarn.add(sudoku.getCell(cell.getRowIndex(), cell.getColumnIndex() - columnIndexDecrementer));
      columnIndexDecrementer++;
    }
    return nachbarn;
  }

  /**
   * Liefert zu einer Zelle aus dem Sudoku die Nachbarn in der gleichen Spalte im gleichen Block.
   * 
   * Beispiel: Sudoku und Zelle mit Wert 4 liefert 1 und 7.
   * 
   * <pre>
   *   123 ... ...
   *   456 ... ...
   *   789 ... ...
   *   
   *   ... ... ...
   * </pre>
   * 
   * @param sudoku
   *          Das Sudoku aus dem die Nachbarn in der gleichen Spalte im gleichen Block gesucht werden.
   * @param cell
   *          Die Zelle aus dem Sudoku.
   * @return Eine Sammlung von Nachbar-Zellen.
   */
  private static Collection<Cell> getColumnNachbarschaft(final Grid sudoku, final Cell cell) {
    Collection<Cell> nachbarn = new HashSet<Cell>(sudoku.getSize().getHouseSize() - 1);
    int rowIndexIncrementer = 1;
    while (cell.getRowIndex() + rowIndexIncrementer >= 0
        && cell.getRowIndex() + rowIndexIncrementer < sudoku.getSize().getHouseSize()
        && cell.getBlockIndex() == sudoku.getCell(cell.getRowIndex() + rowIndexIncrementer, cell.getColumnIndex())
            .getBlockIndex()) {
      nachbarn.add(sudoku.getCell(cell.getRowIndex() + rowIndexIncrementer, cell.getColumnIndex()));
      rowIndexIncrementer++;
    }
    int rowIndexDecrementer = 1;
    while (cell.getRowIndex() - rowIndexDecrementer >= 0
        && cell.getRowIndex() - rowIndexDecrementer < sudoku.getSize().getHouseSize()
        && cell.getBlockIndex() == sudoku.getCell(cell.getRowIndex() - rowIndexDecrementer, cell.getColumnIndex())
            .getBlockIndex()) {
      nachbarn.add(sudoku.getCell(cell.getRowIndex() - rowIndexDecrementer, cell.getColumnIndex()));
      rowIndexDecrementer++;
    }
    return nachbarn;
  }

}
