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

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RetainCandidatesCommand.RetainCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;

public final class HiddenColumnStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands.add(new RetainCandidatesCommandBuilder(null, 3, 1).addCandidate(6, 8).build());
    commands.add(new RetainCandidatesCommandBuilder(null, 6, 1).addCandidate(6, 8).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    final HiddenColumnStrategy hiddenColumnStrategy = new HiddenColumnStrategy(sudoku);
    hiddenColumnStrategy.setSize(2);
    return hiddenColumnStrategy;
  }

  @Override
  protected String getSudokuAsString() {
    return "(57),(57),8,(1349),(1349),6,(34),2,(1349),2,3,6,(1489),(1479),(4789),(47),5,(1479),1,9,4,5,(237),(27),8,6,(37),(5678),(5678),(57),(2489),(245679),3,1,(479),(4789),(678),1,3,(489),(4679),(4789),5,(479),2,9,4,2,(18),(157),(578),6,(37),(378),(45678),(25678),(57),(234),(2345),(245),9,1,(347),3,(27),1,6,(249),(249),(247),8,5,(45),(25),9,7,8,1,(234),(34),6";
  }

}
