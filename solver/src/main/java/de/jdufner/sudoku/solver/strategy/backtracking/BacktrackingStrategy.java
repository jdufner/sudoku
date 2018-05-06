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
package de.jdufner.sudoku.solver.strategy.backtracking;

import java.util.Collection;
import java.util.List;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.SetValueCommand.SetValueCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.backtracking.Backtracking;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class BacktrackingStrategy extends AbstractStrategy implements CellHandler {

  private transient Grid result = null;
  private transient boolean sudokuUnique = true;

  public BacktrackingStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.SEHR_SCHWER;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return StrategyNameEnum.BACKTRACKING;
  }

  @Override
  public boolean isSudokuUnique() {
    return sudokuUnique;
  }

  @Override
  public Collection<Command> executeStrategy() {
    final Backtracking backtracking = new Backtracking(getSudoku(), 1);
    final List<Grid> results = backtracking.firstSolutions(2);
    result = results.get(0);
    if (results.size() > 1) {
      sudokuUnique = false;
    }
    HandlerUtil.forEachCell(getSudoku(), this);
    return getCommands();
  }

  @Override
  public void initialize() {
    // nothing to do
  }

  @Override
  public void handleCell(final Cell cell) {
    if (!cell.isFixed()) {
      getCommands().add(
          new SetValueCommandBuilder(getStrategyName(), cell, result.getCell(cell.getNumber()).getValue()).build());
    }
  }

}
