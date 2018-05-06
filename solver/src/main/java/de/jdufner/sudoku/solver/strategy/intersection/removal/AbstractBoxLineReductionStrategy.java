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

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Box;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractBoxLineReductionStrategy extends AbstractStrategy {

  private static final Logger LOG = Logger.getLogger(AbstractBoxLineReductionStrategy.class);

  protected AbstractBoxLineReductionStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.MITTEL;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return StrategyNameEnum.INTERSECTION_REMOVAL;
  }

  /**
   * Funktioniert schon richtig. Wenn zwei Zellen paarweise verglichen werden und der Block unterschiedlich ist, wird
   * <code>false</code> zurückgeliefert. Ist dier Block gleich wird der nächste Block mit dem ersten verglichen. Bis
   * hierher wurde dann schon geprüft, dass die bisherigen Blöcke gleich sind.
   * 
   * @param cells
   * @return
   */
  protected boolean areCellsInSameBlock(final Collection<Cell> cells) {
    Box block = null;
    for (Cell cell : cells) {
      if (block == null) {
        block = getSudoku().getBlock(cell.getBlockIndex());
      }
      if (!block.equals(getSudoku().getBlock(cell.getBlockIndex()))) {
        return false;
      }
    }
    return true;
  }

}
