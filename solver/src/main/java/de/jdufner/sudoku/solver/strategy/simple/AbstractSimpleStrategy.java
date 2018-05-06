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

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.House;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * 
 */
public abstract class AbstractSimpleStrategy extends AbstractStrategy {

  private static final Logger LOG = Logger.getLogger(AbstractSimpleStrategy.class);

  public AbstractSimpleStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.SEHR_LEICHT;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return StrategyNameEnum.SIMPLE;
  }

  public void handleUnit(final House unit) {
    final Candidates<Literal> fixed = new Candidates<Literal>();
    fixed.addAll(unit.getFixedAsLiteral());
    for (Cell cell : unit.getNonFixed()) {
      if (cell.getCandidates().containsAtLeastOneOf(fixed)) {
        final Command cmd = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, cell).addCandidate(fixed)
            .build();
        if (LOG.isDebugEnabled()) {
          LOG.debug(cmd);
        }
        getCommands().add(cmd);
      }
    }
  }

}
