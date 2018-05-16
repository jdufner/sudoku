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


package de.jdufner.sudoku.commands;

import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 14.03.2010
 * @version $Revision$
 */
public final class CommandFactoryTest extends AbstractSolverTestCase {

  public void testEquals1() {
    Command c1 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, new Cell(0, 0, Literal.getInstance(1),
        SudokuSize.DEFAULT)).addCandidate(1).build();
    Command c2 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, new Cell(0, 0, Literal.getInstance(1),
        SudokuSize.DEFAULT)).addCandidate(1).build();
    assertEquals("Muss gleich sein.", c1, c2);
    assertEquals("Muss gleich sein.", c1.hashCode(), c2.hashCode());
    assertNotSame("Darf nicht gleich sein.", c1, c2);
  }
}
