// $Id$

/*
 * Gudoku (http://sourceforge.net/projects/gudoku)
 * Sudoku-Implementierung auf Basis des Google Webtoolkit
 * (http://code.google.com/webtoolkit/). Die Lösungsalgorithmen in Java laufen
 * parallel. Die Sudoku-Rätsel werden mittels JDBC in einer Datenbank
 * gespeichert.
 *
 * Copyright (C) 2008 Jürgen Dufner
 *
 * Dieses Programm ist freie Software. Sie können es unter den Bedingungen der
 * GNU General Public License, wie von der Free Software Foundation
 * veröffentlicht, weitergeben und/oder modifizieren, entweder gemäß Version 3
 * der Lizenz oder (nach Ihrer Option) jeder späteren Version.
 *
 * Die Veröffentlichung dieses Programms erfolgt in der Hoffnung, daß es Ihnen
 * von Nutzen sein wird, aber OHNE IRGENDEINE GARANTIE, sogar ohne die
 * implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FÜR EINEN
 * BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
 *
 * Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem
 * Programm erhalten haben. Falls nicht, siehe <http://www.gnu.org/licenses/>.
 *
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
