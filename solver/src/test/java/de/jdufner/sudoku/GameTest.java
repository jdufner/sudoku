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
package de.jdufner.sudoku;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.SetValueCommand.SetValueCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class GameTest extends AbstractSolverTestCase {
  private static final Logger LOG = Logger.getLogger(GameTest.class);

  public void testGame() {
    int id = 1;
    Game game = new Game(id);
    assertEquals(id, game.getId());
    assertTrue(game.getQuest().isValid());
    assertFalse(game.getQuest().isSolved());
    assertTrue(game.getSolution().isValid());
    assertTrue(game.getSolution().isSolved());
    Command command = new SetValueCommandBuilder(null, 5, 6, 8).build();
    game.doCommand(command);
    assertTrue(game.isCorrect(5, 6));
    assertTrue(game.isUndoPossible());
    Cell cell = game.undo();
    if (LOG.isDebugEnabled()) {
      LOG.debug("Zug rückgängig gemacht: " + cell);
    }
    assertTrue(game.isRedoPossible());
    cell = game.redo();
    if (LOG.isDebugEnabled()) {
      LOG.debug("Zug wiederholt: " + cell);
    }
  }
}
