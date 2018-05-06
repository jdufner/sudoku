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
package de.jdufner.sudoku.solver.strategy.intersection.removal;

import java.util.Collection;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Box;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Literal2CellMap;
import de.jdufner.sudoku.common.board.Row;
import de.jdufner.sudoku.common.board.RowHandler;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class BoxLineReductionRowStrategy extends AbstractBoxLineReductionStrategy implements RowHandler,
    Callable<Collection<Command>> {
  private static final Logger LOG = Logger.getLogger(BoxLineReductionRowStrategy.class);

  public BoxLineReductionRowStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachRow(getSudoku(), this);
    return getCommands();
  }

  public void handleRow(final Row row) {
    final Literal2CellMap literal2CellMapRow = new Literal2CellMap(row.getCells());
    for (Literal testCandidate : row.getCandidates()) {
      if (literal2CellMapRow.getCellsContainingLiteral(testCandidate).size() > 1
          && literal2CellMapRow.getCellsContainingLiteral(testCandidate).size() <= getSudoku().getSize()
              .getBoxWidth()) {
        if (areCellsInSameBlock(literal2CellMapRow.getCellsContainingLiteral(testCandidate))) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("Found candidate "
                + testCandidate
                + " in row "
                + getSudoku().getColumn(
                    literal2CellMapRow.getCellsContainingLiteral(testCandidate).first().getColumnIndex())
                + " in block "
                + getSudoku().getBlock(
                    literal2CellMapRow.getCellsContainingLiteral(testCandidate).first().getBlockIndex()) + " only");
          }
          removeCandidateInBlockExceptInRow(testCandidate, getSudoku().getBlock(
              literal2CellMapRow.getCellsContainingLiteral(testCandidate).first().getBlockIndex()), row);
        }
      }
    }
  }

  public Collection<Command> call() {
    return executeStrategy();
  }

  private void removeCandidateInBlockExceptInRow(final Literal testCandidate, final Box block, final Row row) {
    for (Cell cell : block.getNonFixed()) {
      if (!getSudoku().getRow(cell.getRowIndex()).equals(row) && cell.getCandidates().contains(testCandidate)) {
        getCommands().add(
            new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, cell).addCandidate(testCandidate)
                .build());
      }
    }
  }

}
