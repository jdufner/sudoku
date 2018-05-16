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
package de.jdufner.sudoku.solver.strategy.ywing;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

public final class YWingParallelStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.YWING, 0, 8).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.YWING, 8, 0).addCandidate(6).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new YWingParallelStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    return "(56),7,4,1,(568),9,(268),3,(268),1,9,3,(68),2,4,(68),5,7,2,(56),8,7,3,(56),9,1,4,7,(568),1,9,(568),3,4,2,(56),3,2,(56),4,1,7,(58),(68),9,4,(568),9,2,(568),(568),1,7,3,(568),4,2,(568),7,1,3,9,(568),9,3,(56),(58),4,2,7,(68),1,(568),1,7,3,9,(68),(2568),4,(2568)";
  }

}
