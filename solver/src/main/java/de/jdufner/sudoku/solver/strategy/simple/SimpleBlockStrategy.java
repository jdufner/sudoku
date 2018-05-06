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
package de.jdufner.sudoku.solver.strategy.simple;

import java.util.Collection;
import java.util.concurrent.Callable;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Box;
import de.jdufner.sudoku.common.board.BoxHandler;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.exceptions.SudokuRuntimeException;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SimpleBlockStrategy extends AbstractSimpleStrategy implements BoxHandler,
    Callable<Collection<Command>> {

  // private static final Logger LOG = Logger.getLogger(SimpleBlockStrategy.class);

  public SimpleBlockStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Collection<Command> executeStrategy() {
    String exceptionInParallelTask = System.getProperty("test.solver.exceptionInParallelTask");
    if (exceptionInParallelTask != null && exceptionInParallelTask.equalsIgnoreCase("true")) {
      throw new SudokuRuntimeException("Systemproperty 'test.solver.exceptionInParallelTask' hat den Wert '"
          + exceptionInParallelTask + "', deshalb wird diese Exception geworfen.");
    }
    HandlerUtil.forEachBlock(getSudoku(), this);
    return getCommands();
  }

  public void handleBlock(final Box block) {
    handleUnit(block);
  }

  public Collection<Command> call() {
    return executeStrategy();
  }

}
