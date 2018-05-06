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
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Column;
import de.jdufner.sudoku.common.board.ColumnHandler;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-02-20
 * @version $Revision$
 */
public final class YWingColumnStrategy extends AbstractYWingStrategy implements ColumnHandler,
    Callable<Collection<Command>> {

  private static final Logger LOG = Logger.getLogger(YWingColumnStrategy.class);

  public YWingColumnStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  protected Collection<Command> executeStrategy() {
    HandlerUtil.forEachColumn(getSudoku(), this);
    return getCommands();
  }

  @Override
  public Level getLevel() {
    return Level.MITTEL;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return StrategyNameEnum.YWING;
  }

  @Override
  public void handleColumn(final Column column) {
    final Set<Cell> nonFixed = column.getNonFixed(2);
    // Es müssen mindestens zwei Zellen vorhanden sein
    if (nonFixed.size() < 2) {
      return;
    }
    for (Cell firstCell : nonFixed) {
      for (Cell secondCell : nonFixed) {
        if (firstCell.getRowIndex() < secondCell.getRowIndex()) {
          compareTwoCellsInColumn(firstCell, secondCell);
        }
      }
    }
  }

  @Override
  public Collection<Command> call() throws Exception {
    return executeStrategy();
  }

  /**
   * Prüft ob die zwei Zellen einer Spalte einen gemeinsamen Kandidaten haben.
   * 
   * Erwartet zwei Zellen mit jeweils nur zwei Kandidaten.
   * 
   * Identifiziere einen gemeinsamen Kandidaten und die jeweils
   * unterschiedlichen Kandidaten.
   * 
   * Suche Zelle mit den unterschiedlichen Kandidaten in den Reihe der
   * übergebenen Zellen.
   * 
   * @param firstCell
   * @param secondCell
   */
  private void compareTwoCellsInColumn(final Cell firstCell, final Cell secondCell) {
    final Literal commonCandidate = getCommonCandidate(firstCell, secondCell);
    if (commonCandidate == null) {
      return;
    }
    final Literal differentCandidateOfFirstCell = getDifferentCandidate(firstCell, commonCandidate);
    final Literal differentCandidateOfSecondCell = getDifferentCandidate(secondCell, commonCandidate);
    if (differentCandidateOfFirstCell.equals(differentCandidateOfSecondCell)) {
      return;
    }
    final Collection<Literal> searchCandidates = new ArrayList<Literal>();
    searchCandidates.add(differentCandidateOfFirstCell);
    searchCandidates.add(differentCandidateOfSecondCell);
    LOG.debug("Zwei Zellen in einer Spalte mit einem gemeinsamen Kandidaten "
        + "und jeweils einem unterschiedlichen Kandidaten gefunden: " + firstCell + " und " + secondCell);
    findInterceptCellInRow(firstCell, secondCell, searchCandidates);
    findInterceptCellInRow(secondCell, firstCell, searchCandidates);
    findInterceptCellInBlock(firstCell, secondCell, searchCandidates);
    findInterceptCellInBlock(secondCell, firstCell, searchCandidates);
  }

  /**
   * Suche Schnittpunkt zwischen den Zellen einer Spalte und der neuen Zelle
   * einer Reihe.
   * 
   * @param firstCell
   * @param secondCell
   * @param searchCandidates
   */
  private void findInterceptCellInRow(final Cell firstCell, final Cell secondCell,
      final Collection<Literal> searchCandidates) {
    final Collection<Cell> cells1 = findCellsByCandidatesInRow(searchCandidates, firstCell.getRowIndex());
    if (cells1 != null && !cells1.isEmpty()) {
      LOG.debug("In der Zeile einer gefundenen Zellen wurden folgenden Zellen mit den unterschiedlichen Kandidaten gefunden: "
          + cells1);
      for (Cell cell : cells1) {
        final Cell foundCell = getSudoku().getCell(secondCell.getRowIndex(), cell.getColumnIndex());
        LOG.debug(foundCell);
        final Literal removableCandidate = getCommonCandidate(cell, secondCell);
        LOG.debug("Entferne Kandidat: " + removableCandidate);
        if (!foundCell.isFixed() && foundCell.getCandidates().contains(removableCandidate)) {
          LOG.debug("Zelle gefunden: " + foundCell + " Erzeuge Kommando zum Entfernen von " + removableCandidate);
          LOG.debug("Gefunden in: " + getSudoku());
          getCommands().add(
              new RemoveCandidatesCommandBuilder(StrategyNameEnum.YWING, foundCell).addCandidate(removableCandidate)
                  .build());
        }
      }
    }
  }

  /**
   * @param firstCell
   * @param secondCell
   * @param searchCandidates
   */
  private void findInterceptCellInBlock(final Cell firstCell, final Cell secondCell,
      final Collection<Literal> searchCandidates) {
    if (firstCell.getBlockIndex() == secondCell.getBlockIndex()) {
      return;
    }
    final Collection<Cell> cells1 = findCellsByCandidatesInBlock(searchCandidates, firstCell.getBlockIndex());
    if (cells1 != null && !cells1.isEmpty()) {
      LOG.debug("In dem Block einer gefundenen Zellen wurden folgenden Zellen mit den unterschiedlichen Kandidaten gefunden: "
          + cells1);
      for (Cell cell : cells1) {
        if (cell.getColumnIndex() == firstCell.getColumnIndex()) {
          continue;
        }
        final Literal removableCandidate = getCommonCandidate(cell, secondCell);
        LOG.debug("Entferne Kandidat: " + removableCandidate);
        // Entferne in Block der firstCell in der Reihe der firstCell den
        // Kandidaten
        createRemoveCommands(firstCell, cell, removableCandidate);
        // Entferne in Block der secondCell in der Reihe der cell den Kandidaten
        createRemoveCommands(cell, secondCell, removableCandidate);
      }
    }
  }

  private void createRemoveCommands(final Cell firstCell, final Cell secondCell, final Literal removableCandidate) {
    final Collection<Cell> cells = getSudoku().getCellByColumnAndBlock(firstCell.getColumnIndex(),
        secondCell.getBlockIndex());
    for (Cell foundCell : cells) {
      if (!foundCell.isFixed() && foundCell.getCandidates().contains(removableCandidate)) {
        LOG.debug("Zelle gefunden: " + foundCell + " Erzeuge Kommando zum Entfernen von " + removableCandidate);
        LOG.debug("Gefunden in: " + getSudoku());
        getCommands().add(
            new RemoveCandidatesCommandBuilder(StrategyNameEnum.YWING, foundCell).addCandidate(removableCandidate)
                .build());
      }
    }
  }

}
