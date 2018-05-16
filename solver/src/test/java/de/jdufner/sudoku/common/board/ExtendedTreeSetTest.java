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
package de.jdufner.sudoku.common.board;

import de.jdufner.sudoku.common.board.ExtendedTreeSet;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class ExtendedTreeSetTest extends AbstractSolverTestCase {

  ExtendedTreeSet<Integer> set = new ExtendedTreeSet<Integer>();

  @Override
  public void setUp() {
    set.add(1);
    set.add(2);
    set.add(3);
    set.add(4);
    set.add(5);
    set.add(6);
    set.add(7);
  }

  public void testGetSmallestElementGreatherThan1() {
    assertEquals(Integer.valueOf(5), set.getSmallestElementGreatherThan(4));
  }

  public void testGetSmallestElementGreatherThan2() {
    assertNull(set.getSmallestElementGreatherThan(7));
  }

  public void testGetGreatestElementSmallerThan1() {
    assertEquals(Integer.valueOf(4), set.getGreatestElementSmallerThan(5));
  }

  public void testGetGreatestElementSmallerThan2() {
    assertNull(set.getGreatestElementSmallerThan(1));
  }

  public void testHeadSetBySize1() {
    assertEquals(2, set.headSetBySize(2, true).size());
  }

  public void testHeadSetBySize2() {
    assertEquals(2, set.headSetBySize(2, false).size());
  }

  public void testHeadSetBySize3() {
    assertEquals(7, set.headSetBySize(10, true).size());
  }

  public void testHeadSetBySize4() {
    assertNull(set.headSetBySize(10, false));
  }

  public void testGetSmallestElementsGreaterThan1() {
    assertEquals(1, set.getSmallestElementsGreatherThan(4, 1).size());
    assertTrue(set.getSmallestElementsGreatherThan(4, 1).contains(5));
  }

  public void testGetSmallestElementsGreaterThan2() {
    assertNull(set.getSmallestElementsGreatherThan(7, 1));
  }

  public void testGetSmallestElementsGreaterThan3() {
    assertEquals(2, set.getSmallestElementsGreatherThan(4, 2).size());
    assertTrue(set.getSmallestElementsGreatherThan(4, 2).contains(5));
    assertTrue(set.getSmallestElementsGreatherThan(4, 2).contains(6));
  }

  public void testContainsGreaterElementThan1() {
    assertTrue(set.containsGreaterElementThan(0));
  }

  public void testContainsGreaterElementThan2() {
    assertTrue(set.containsGreaterElementThan(4));
  }

  public void testContainsGreaterElementThan3() {
    assertFalse(set.containsGreaterElementThan(7));
  }

  public void testContainsGreaterElementThan5() {
    assertFalse(set.containsGreaterElementThan(10));
  }

}
