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
package de.jdufner.sudoku.solver.strategy;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Grid;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since
 * @version $Revision$
 */
public abstract class AbstractSerialStrategy extends AbstractStrategy {

  //  private static final Logger LOG = Logger.getLogger(AbstractSerialStrategy.class);

  private final transient Collection<AbstractStrategy> strategies = new ArrayList<AbstractStrategy>();

  public AbstractSerialStrategy(final Grid sudoku) {
    super(sudoku);
  }

  protected Collection<AbstractStrategy> getStrategies() {
    return strategies;
  }

  protected void gatherCommandsFromStrategies() {
    for (AbstractStrategy strategy : strategies) {
      getCommands().addAll(strategy.executeStrategy());
    }
  }

  @Override
  public Collection<Command> executeStrategy() {
    gatherCommandsFromStrategies();
    return getCommands();
  }

}
