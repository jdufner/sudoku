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
package de.jdufner.sudoku.solver.strategy.backtracking;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.SetValueCommand.SetValueCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

public final class BacktrackingStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 3, 0, 2).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 4, 5, 2).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 5, 8, 2).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 6, 1, 2).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 7, 4, 2).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 8, 7, 2).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 0, 6, 4).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 1, 4, 4).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 4, 8, 4).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 7, 7, 4).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 8, 0, 4).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 0, 0, 5).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 1, 8, 5).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 2, 5, 5).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 3, 2, 5).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 0, 4, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 2, 7, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 3, 8, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 4, 1, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 6, 6, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 7, 0, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 8, 5, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 3, 1, 7).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 5, 6, 7).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 6, 8, 7).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 1, 6, 8).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 4, 7, 8).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 5, 1, 8).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 8, 8, 8).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 0, 8, 9).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 1, 5, 9).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 2, 2, 9).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 3, 7, 9).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 4, 4, 9).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 5, 0, 9).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new BacktrackingStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    //return "(2469),(246),3,(269),8,7,(2569),(569),1,(2689),5,(69),1,4,(2369),(23689),7,(369),7,(1268),(169),5,(269),(2369),4,(3689),(369),(34568),9,(456),7,(16),(1568),(356),(13456),2,1,(4678),2,(4689),3,(5689),(5679),(4569),(45679),(3456),(467),(4567),(2469),(1269),(12569),(35679),(134569),8,(2569),3,(15679),(269),(12679),4,(56789),(5689),(5679),(24569),(12467),8,(2369),(12679),(1269),(35679),(34569),(345679),(469),(467),(4679),(3689),5,(689),1,2,(34679)";
    return "(59),1,2,8,(46),3,(46),7,(59),7,3,6,2,(49),(59),(48),1,(58),8,4,(59),1,7,(56),2,(69),3,(256),(267),(59),4,3,8,1,(269),(267),1,(68),3,7,(29),(29),5,(468),(468),(29),(278),4,6,5,1,(78),3,(2789),3,(26),1,9,8,4,(67),5,(267),(246),9,8,5,(26),7,3,(246),1,(246),5,7,3,1,(26),9,(2468),(2468)";
  }

}
