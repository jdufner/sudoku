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
package de.jdufner.sudoku.solver.strategy.hidden;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RetainCandidatesCommand.RetainCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-03-15
 * @version $Revision$
 */
public final class HiddenTripleParallelStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands
        .add(new RetainCandidatesCommandBuilder(StrategyNameEnum.HIDDEN_TRIPLE, 0, 6).addCandidate(2, 5, 7).build());
    commands
        .add(new RetainCandidatesCommandBuilder(StrategyNameEnum.HIDDEN_TRIPLE, 0, 8).addCandidate(2, 5, 7).build());
    commands
        .add(new RetainCandidatesCommandBuilder(StrategyNameEnum.HIDDEN_TRIPLE, 1, 8).addCandidate(2, 5, 7).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new HiddenTripleParallelStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    return "(378),(12368),(1278),4,(35),9,(125678),(168),(25678),(379),(2369),(279),1,(35),8,4,(69),(25679),(489),(1489),5,2,6,7,(18),3,(89),(4589),(2489),6,3,(49),(25),(28),7,1,(34589),(123489),(12489),6,7,(125),(238),(489),(23489),(349),7,(1249),8,(49),(12),(236),5,(23469),6,(489),(4789),(59),(18),3,(1578),2,(4578),2,5,3,7,(18),6,9,(148),(48),1,(89),(789),(59),2,4,(35678),(68),(35678)";
  }

}
