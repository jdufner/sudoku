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
import de.jdufner.sudoku.common.board.Literal;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-03-10
 * @version $Revision$
 */
public final class ShortStringWithCandidates implements CellHandler {

  private final static char CELL_SEPARATOR = ',';
  private final static char CANDIDATE_BEGIN = '(';
  private final static char CANDIDATE_END = ')';

  private transient StringBuilder stringBuilder = null;

  @Override
  public void initialize() {
    stringBuilder = new StringBuilder();
  }

  @Override
  public void handleCell(final Cell cell) {
    if (cell.getNumber() > 0) {
      stringBuilder.append(CELL_SEPARATOR);
    }
    if (cell.isFixed()) {
      stringBuilder.append(cell.getValue());
    } else {
      stringBuilder.append(CANDIDATE_BEGIN);
      for (Literal candidate : cell.getCandidates()) {
        stringBuilder.append(candidate);
      }
      stringBuilder.append(CANDIDATE_END);
    }
  }

  @Override
  public String toString() {
    return stringBuilder.toString();
  }

}
