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
package de.jdufner.sudoku.solver.strategy.ywing;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.House;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-02-21
 * @version $Revision$
 * 
 */
public abstract class AbstractYWingStrategy extends AbstractStrategy {

  public AbstractYWingStrategy(final Grid sudoku) {
    super(sudoku);
  }

  private Collection<Cell> findCellsByCandidates(final Collection<Literal> searchCandidates, final House unit) {
    final Collection<Cell> foundCells = new ArrayList<Cell>();
    for (Cell cell : unit.getNonFixed(searchCandidates.size())) {
      if (cell.getCandidates().containsAll(searchCandidates)) {
        foundCells.add(cell);
      }
    }
    return foundCells;
  }

  protected Collection<Cell> findCellsByCandidatesInBlock(final Collection<Literal> searchCandidates,
      final int blockIndex) {
    return findCellsByCandidates(searchCandidates, getSudoku().getBlock(blockIndex));
  }

  protected Collection<Cell> findCellsByCandidatesInColumn(final Collection<Literal> searchCandidates,
      final int columnIndex) {
    return findCellsByCandidates(searchCandidates, getSudoku().getColumn(columnIndex));
  }

  protected Collection<Cell> findCellsByCandidatesInRow(final Collection<Literal> searchCandidates, final int rowIndex) {
    return findCellsByCandidates(searchCandidates, getSudoku().getRow(rowIndex));
  }

  protected Literal getCommonCandidate(final Cell firstCell, final Cell secondCell) {
    if (firstCell.getCandidates().containsAll(secondCell.getCandidates())) {
      // Wenn die Kandidatenpaare gleich sind
      return null;
    }
    for (Literal candidate : firstCell.getCandidates()) {
      if (secondCell.getCandidates().contains(candidate)) {
        return candidate;
      }
    }
    return null;
  }

  protected Literal getDifferentCandidate(final Cell cell, final Literal commonCandidate) {
    for (Literal candidate : cell.getCandidates()) {
      if (!candidate.equals(commonCandidate)) {
        return candidate;
      }
    }
    return null;
  }

}
