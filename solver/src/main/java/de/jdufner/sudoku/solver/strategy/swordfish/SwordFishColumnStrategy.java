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
import de.jdufner.sudoku.common.board.ColumnHandler;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Kombination;
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
 * 
 */
public final class SwordFishColumnStrategy extends AbstractStrategy implements ColumnHandler,
    Callable<Collection<Command>> {

  private static final Logger LOG = Logger.getLogger(SwordFishColumnStrategy.class);

  private final transient Map<Literal, List<SwordFishColumnCandidate>> literal2SwordFishColumnCandidates = new HashMap<Literal, List<SwordFishColumnCandidate>>();

  public SwordFishColumnStrategy(final Grid sudoku) {
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
    HandlerUtil.forEachColumn(getSudoku(), this);
    for (Literal literal : literal2SwordFishColumnCandidates.keySet()) {
      if (literal2SwordFishColumnCandidates.get(literal).size() >= 3) {
        checkIfCandidatesInStairCase(literal);
      }
    }
    return getCommands();
  }

  private void checkIfCandidatesInStairCase(final Literal literal) {
    if (LOG.isDebugEnabled()) {
      for (SwordFishColumnCandidate swordFishColumnCandidate : literal2SwordFishColumnCandidates.get(literal)) {
        LOG.debug(swordFishColumnCandidate.column + " contains literal " + literal + " in "
            + swordFishColumnCandidate.cells);
      }
    }
    final Kombination<SwordFishColumnCandidate> kombination = new Kombination<SwordFishColumnCandidate>(
        literal2SwordFishColumnCandidates.get(literal), 3);
    while (kombination.hasNextKombination()) {
      kombination.buildNextKombination();
      final Collection<SwordFishColumnCandidate> swordFishColumnCandidates = kombination.getKombination();
      if (LOG.isDebugEnabled()) {
        LOG.debug(swordFishColumnCandidates);
      }
      final Collection<Row> rows = getRows(swordFishColumnCandidates);
      if (rows.size() == 3) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Rows " + rows + " in stair case.");
        }
        removeLiteralInRowExceptInSwordFishColumnCandidate(literal, swordFishColumnCandidates, rows);
      }
    }
  }

  private void removeLiteralInRowExceptInSwordFishColumnCandidate(final Literal literal,
      final Collection<SwordFishColumnCandidate> swordFishColumnCandidates, final Collection<Row> rows) {
    for (Row row : rows) {
      final Collection<Cell> cornerCells = getCellsByRow(swordFishColumnCandidates, row);
      for (Cell cell : row.getCells()) {
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

  public void handleColumn(final Column column) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Handle " + column);
    }
    getSwordFishCandidates(column);
  }

  public Collection<Command> call() {
    return executeStrategy();
  }

  private List<SwordFishColumnCandidate> getSwordFishCandidates(final Column column) {
    final List<SwordFishColumnCandidate> columnCandidates = new ArrayList<SwordFishColumnCandidate>();
    final Literal2CellMap map = new Literal2CellMap(column.getCells());
    for (Literal literal : map.getCandidatesByNumber(2)) {
      final SwordFishColumnCandidate swordFishColumnCandidate = new SwordFishColumnCandidate(column, map
          .getCellsContainingLiteral(literal), literal);
      addSwordFishColumnCandidateByLiteral(literal, swordFishColumnCandidate);
    }
    return columnCandidates;
  }

  private void addSwordFishColumnCandidateByLiteral(final Literal literal,
      final SwordFishColumnCandidate swordFishColumnCandidate) {
    List<SwordFishColumnCandidate> swordFishColumnCandidates = null;
    if (literal2SwordFishColumnCandidates.get(literal) == null) {
      swordFishColumnCandidates = new ArrayList<SwordFishColumnCandidate>();
      literal2SwordFishColumnCandidates.put(literal, swordFishColumnCandidates);
    } else {
      swordFishColumnCandidates = literal2SwordFishColumnCandidates.get(literal);
    }
    swordFishColumnCandidates.add(swordFishColumnCandidate);
  }

  private Collection<Row> getRows(final Collection<SwordFishColumnCandidate> swordFishColumnCandidates) {
    final SortedSet<Row> rows = new TreeSet<Row>();
    for (SwordFishColumnCandidate swordFishColumnCandidate : swordFishColumnCandidates) {
      for (Cell cell : swordFishColumnCandidate.cells) {
        rows.add(getSudoku().getRow(cell.getRowIndex()));
      }
    }
    return rows;
  }

  private Collection<Cell> getCellsByRow(final Collection<SwordFishColumnCandidate> swordFishColumnCandidates,
      final Row row) {
    final SortedSet<Cell> cells = new TreeSet<Cell>();
    for (SwordFishColumnCandidate swordFishColumnCandidate : swordFishColumnCandidates) {
      for (Cell cell : swordFishColumnCandidate.cells) {
        if (getSudoku().getRow(cell.getRowIndex()).equals(row)) {
          cells.add(cell);
        }
      }
    }
    return cells;
  }

  private class SwordFishColumnCandidate implements Comparable<SwordFishColumnCandidate> {
    private final transient Column column;
    private final transient SortedSet<Cell> cells;
    private final transient Literal literal;

    private SwordFishColumnCandidate(final Column column, final SortedSet<Cell> cells, final Literal literal) {
      this.column = column;
      assert cells.size() == 2 : "Die Liste muss genau zwei Zellen enthalten.";
      this.cells = cells;
      this.literal = literal;
    }

    public int compareTo(final SwordFishColumnCandidate other) {
      return column.getIndex() - other.column.getIndex();
    }

    @Override
    public String toString() {
      return column + " contains literal " + literal + " in " + cells;
    }
  }

}
