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


package de.jdufner.sudoku.common.board;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Kombination;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class KombinationTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(KombinationTest.class);

  public void testB() {
    Collection<String> collection = new ArrayList<String>();
    collection.add("a");
    collection.add("b");
    collection.add("c");
    collection.add("d");
    collection.add("e");
    Kombination<String> kombination = new Kombination<String>(collection, 3);
    LOG.info(kombination);
    int i = 0;
    while (kombination.hasNextKombination() && i < 20) {
      i++;
      kombination.buildNextKombination();
      LOG.info(kombination);
    }
  }

  public void testC() {
    Collection<String> collection = new ArrayList<String>();
    collection.add("a");
    collection.add("b");
    Kombination<String> kombination = new Kombination<String>(collection, 2);
    LOG.info(kombination);
    int i = 0;
    while (kombination.hasNextKombination() && i < 20) {
      i++;
      kombination.buildNextKombination();
      LOG.info(kombination);
    }
  }

}
