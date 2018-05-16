/*
 * Sudoku is a puzzle game. It solves and generates puzzles in different
 * formats.
 * Copyright (C) 2008-2018  Juergen Dufner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

// $Id$
package de.jdufner.sudoku.solver.strategy.xwing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Column;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Literal2CellMap;
import de.jdufner.sudoku.common.board.Row;
import de.jdufner.sudoku.common.board.RowHandler;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class XWingRowStrategy extends AbstractStrategy implements RowHandler, Callable<Collection<Command>> {

  private static final Logger LOG = Logger.getLogger(XWingRowStrategy.class);

  private transient List<List<XWingRowCandidate>> xWingRowCandidates;

  public XWingRowStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.SCHWER;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return StrategyNameEnum.XWING;
  }

  @Override
  public Collection<Command> executeStrategy() {
    xWingRowCandidates = new ArrayList<List<XWingRowCandidate>>();
    HandlerUtil.forEachRow(getSudoku(), this);
    eleminateXWingRowCandidates(xWingRowCandidates);
    return getCommands();
  }

  public void handleRow(final Row row) {
    xWingRowCandidates.add(getXWingRowCandidates(row));
  }

  public Collection<Command> call() {
    return executeStrategy();
  }

  private void eleminateXWingRowCandidates(final List<List<XWingRowCandidate>> listOfXWingRowCandidates) {
    for (int i = 0; i < listOfXWingRowCandidates.size(); i++) {
      final List<XWingRowCandidate> xWingCandidates = listOfXWingRowCandidates.get(i);
      for (XWingRowCandidate xWingRowCandidate : xWingCandidates) {
        for (int j = i + 1; j < listOfXWingRowCandidates.size(); j++) {
          final List<XWingRowCandidate> xWingCandidatesComparator = listOfXWingRowCandidates.get(j);
          for (XWingRowCandidate xWingRowCandidateComparator : xWingCandidatesComparator) {
            if (xWingRowCandidate.equalsCandidateAndColumns(xWingRowCandidateComparator)) {
              LOG.debug("Row " + i + " and row " + j + " are containing the XWing-Candidate "
                  + xWingRowCandidate.candidate);
              final List<Row> rows = new ArrayList<Row>();
              rows.add(xWingRowCandidate.row);
              rows.add(xWingRowCandidateComparator.row);
              eleminateCandidatesInColumnWithoutRows(xWingRowCandidate.candidate, xWingRowCandidate.column1, rows);
              eleminateCandidatesInColumnWithoutRows(xWingRowCandidate.candidate, xWingRowCandidate.column2, rows);
            }
          }
        }
      }
    }
  }

  private void eleminateCandidatesInColumnWithoutRows(final Literal value, final Column column, final List<Row> rows) {
    for (Cell cell : column.getCells()) {
      if (!rows.contains(getSudoku().getRow(cell.getRowIndex())) && cell.getCandidates().contains(value)) {
        getCommands().add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, cell).addCandidate(value).build());
      }
    }
  }

  private List<XWingRowCandidate> getXWingRowCandidates(final Row row) {
    final List<XWingRowCandidate> xWingCandidates = new ArrayList<XWingRowCandidate>();
    final Literal2CellMap map = new Literal2CellMap(row.getNonFixed());
    for (Literal literal : map.getCandidatesByNumber(2)) {
      xWingCandidates.add(new XWingRowCandidate(row, map.getCellsContainingLiteral(literal), literal));
    }
    return xWingCandidates;
  }

  private class XWingRowCandidate {
    private final transient Row row;
    private final transient Column column1, column2;
    private final transient Literal candidate;

    private XWingRowCandidate(final Row row, final SortedSet<Cell> cells, final Literal candidate) {
      this.row = row;
      assert cells.size() == 2 : "Die Liste muss genau zwei Zellen enhalten.";
      this.column1 = getSudoku().getColumn(cells.first().getColumnIndex());
      this.column2 = getSudoku().getColumn(cells.last().getColumnIndex());
      this.candidate = candidate;
    }

    private boolean equalsCandidateAndColumns(final XWingRowCandidate that) {
      if (this.candidate.equals(that.candidate)) {
        if (this.column1.equals(that.column1) && this.column2.equals(that.column2)) {
          return true;
        }
      }
      return false;
    }
  }

}
