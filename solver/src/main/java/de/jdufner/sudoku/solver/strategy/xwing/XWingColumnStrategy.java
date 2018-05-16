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
import de.jdufner.sudoku.common.board.ColumnHandler;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Literal2CellMap;
import de.jdufner.sudoku.common.board.Row;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class XWingColumnStrategy extends AbstractStrategy implements ColumnHandler, Callable<Collection<Command>> {

  private static final Logger LOG = Logger.getLogger(XWingColumnStrategy.class);

  private transient List<List<XWingColumnCandidate>> xWingColumnCandidates;

  public XWingColumnStrategy(final Grid sudoku) {
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
    xWingColumnCandidates = new ArrayList<List<XWingColumnCandidate>>();
    HandlerUtil.forEachColumn(getSudoku(), this);
    eleminateXWingColumnCandidates(xWingColumnCandidates);
    return getCommands();
  }

  public void handleColumn(final Column column) {
    xWingColumnCandidates.add(getXWingColumnCandidates(column));
  }

  public Collection<Command> call() {
    return executeStrategy();
  }

  private void eleminateXWingColumnCandidates(final List<List<XWingColumnCandidate>> listOfXWingColumnCandidates) {
    for (int i = 0; i < listOfXWingColumnCandidates.size(); i++) {
      final List<XWingColumnCandidate> xWingCandidates = listOfXWingColumnCandidates.get(i);
      for (XWingColumnCandidate xWingColumnCandidate : xWingCandidates) {
        for (int j = i + 1; j < listOfXWingColumnCandidates.size(); j++) {
          final List<XWingColumnCandidate> xWingCandidatesComparator = listOfXWingColumnCandidates.get(j);
          for (XWingColumnCandidate xWingColumnCandidateComparator : xWingCandidatesComparator) {
            if (xWingColumnCandidate.equalsCandidateAndRows(xWingColumnCandidateComparator)) {
              LOG.debug("Column " + i + " and column " + j + " contain the XWing-Candidate "
                  + xWingColumnCandidate.candidate);
              final List<Column> columns = new ArrayList<Column>();
              columns.add(xWingColumnCandidate.column);
              columns.add(xWingColumnCandidateComparator.column);
              eleminateCandidatesInRowWithoutColumns(xWingColumnCandidate.candidate, xWingColumnCandidate.row1, columns);
              eleminateCandidatesInRowWithoutColumns(xWingColumnCandidate.candidate, xWingColumnCandidate.row2, columns);
            }
          }
        }
      }
    }
  }

  private void eleminateCandidatesInRowWithoutColumns(final Literal value, final Row row, final List<Column> columns) {
    for (Cell cell : row.getCells()) {
      if (!columns.contains(getSudoku().getColumn(cell.getColumnIndex())) && cell.getCandidates().contains(value)) {
        getCommands().add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, cell).addCandidate(value).build());
      }
    }
  }

  private List<XWingColumnCandidate> getXWingColumnCandidates(final Column column) {
    final List<XWingColumnCandidate> xWingCandidates = new ArrayList<XWingColumnCandidate>();
    final Literal2CellMap map = new Literal2CellMap(column.getNonFixed());
    for (Literal literal : map.getCandidatesByNumber(2)) {
      xWingCandidates.add(new XWingColumnCandidate(column, map.getCellsContainingLiteral(literal), literal));
    }
    return xWingCandidates;
  }

  private class XWingColumnCandidate {
    private final transient Column column;
    private final transient Row row1, row2;
    private final transient Literal candidate;

    private XWingColumnCandidate(final Column colum, final SortedSet<Cell> cells, final Literal candidate) {
      this.column = colum;
      assert cells.size() == 2 : "Die Liste muss genau zwei Zellen enhalten.";
      this.row1 = getSudoku().getRow(cells.first().getRowIndex());
      this.row2 = getSudoku().getRow(cells.last().getRowIndex());
      this.candidate = candidate;
    }

    private boolean equalsCandidateAndRows(final XWingColumnCandidate that) {
      if (this.candidate.equals(that.candidate)) {
        if (this.row1.equals(that.row1) && this.row2.equals(that.row2)) {
          return true;
        }
      }
      return false;
    }
  }

}
