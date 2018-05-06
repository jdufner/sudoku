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
package de.jdufner.sudoku.solver.strategy.xwing;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

public final class XWingSerialStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    Collection<Command> commands = new ArrayList<Command>();
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 2, 1).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 2, 4).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 2, 6).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 2, 8).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 8, 4).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 8, 6).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 8, 8).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 2, 4).addCandidate(9).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 6, 4).addCandidate(9).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 1, 8).addCandidate(9).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 2, 8).addCandidate(9).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 6, 8).addCandidate(9).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new XWingSerialStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    return "2,8,3,4,(69),1,7,5,(69),1,(69),5,(239),8,7,(26),4,(369),7,(69),4,(2359),(3569),(3569),(1268),(689),(13689),4,1,(79),(589),2,(59),(568),3,(5678),6,2,(79),(3589),1,(359),4,(78),(578),3,5,8,6,7,4,9,1,2,8,3,1,(59),(4569),2,(56),(79),(479),5,7,6,1,(49),8,3,2,(49),9,4,2,7,(356),(356),(1568),(68),(1568)";
  }

}
