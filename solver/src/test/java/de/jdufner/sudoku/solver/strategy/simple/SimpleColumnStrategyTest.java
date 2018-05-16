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

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-03-08
 * @version $Revision$
 */
public final class SimpleColumnStrategyTest extends AbstractSimpleStrategyTestCase {

  @Override
  protected Strategy getStrategy() {
    return new SimpleColumnStrategy(sudoku);
  }

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 8).addCandidate(1, 3, 4, 7, 8, 9)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 2, 8).addCandidate(1, 3, 4, 7, 8, 9)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 1, 5).addCandidate(2, 3, 4, 5, 6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 3, 5).addCandidate(2, 3, 4, 5, 6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 5).addCandidate(2, 3, 4, 5, 6).build());
    return commands;
  }
}
