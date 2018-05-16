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

import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 13.03.2010
 * @version $Revision$
 */
public final class CandidatesTest extends AbstractSolverTestCase {

  public void testEquals1() throws Exception {
    final Candidates<Literal> c1 = new Candidates<Literal>();
    c1.add(Literal.getInstance(1));
    c1.add(Literal.getInstance(2));
    c1.add(Literal.getInstance(3));
    final Candidates<Literal> c2 = new Candidates<Literal>();
    c2.add(Literal.getInstance(1));
    c2.add(Literal.getInstance(2));
    c2.add(Literal.getInstance(3));
    final Candidates<Literal> c3 = new Candidates<Literal>();
    c3.add(Literal.getInstance(1));
    c3.add(Literal.getInstance(2));
    c3.add(Literal.getInstance(3));
    final Candidates<Literal> c4 = new Candidates<Literal>();
    c4.add(Literal.getInstance(1));
    assertEquals("Soll gleich sein.", c1, c2);
    assertNotSame("Sind nicht dasselbe.", c1, c2);
    assertEquals("Soll gleich sein.", c1, c3);
    assertNotSame("Sind nicht dasselbe.", c1, c3);
    assertEquals("Soll gleich sein.", c2, c3);
    assertNotSame("Sind nicht dasselbe.", c2, c3);
    assertFalse("Enthält unterschiedliche Literale.", c1.equals(c4));
    assertNotSame("Sind nicht dasselbe.", c2, c3);
  }

  public void testContainsAtLeastOneOf1() throws Exception {
    final Candidates<Literal> candidates = new Candidates<Literal>();
    candidates.add(Literal.getInstance(1));
    candidates.add(Literal.getInstance(2));
    candidates.add(Literal.getInstance(3));
    final Collection<Literal> testCandidates = new ArrayList<Literal>();
    testCandidates.add(Literal.getInstance(1));
    assertTrue("Testkandidat ist in Kandidatenliste enthalten", candidates.containsAtLeastOneOf(testCandidates));
  }

  public void testContainsAtLeastOneOf2() throws Exception {
    //    final Collection<Literal> c = new ArrayList<Literal>();
    //    c.add(Literal.getInstance(1));
    //    c.add(Literal.getInstance(2));
    //    c.add(Literal.getInstance(3));
    final Candidates<Literal> candidates = new Candidates<Literal>();
    candidates.add(Literal.getInstance(1));
    candidates.add(Literal.getInstance(2));
    candidates.add(Literal.getInstance(3));
    final Collection<Literal> testCandidates = new ArrayList<Literal>();
    testCandidates.add(Literal.getInstance(4));
    //    assertFalse(c.contains(Literal.getInstance(4)));
    assertFalse("Testkandidat ist in Kandidatenliste nicht enthalten", candidates.containsAtLeastOneOf(testCandidates));
  }
}
