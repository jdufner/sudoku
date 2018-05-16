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
package de.jdufner.sudoku.solver.strategy.simple;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.House;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * 
 */
public abstract class AbstractSimpleStrategy extends AbstractStrategy {

  private static final Logger LOG = Logger.getLogger(AbstractSimpleStrategy.class);

  public AbstractSimpleStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.SEHR_LEICHT;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return StrategyNameEnum.SIMPLE;
  }

  public void handleUnit(final House unit) {
    final Candidates<Literal> fixed = new Candidates<Literal>();
    fixed.addAll(unit.getFixedAsLiteral());
    for (Cell cell : unit.getNonFixed()) {
      if (cell.getCandidates().containsAtLeastOneOf(fixed)) {
        final Command cmd = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, cell).addCandidate(fixed)
            .build();
        if (LOG.isDebugEnabled()) {
          LOG.debug(cmd);
        }
        getCommands().add(cmd);
      }
    }
  }

}
