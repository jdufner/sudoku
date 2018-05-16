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
package de.jdufner.sudoku.solver.strategy.intersection.removal;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

public final class IntersectionRemovalSeriallStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 7).addCandidate(2)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 5).addCandidate(3)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 7, 4).addCandidate(3)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 7, 5).addCandidate(3)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 2, 6).addCandidate(4)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 3, 5).addCandidate(6)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 4, 3).addCandidate(6)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 4, 5).addCandidate(6)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 5, 5).addCandidate(6)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 5).addCandidate(6)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 0).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 1).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 2).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 5).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 7, 0).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 7, 1).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 7, 2).addCandidate(8)
        .build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new IntersectionRemovalSerialStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    return "(368),7,(358),4,9,(36),2,(18),(15),(2469),(2569),(2459),1,8,(67),3,(79),(4579),1,(89),(3489),2,(37),5,(4789),6,(479),5,4,(129),8,(1367),(13679),(179),(1279),(36),(69),3,(19),(679),2,(14679),(1479),5,8,7,(12689),(1289),5,(1346),(13469),(149),(129),(36),(23489),(1289),(1234789),(3679),5,(1346789),(1789),(12789),(1279),(2389),(12589),(1235789),(379),(137),(13789),6,4,(1279),(489),(189),6,(79),(147),2,5,3,(179)";
  }

}
