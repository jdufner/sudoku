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


package de.jdufner.sudoku.solver.strategy.hidden;

import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Box;
import de.jdufner.sudoku.common.board.BoxHandler;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Grid;

/**
 * Führt die <a href="http://www.sudopedia.org/wiki/Hidden_Subset">Hidden Subset</a> Strategie konfigurierbar auf einem
 * <a href="http://www.sudopedia.org/wiki/Box">Block</a> aus.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public class HiddenBlockStrategy extends AbstractHiddenStrategy implements BoxHandler {

  //private static final Logger LOG = Logger.getLogger(HiddenBlockStrategy.class);

  protected HiddenBlockStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachBlock(getSudoku(), this);
    return getCommands();
  }

  /**
   * @see BoxHandler#handleBlock(Box)
   * @see AbstractHiddenStrategy#handleUnit(de.jdufner.sudoku.common.Unit)
   */
  public void handleBlock(final Box block) {
    handleUnit(block);
  }

}
