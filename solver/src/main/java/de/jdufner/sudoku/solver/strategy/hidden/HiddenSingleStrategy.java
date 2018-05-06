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
import java.util.List;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.SetValueCommand.SetValueCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Literal2CellMap;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.House;
import de.jdufner.sudoku.common.board.UnitHandler;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Wird diese Klasse überhaupt benötigt? Dieser Fall tritt ein, wenn nur ein Kandidat, der in keiner anderen Zelle in
 * derselben Unit vorkommt, existiert.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class HiddenSingleStrategy extends AbstractStrategy implements UnitHandler {
  private static final Logger LOG = Logger.getLogger(HiddenSingleStrategy.class);

  public HiddenSingleStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.LEICHT;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return StrategyNameEnum.HIDDEN_SINGLE;
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachUnit(getSudoku(), this);
    return getCommands();
  }

  public void handleUnit(final House unit) {
    final Literal2CellMap literal2CellMap = new Literal2CellMap(unit.getCells());
    for (Literal single : literal2CellMap.getCandidatesByNumber(1)) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Found unique candidate " + single + " in Unit " + unit);
      }
      for (Cell cell : literal2CellMap.getCellsContainingLiteral(single)) {
        // TODO evtl andere Implementierungsvariante ausprobieren
        final List<Literal> fixed = new ArrayList<Literal>();
        fixed.addAll(getSudoku().getBlock(cell.getBlockIndex()).getFixedAsLiteral());
        fixed.addAll(getSudoku().getColumn(cell.getColumnIndex()).getFixedAsLiteral());
        fixed.addAll(getSudoku().getRow(cell.getRowIndex()).getFixedAsLiteral());
        if (!fixed.contains(single)) {
          getCommands().add(new SetValueCommandBuilder(StrategyNameEnum.HIDDEN_SINGLE, cell, single).build());
        }
      }
    }
  }

}
