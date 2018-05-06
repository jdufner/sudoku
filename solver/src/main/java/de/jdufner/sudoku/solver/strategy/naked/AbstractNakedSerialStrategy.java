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
package de.jdufner.sudoku.solver.strategy.naked;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractSerialStrategy;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractNakedSerialStrategy extends AbstractSerialStrategy implements Naked {

  //  private static final Logger LOG = Logger.getLogger(AbstractSerialStrategy.class);

  protected AbstractNakedSerialStrategy(final Grid sudoku) {
    super(sudoku);
    final NakedBlockStrategy nakedBlockStrategy = new NakedBlockStrategy(sudoku);
    nakedBlockStrategy.setSize(getSize());
    nakedBlockStrategy.setStrategyNameEnum(getStrategyName());
    getStrategies().add(nakedBlockStrategy);
    final NakedColumnStrategy nakedColumnStrategy = new NakedColumnStrategy(sudoku);
    nakedColumnStrategy.setSize(getSize());
    nakedColumnStrategy.setStrategyNameEnum(getStrategyName());
    getStrategies().add(nakedColumnStrategy);
    final NakedRowStrategy nakedRowStrategy = new NakedRowStrategy(sudoku);
    nakedRowStrategy.setSize(getSize());
    nakedRowStrategy.setStrategyNameEnum(getStrategyName());
    getStrategies().add(nakedRowStrategy);
  }

  @Override
  public Level getLevel() {
    return Level.LEICHT;
  }
}
