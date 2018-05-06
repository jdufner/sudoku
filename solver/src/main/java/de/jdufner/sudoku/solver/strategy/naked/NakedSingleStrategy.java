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

import java.util.Collection;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.SetValueCommand.SetValueCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Setzt einen einzelnen verbleibenden Kandidaten als festen Wert. Diese
 * Strategie wird zur Zeit nicht benötigt, weil das immer beim Entfernen
 * automatisch geprüft und ggf. durchgeführt wird.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 03.04.2010
 * @version $Revision$
 */
public final class NakedSingleStrategy extends AbstractStrategy implements CellHandler {

  private static final Logger LOG = Logger.getLogger(NakedSingleStrategy.class);

  public NakedSingleStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public void handleCell(final Cell cell) {
    if (!cell.isFixed() && cell.getCandidates().size() == 1) {
      getCommands().add(new SetValueCommandBuilder(getStrategyName(), cell, cell.getCandidates().first()).build());
    }
  }

  @Override
  public void initialize() {
    // Nix machen
  }

  @Override
  protected Collection<Command> executeStrategy() {
    HandlerUtil.forEachCell(getSudoku(), this);
    return getCommands();
  }

  @Override
  public Level getLevel() {
    return Level.SEHR_LEICHT;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return StrategyNameEnum.NAKED_SINGLE;
  }

}
