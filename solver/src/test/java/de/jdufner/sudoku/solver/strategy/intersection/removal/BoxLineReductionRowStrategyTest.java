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

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

public final class BoxLineReductionRowStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 0).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 1).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 2).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 7, 0).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 7, 1).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 7, 2).addCandidate(8)
        .build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new BoxLineReductionRowStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    return "(368),7,(358),4,9,(36),2,(18),(15),(2469),(2569),(2459),1,8,(67),3,(79),(4579),1,(89),(3489),2,(37),5,(4789),6,(479),5,4,(129),8,(1367),(13679),(179),(1279),(36),(69),3,(19),(679),2,(14679),(1479),5,8,7,(12689),(1289),5,(1346),(13469),(149),(129),(36),(23489),(1289),(1234789),(3679),5,(1346789),(1789),(12789),(1279),(2389),(12589),(1235789),(379),(137),(13789),6,4,(1279),(489),(189),6,(79),(147),2,5,3,(179)";
  }

}
