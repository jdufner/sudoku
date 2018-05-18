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


package de.jdufner.sudoku;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.context.SolverServiceFactory;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-03-06
 * 
 */
public abstract class AbstractMainClass {

  private static Logger LOG = Logger.getLogger(AbstractMainClass.class);

  /**
   * Startet die Applikation und führt die allgemeine Logik vorher und nachher aus.
   * 
   * @throws Exception
   */
  protected void start() throws Exception {
    setUp();
    run();
    tearDown();
  }

  /**
   * Allgemeine Logik vor dem Ausführen der Applikation.
   */
  protected void setUp() {

  }

  /**
   * Allgemeine Logik nach dem Ausführen der Applikation.
   */
  protected void tearDown() {
    LOG.debug("ExecutorService runterfahren");
    SolverServiceFactory.INSTANCE.shutdown();
  }

  /**
   * Methode, welche die eigentliche Logik der Applikation enthält.
   * 
   * @throws Exception
   */
  protected abstract void run() throws Exception;

}
