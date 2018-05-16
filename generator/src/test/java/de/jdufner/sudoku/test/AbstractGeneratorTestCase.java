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


package de.jdufner.sudoku.test;

import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.solver.service.Solver;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-02-26
 * 
 * 
 */
public abstract class AbstractGeneratorTestCase {

  protected transient Solver backtrackingSolver;
  protected transient ExtendedSolver strategySolver;
  protected transient ExtendedSolver strategySolverWithBacktracking;

  protected void setUp() throws Exception {
    backtrackingSolver = (Solver) SolverServiceFactory.INSTANCE.getBean(SolverServiceFactory.BACKTRACKING_SOLVER);
    strategySolver = (ExtendedSolver) SolverServiceFactory.INSTANCE.getBean(SolverServiceFactory.STRATEGY_SOLVER);
    strategySolverWithBacktracking = (ExtendedSolver) SolverServiceFactory.INSTANCE
        .getBean(SolverServiceFactory.STRATEGY_SOLVER_WITH_BACKTRACKING);
  }

  protected Solver getBacktrackingSolver() {
    return backtrackingSolver;
  }

  protected ExtendedSolver getStrategySolver() {
    return strategySolver;
  }

  protected ExtendedSolver getStrategySolverWithBacktracking() {
    return strategySolverWithBacktracking;
  }

}
