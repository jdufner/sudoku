/*
 * Sudoku is a puzzle game. It solves and generates puzzles in different
 * formats.
 * Copyright (C) 2008-2018  Juergen Dufner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package de.jdufner.sudoku.solver.strategy.naked;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.House;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.UnitHandler;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since
 * @version $Revision$
 */
public abstract class AbstractNakedStrategy extends AbstractStrategy implements NakedUnit, UnitHandler {

  private static final Logger LOG = Logger.getLogger(AbstractNakedStrategy.class);

  private transient int size;
  private transient StrategyNameEnum strategyNameEnum;

  /**
   * In dieser Zuordnung werden pro Einheit die Kandidaten gespeichert, die
   * bereits in der Strategie geprüft wurden.
   */
  private final transient Map<House, List<Candidates<Literal>>> allreadyHandledCandidatesPerUnit = new HashMap<House, List<Candidates<Literal>>>();

  protected AbstractNakedStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.LEICHT;
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public void setSize(final int size) {
    this.size = size;
  }

  @Override
  public void setStrategyNameEnum(final StrategyNameEnum strategyNameEnum) {
    this.strategyNameEnum = strategyNameEnum;
  }

  @Override
  public StrategyNameEnum getStrategyName() {
    return strategyNameEnum;
  }

  @Override
  public void handleUnit(final House unit) {
    final SortedSet<Cell> nonFixed = unit.getNonFixed();
    if (nonFixed.size() == getSize()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug(getClass().getSimpleName() + " skipped for unit " + unit);
      }
      return;
    }
    for (Cell cell : nonFixed) {
      if (areCandidatesAllreadyHandled(unit, cell.getCandidates())) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Candidates " + cell.getCandidates() + " are allready handled in Unit " + unit);
        }
        continue;
      }
      if (cell.getCandidates().size() == getSize()) {
        putCandidatesIntoMap(unit, cell.getCandidates());
        final Candidates<Cell> cellsWithSameCandidates = findCellsWithSameCandidatesInUnit(cell, unit);
        if (cellsWithSameCandidates.size() == getSize()) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("Found " + getSize() + " cells " + cellsWithSameCandidates + " in " + unit);
          }
          removeCandidatesInOtherCells(cell.getCandidates(), cellsWithSameCandidates, unit);
        }
      }
    }
  }

  /**
   * Legt die Kandidaten pro Unit in der Zuordnung ab.
   * 
   * @param unit
   * @param candidates
   */
  protected void putCandidatesIntoMap(final House unit, final Candidates<Literal> candidates) {
    if (allreadyHandledCandidatesPerUnit.get(unit) == null) {
      final List<Candidates<Literal>> setOfCandidates = new ArrayList<Candidates<Literal>>();
      allreadyHandledCandidatesPerUnit.put(unit, setOfCandidates);
    }
    if (!allreadyHandledCandidatesPerUnit.get(unit).contains(candidates)) {
      allreadyHandledCandidatesPerUnit.get(unit).add(candidates);
    }
  }

  /**
   * @param unit
   * @param candidates
   * @return <code>true</code>, wenn für die angegebene Einheit bereits die
   *         Kandidaten vorhanden sind, sonst <code>false</code>
   */
  protected boolean areCandidatesAllreadyHandled(final House unit, final Collection<Literal> candidates) {
    if (allreadyHandledCandidatesPerUnit.get(unit) == null) {
      return false;
    }
    if (!allreadyHandledCandidatesPerUnit.get(unit).contains(candidates)) {
      return false;
    }
    return true;
  }

  /**
   * The result list return the given cell too.
   * 
   * @param cell
   * @param unit
   * @return
   */
  protected final Candidates<Cell> findCellsWithSameCandidatesInUnit(final Cell cell, final House unit) {
    final Candidates<Cell> cells = new Candidates<Cell>();
    for (Cell cell2 : unit.getNonFixed()) {
      if (cell2.getCandidates().containsAtMost(cell.getCandidates())) {
        cells.add(cell2);
      }
    }
    return cells;
  }

  /**
   * @param candidates
   * @param excludedCells
   * @param unit
   */
  protected final void removeCandidatesInOtherCells(final Candidates<Literal> candidates,
      final Collection<Cell> excludedCells, final House unit) {
    for (Cell cell : unit.getNonFixed()) {
      if (!excludedCells.contains(cell) && cell.getCandidates().containsAtLeastOneOf(candidates)) {
        getCommands().add(new RemoveCandidatesCommandBuilder(strategyNameEnum, cell).addCandidate(candidates).build());
      }
    }
  }

}
