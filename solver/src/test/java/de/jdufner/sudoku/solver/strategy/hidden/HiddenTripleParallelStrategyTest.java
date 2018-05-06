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
package de.jdufner.sudoku.solver.strategy.hidden;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RetainCandidatesCommand.RetainCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2010-03-15
 * @version $Revision$
 */
public final class HiddenTripleParallelStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands
        .add(new RetainCandidatesCommandBuilder(StrategyNameEnum.HIDDEN_TRIPLE, 0, 6).addCandidate(2, 5, 7).build());
    commands
        .add(new RetainCandidatesCommandBuilder(StrategyNameEnum.HIDDEN_TRIPLE, 0, 8).addCandidate(2, 5, 7).build());
    commands
        .add(new RetainCandidatesCommandBuilder(StrategyNameEnum.HIDDEN_TRIPLE, 1, 8).addCandidate(2, 5, 7).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new HiddenTripleParallelStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    return "(378),(12368),(1278),4,(35),9,(125678),(168),(25678),(379),(2369),(279),1,(35),8,4,(69),(25679),(489),(1489),5,2,6,7,(18),3,(89),(4589),(2489),6,3,(49),(25),(28),7,1,(34589),(123489),(12489),6,7,(125),(238),(489),(23489),(349),7,(1249),8,(49),(12),(236),5,(23469),6,(489),(4789),(59),(18),3,(1578),2,(4578),2,5,3,7,(18),6,9,(148),(48),1,(89),(789),(59),2,4,(35678),(68),(35678)";
  }

}
