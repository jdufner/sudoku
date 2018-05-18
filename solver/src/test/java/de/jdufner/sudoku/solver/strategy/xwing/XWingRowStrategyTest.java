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

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

public final class XWingRowStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    Collection<Command> commands = new ArrayList<Command>();
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 2, 4).addCandidate(9).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 6, 4).addCandidate(9).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 1, 8).addCandidate(9).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 2, 8).addCandidate(9).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 6, 8).addCandidate(9).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new XWingRowStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    return "2,8,3,4,(69),1,7,5,(69),1,(69),5,(239),8,7,(26),4,(369),7,(69),4,(2359),(3569),(3569),(1268),(689),(13689),4,1,(79),(589),2,(59),(568),3,(5678),6,2,(79),(3589),1,(359),4,(78),(578),3,5,8,6,7,4,9,1,2,8,3,1,(59),(4569),2,(56),(79),(479),5,7,6,1,(49),8,3,2,(49),9,4,2,7,(356),(356),(1568),(68),(1568)";
  }

}
