// $Id$
package de.jdufner.sudoku.solver.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.AbstractCommand;
import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.exceptions.SudokuRuntimeException;
import de.jdufner.sudoku.context.SolverServiceFactory;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.2
 * @version $Revision$
 */
public abstract class AbstractParallelStrategy extends AbstractStrategy {

  private static final Logger LOG = Logger.getLogger(AbstractParallelStrategy.class);

  private final Collection<Callable<Collection<Command>>> callables = new ArrayList<Callable<Collection<Command>>>(); // NOPMD by Jürgen on 08.11.09 21:30

  public AbstractParallelStrategy(final Grid sudoku) {
    super(sudoku);
  }

  protected Collection<Callable<Collection<Command>>> getCallables() {
    return callables;
  }

  /**
   * Sammelt die {@link AbstractCommand}-Objekte der parallel ausgeführten Strategien ein.
   */
  protected void gatherCommandsFromCallables() {
    try {
      final List<Future<Collection<Command>>> futures = SolverServiceFactory.INSTANCE.getExecutorService().invokeAll(
          callables);
      for (Future<Collection<Command>> future : futures) {
        getCommands().addAll(future.get());
      }
    } catch (InterruptedException ie) {
      LOG.error(ie);
      throw new SudokuRuntimeException(ie);
    } catch (ExecutionException ee) {
      LOG.error(ee);
      throw new RuntimeException(ee);
    }
  }

  @Override
  public Collection<Command> executeStrategy() {
    gatherCommandsFromCallables();
    return getCommands();
  }

}
