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

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.commands.SetValueCommand.SetValueCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class CommandManagerTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(CommandManagerTest.class);

  private Grid sudoku;
  private CommandManager commandManager;

  @Override
  public void setUp() throws Exception {
    sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.ING_DIBA);
    commandManager = new CommandManager();
  }

  @Deprecated
  public void execute(Command command) {
    commandManager.doCommand(sudoku, command);
  }

  @Deprecated
  public void undo() {
    if (commandManager.isUndoPossible()) {
      commandManager.undoCommand(sudoku);
    }
  }

  @Deprecated
  public void redo() {
    if (commandManager.isRedoPossible()) {
      commandManager.redoCommand(sudoku);
    }
  }

  public void testSetValueCommand() {
    sudoku.resetAndClearCandidatesOfNonFixed();
    Cell cell1 = sudoku.getCell(1, 1);
    LOG.debug("1) " + cell1.toString());
    Command cmd1 = new SetValueCommandBuilder(null, cell1, Literal.getInstance(3)).build();
    execute(cmd1);
    LOG.debug("2) " + cell1.toString());
    undo();
    LOG.debug("3) " + cell1.toString());
    redo();
    LOG.debug("4) " + cell1.toString());
  }

  public void testRemoveCandidatesCommand() {
    sudoku.resetAndClearCandidatesOfNonFixed();
    Cell cell1 = sudoku.getCell(1, 1);
    LOG.debug("1) " + cell1.toString());
    Command cmd1 = new RemoveCandidatesCommandBuilder(null, cell1).addCandidate(1).build();
    execute(cmd1);
    LOG.debug("2) " + cell1.toString());
    undo();
    LOG.debug("3) " + cell1.toString());
    redo();
    LOG.debug("4) " + cell1.toString());
  }

}
