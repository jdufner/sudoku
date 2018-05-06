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
