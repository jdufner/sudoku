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

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SolverClock implements MethodInterceptor {

  private static final Logger LOG = Logger.getLogger(SolverClock.class);

  public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
    final String methodName = methodInvocation.getMethod().getName();
    if (LOG.isInfoEnabled()) {
      LOG.info(">>> " + methodName);
    }
    final long startTime = System.currentTimeMillis();
    final Object obj = methodInvocation.proceed();
    final long endTime = System.currentTimeMillis();
    if (LOG.isInfoEnabled()) {
      LOG.info("<<< " + methodName + " " + (endTime - startTime) + " msec");
    }
    return obj;
  }

}
