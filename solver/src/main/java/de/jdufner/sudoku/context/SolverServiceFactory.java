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

// $Id$
package de.jdufner.sudoku.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public enum SolverServiceFactory {

  INSTANCE;

  //private static final Logger LOG = Logger.getLogger(SolverServiceFactory.class);
  private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);

  public static final String BACKTRACKING_SOLVER = "backtrackingSolver";
  public static final String STRATEGY_SOLVER = "strategySolver";
  public static final String STRATEGY_SOLVER_WITH_BACKTRACKING = "strategySolverWithBacktracking";

  private final transient ApplicationContext applicationContext;

  private SolverServiceFactory() {
    applicationContext = new ClassPathXmlApplicationContext("solver-context.xml");
//    try {
//      Log4jConfigurer.initLogging(Log4jConfigurer.CLASSPATH_URL_PREFIX + "log4j.properties", 10000);
//    } catch (FileNotFoundException fnfe) {
//      throw new RuntimeException(fnfe); // NOPMD
//    }
  }

  public Object getBean(final String name) {
    return applicationContext.getBean(name);
  }

  public Object getBean(final Class<?> clazz) {
    final String[] beanNames = applicationContext.getBeanNamesForType(clazz);
    if (beanNames.length == 0) {
      throw new IllegalStateException("Klasse " + clazz + " existiert im Kontext "
          + applicationContext.getDisplayName() + " nicht.");
    }
    if (beanNames.length > 1) {
      throw new IllegalStateException("Klasse " + clazz + " existiert im Kontext "
          + applicationContext.getDisplayName() + " mehrfach und ist nicht eindeutig.");
    }
    return applicationContext.getBean(beanNames[0]);
  }

  public ExecutorService getExecutorService() {
    return EXECUTOR_SERVICE;
  }

  public void shutdown() {
    getExecutorService().shutdown();
  }

}
