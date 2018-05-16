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


package de.jdufner.sudoku.solver.strategy.hidden;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.SetValueCommand.SetValueCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Literal2CellMap;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.House;
import de.jdufner.sudoku.common.board.UnitHandler;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Wird diese Klasse überhaupt benötigt? Dieser Fall tritt ein, wenn nur ein Kandidat, der in keiner anderen Zelle in
 * derselben Unit vorkommt, existiert.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class HiddenSingleStrategy extends AbstractStrategy implements UnitHandler {
  private static final Logger LOG = Logger.getLogger(HiddenSingleStrategy.class);

  public HiddenSingleStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.LEICHT;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return StrategyNameEnum.HIDDEN_SINGLE;
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachUnit(getSudoku(), this);
    return getCommands();
  }

  public void handleUnit(final House unit) {
    final Literal2CellMap literal2CellMap = new Literal2CellMap(unit.getCells());
    for (Literal single : literal2CellMap.getCandidatesByNumber(1)) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Found unique candidate " + single + " in Unit " + unit);
      }
      for (Cell cell : literal2CellMap.getCellsContainingLiteral(single)) {
        // TODO evtl andere Implementierungsvariante ausprobieren
        final List<Literal> fixed = new ArrayList<Literal>();
        fixed.addAll(getSudoku().getBlock(cell.getBlockIndex()).getFixedAsLiteral());
        fixed.addAll(getSudoku().getColumn(cell.getColumnIndex()).getFixedAsLiteral());
        fixed.addAll(getSudoku().getRow(cell.getRowIndex()).getFixedAsLiteral());
        if (!fixed.contains(single)) {
          getCommands().add(new SetValueCommandBuilder(StrategyNameEnum.HIDDEN_SINGLE, cell, single).build());
        }
      }
    }
  }

}
