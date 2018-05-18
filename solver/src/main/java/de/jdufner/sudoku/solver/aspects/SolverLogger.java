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


package de.jdufner.sudoku.solver.aspects;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public final class SolverLogger implements MethodInterceptor {

  private static final Logger LOG = Logger.getLogger(SolverLogger.class);
  private static final String ERGEBNIS = "Rätsel ";
  private static final String GELOEST = "gelöst!";
  private static final String NICHT_GELOEST = "nicht gelöst!";

  public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
    final Grid sudoku = (Grid) methodInvocation.getArguments()[0];
    LOG.info(ERGEBNIS + sudoku.toShortString());
    final Object obj = methodInvocation.proceed();
    if (obj instanceof Solution) {
      final Solution solution = (Solution) obj;
      LOG.info(solution);
    } else if (obj instanceof Grid) {
      final Grid result = (Grid) obj;
      if (result == null) {
        LOG.info(ERGEBNIS + NICHT_GELOEST);
      } else {
        LOG.info(ERGEBNIS + GELOEST + " " + result.toShortString());
      }
    } else {
      LOG.info(ERGEBNIS + obj.toString());
      // TODO Warum?
      // throw new IllegalStateException("Methode wurde mit unerwartetem Parameter (nicht " + Sudoku.class.getName()
      // + ", sondern " + obj.getClass().getName() + ") aufgerufen. " + obj.toString());
    }
    return obj;
  }
}
