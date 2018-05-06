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

public final class HiddenColumnStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands.add(new RetainCandidatesCommandBuilder(null, 3, 1).addCandidate(6, 8).build());
    commands.add(new RetainCandidatesCommandBuilder(null, 6, 1).addCandidate(6, 8).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    final HiddenColumnStrategy hiddenColumnStrategy = new HiddenColumnStrategy(sudoku);
    hiddenColumnStrategy.setSize(2);
    return hiddenColumnStrategy;
  }

  @Override
  protected String getSudokuAsString() {
    return "(57),(57),8,(1349),(1349),6,(34),2,(1349),2,3,6,(1489),(1479),(4789),(47),5,(1479),1,9,4,5,(237),(27),8,6,(37),(5678),(5678),(57),(2489),(245679),3,1,(479),(4789),(678),1,3,(489),(4679),(4789),5,(479),2,9,4,2,(18),(157),(578),6,(37),(378),(45678),(25678),(57),(234),(2345),(245),9,1,(347),3,(27),1,6,(249),(249),(247),8,5,(45),(25),9,7,8,1,(234),(34),6";
  }

}
