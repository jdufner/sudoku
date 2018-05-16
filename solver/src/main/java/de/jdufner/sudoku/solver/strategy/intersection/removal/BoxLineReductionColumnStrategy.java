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


package de.jdufner.sudoku.solver.strategy.intersection.removal;

import java.util.Collection;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Box;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Column;
import de.jdufner.sudoku.common.board.ColumnHandler;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Literal2CellMap;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public final class BoxLineReductionColumnStrategy extends AbstractBoxLineReductionStrategy implements ColumnHandler,
    Callable<Collection<Command>> {
  private static final Logger LOG = Logger.getLogger(BoxLineReductionColumnStrategy.class);

  public BoxLineReductionColumnStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachColumn(getSudoku(), this);
    return getCommands();
  }

  public void handleColumn(final Column column) {
    final Literal2CellMap literal2CellMapColumn = new Literal2CellMap(column.getCells());
    for (Literal testCandidate : column.getCandidates()) {
      if (literal2CellMapColumn.getCellsContainingLiteral(testCandidate).size() > 1
          && literal2CellMapColumn.getCellsContainingLiteral(testCandidate).size() <= getSudoku().getSize()
              .getBoxHeight()) {
        if (areCellsInSameBlock(literal2CellMapColumn.getCellsContainingLiteral(testCandidate))) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("Found candidate "
                + testCandidate
                + " in column "
                + getSudoku().getColumn(
                    literal2CellMapColumn.getCellsContainingLiteral(testCandidate).first().getColumnIndex())
                + " in block "
                + getSudoku().getBlock(
                    literal2CellMapColumn.getCellsContainingLiteral(testCandidate).first().getBlockIndex()) + " only");
          }
          removeCandidateInBlockExceptInColumn(testCandidate, getSudoku().getBlock(
              literal2CellMapColumn.getCellsContainingLiteral(testCandidate).first().getBlockIndex()), column);
        }
      }
    }
  }

  public Collection<Command> call() {
    return executeStrategy();
  }

  private void removeCandidateInBlockExceptInColumn(final Literal testCandidate, final Box block, final Column column) {
    for (Cell cell : block.getNonFixed()) {
      if (!getSudoku().getColumn(cell.getColumnIndex()).equals(column) && cell.getCandidates().contains(testCandidate)) {
        getCommands().add(
            new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, cell).addCandidate(testCandidate)
                .build());
      }
    }
  }

}
