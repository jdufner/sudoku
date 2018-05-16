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


package de.jdufner.sudoku.solver.strategy.swordfish;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Column;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Kombination;
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
public final class SwordFishRowStrategy extends AbstractStrategy implements RowHandler, Callable<Collection<Command>> {

  private static final Logger LOG = Logger.getLogger(SwordFishRowStrategy.class);

  private final transient Map<Literal, List<SwordFishRowCandidate>> literal2SwordFishRowCandidates = new HashMap<Literal, List<SwordFishRowCandidate>>();

  public SwordFishRowStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.SCHWER;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return StrategyNameEnum.SWORDFISH;
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachRow(getSudoku(), this);
    for (Literal literal : literal2SwordFishRowCandidates.keySet()) {
      if (literal2SwordFishRowCandidates.get(literal).size() >= 3) {
        checkIfCandidatesInStairCase(literal);
      }
    }
    return getCommands();
  }

  private void checkIfCandidatesInStairCase(final Literal literal) {
    if (LOG.isDebugEnabled()) {
      for (SwordFishRowCandidate swordFishRowCandidate : literal2SwordFishRowCandidates.get(literal)) {
        LOG.debug(swordFishRowCandidate.row + " contains literal " + literal + " in " + swordFishRowCandidate.cells);
      }
    }
    final Kombination<SwordFishRowCandidate> kombination = new Kombination<SwordFishRowCandidate>(
        literal2SwordFishRowCandidates.get(literal), 3);
    while (kombination.hasNextKombination()) {
      kombination.buildNextKombination();
      final Collection<SwordFishRowCandidate> swordFishRowCandidates = kombination.getKombination();
      if (LOG.isDebugEnabled()) {
        LOG.debug(swordFishRowCandidates);
      }
      final Collection<Column> columns = getColumns(swordFishRowCandidates);
      if (columns.size() == 3) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Columns " + columns + " in stair case.");
        }
        removeLiteralInColumnExceptInSwordFishRowCandidate(literal, swordFishRowCandidates, columns);
      }
    }
  }

  private void removeLiteralInColumnExceptInSwordFishRowCandidate(final Literal literal,
      final Collection<SwordFishRowCandidate> swordFishRowCandidates, final Collection<Column> columns) {
    for (Column column : columns) {
      final Collection<Cell> cornerCells = getCellsByColumn(swordFishRowCandidates, column);
      for (Cell cell : column.getCells()) {
        if (!cell.isFixed() && !cornerCells.contains(cell)) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("Create new Remove-Command!");
          }
          getCommands().add(
              new RemoveCandidatesCommandBuilder(StrategyNameEnum.SWORDFISH, cell).addCandidate(literal).build());
        }
      }
    }
  }

  public void handleRow(final Row row) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Handle " + row);
    }
    getSwordFishCandidates(row);
  }

  public Collection<Command> call() {
    return executeStrategy();
  }

  private List<SwordFishRowCandidate> getSwordFishCandidates(final Row row) {
    final List<SwordFishRowCandidate> rowCandidates = new ArrayList<SwordFishRowCandidate>();
    final Literal2CellMap map = new Literal2CellMap(row.getCells());
    for (Literal literal : map.getCandidatesByNumber(2)) {
      final SwordFishRowCandidate swordFishRowCandidate = new SwordFishRowCandidate(row, map
          .getCellsContainingLiteral(literal), literal);
      addSwordFishRowCandidateByLiteral(literal, swordFishRowCandidate);
    }
    return rowCandidates;
  }

  private void addSwordFishRowCandidateByLiteral(final Literal literal,
      final SwordFishRowCandidate swordFishRowCandidate) {
    List<SwordFishRowCandidate> swordFishRowCandidates = null;
    if (literal2SwordFishRowCandidates.get(literal) == null) {
      swordFishRowCandidates = new ArrayList<SwordFishRowCandidate>();
      literal2SwordFishRowCandidates.put(literal, swordFishRowCandidates);
    } else {
      swordFishRowCandidates = literal2SwordFishRowCandidates.get(literal);
    }
    swordFishRowCandidates.add(swordFishRowCandidate);
  }

  private Collection<Column> getColumns(final Collection<SwordFishRowCandidate> swordFishRowCandidates) {
    final SortedSet<Column> columns = new TreeSet<Column>();
    for (SwordFishRowCandidate swordFishRowCandidate : swordFishRowCandidates) {
      for (Cell cell : swordFishRowCandidate.cells) {
        columns.add(getSudoku().getColumn(cell.getColumnIndex()));
      }
    }
    return columns;
  }

  private Collection<Cell> getCellsByColumn(final Collection<SwordFishRowCandidate> swordFishColumnCandidates,
      final Column column) {
    final SortedSet<Cell> cells = new TreeSet<Cell>();
    for (SwordFishRowCandidate swordFishRowCandidate : swordFishColumnCandidates) {
      for (Cell cell : swordFishRowCandidate.cells) {
        if (getSudoku().getColumn(cell.getColumnIndex()).equals(column)) {
          cells.add(cell);
        }
      }
    }
    return cells;
  }

  private class SwordFishRowCandidate implements Comparable<SwordFishRowCandidate> {
    private final transient Row row;
    private final transient SortedSet<Cell> cells;
    private final transient Literal literal;

    private SwordFishRowCandidate(final Row row, final SortedSet<Cell> cells, final Literal literal) {
      this.row = row;
      assert cells.size() == 2 : "Die Liste muss genau zwei Zellen enthalten.";
      this.cells = cells;
      this.literal = literal;
    }

    public int compareTo(final SwordFishRowCandidate other) {
      return row.getIndex() - other.row.getIndex();
    }

    @Override
    public String toString() {
      return row + " contains literal " + literal + " in " + cells;
    }
  }

}
