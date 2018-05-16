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
 * 
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
