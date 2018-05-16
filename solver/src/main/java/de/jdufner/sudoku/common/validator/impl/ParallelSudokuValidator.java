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


package de.jdufner.sudoku.common.validator.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.validator.SudokuValidator;
import de.jdufner.sudoku.context.SolverServiceFactory;

/**
 * Prüft mit einem parallel Algorithmus ob das Sudoku gültig ist.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class ParallelSudokuValidator implements SudokuValidator {

  private static final Logger LOG = Logger.getLogger(Grid.class);

  @Override
  public boolean isValid(final Grid sudoku) {
    try {
      final AtomicBoolean validity = new AtomicBoolean(true);
      final Collection<UnitValidChecker> checkTasks = new ArrayList<UnitValidChecker>();
      checkTasks.add(new UnitValidChecker(validity, sudoku.getBlocks()));
      checkTasks.add(new UnitValidChecker(validity, sudoku.getColumns()));
      checkTasks.add(new UnitValidChecker(validity, sudoku.getRows()));
      SolverServiceFactory.INSTANCE.getExecutorService().invokeAll(checkTasks);
      return validity.get();
    } catch (InterruptedException ie) {
      LOG.error(ie.getMessage(), ie);
      return false;
    }
  }

}
