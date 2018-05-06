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
package de.jdufner.sudoku.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;
import de.jdufner.sudoku.solver.strategy.simple.SimpleSerialStrategy;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

public final class CommandUtilsTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(CommandUtilsTest.class);

  public void testIsEqual1() {
    final Command rcc1 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 6).addCandidate(1, 2).build();
    final List<Command> commands1 = new ArrayList<Command>();
    commands1.add(rcc1);
    final Command rcc2 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 6).addCandidate(1, 2).build();
    final List<Command> commands2 = new ArrayList<Command>();
    commands2.add(rcc2);
    assertTrue(CommandUtils.isEqual(commands1, commands2));
  }

  public void testIsEqual2() {
    final Command rcc1 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 6).addCandidate(1, 3).build();
    final List<Command> commands1 = new ArrayList<Command>();
    commands1.add(rcc1);
    final Command rcc2 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 6).addCandidate(1, 2).build();
    final List<Command> commands2 = new ArrayList<Command>();
    commands2.add(rcc2);
    assertFalse(CommandUtils.isEqual(commands1, commands2));
  }

  public void testIsEqual3() {
    final Command rcc1 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 6).addCandidate(1, 2).build();
    final Command rcc3 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 8).addCandidate(1, 2).build();
    final List<Command> commands1 = new ArrayList<Command>();
    commands1.add(rcc1);
    commands1.add(rcc3);
    final Command rcc2 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 6).addCandidate(1, 2).build();
    final Command rcc4 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 8).addCandidate(1, 2).build();
    final List<Command> commands2 = new ArrayList<Command>();
    commands2.add(rcc2);
    commands2.add(rcc4);
    assertTrue(CommandUtils.isEqual(commands1, commands2));
  }

  public void testAggregateCommands() {
    final Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku(".5..9...2.6..2...8..7...9...81.............5...69.3......7.2..5...6..4..8.9.5...3");
    final SimpleSerialStrategy simpleSerialStrategy = new SimpleSerialStrategy(sudoku);
    final Collection<Command> commands = simpleSerialStrategy.executeStrategy();
    LOG.debug("Original Anzahl der Commands: " + commands.size());
    final Collection<RemoveCandidatesCommand> aggreated = CommandUtils.aggregateCommands(getRemoveCommands(commands));
    LOG.debug("Anzahl der aggregierten Commands: " + aggreated.size());
    assertTrue(aggreated.size() < commands.size());
  }

  private Collection<RemoveCandidatesCommand> getRemoveCommands(Collection<Command> commands) {
    final List<RemoveCandidatesCommand> removeCmds = new ArrayList<RemoveCandidatesCommand>();
    for (Command cmd : commands) {
      if (cmd instanceof RemoveCandidatesCommand) {
        removeCmds.add((RemoveCandidatesCommand) cmd);
      }
    }
    return removeCmds;
  }

}
