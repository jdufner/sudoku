// $Id$

/*
 * Gudoku (http://sourceforge.net/projects/gudoku)
 * Sudoku-Implementierung auf Basis des Google Webtoolkit 
 * (http://code.google.com/webtoolkit/). Die Lösungsalgorithmen in Java laufen 
 * parallel. Die Sudoku-Rätsel werden mittels JDBC in einer Datenbank
 * gespeichert.
 * 
 * Copyright (C) 2008 Jürgen Dufner
 *
 * Dieses Programm ist freie Software. Sie können es unter den Bedingungen der 
 * GNU General Public License, wie von der Free Software Foundation 
 * veröffentlicht, weitergeben und/oder modifizieren, entweder gemäß Version 3 
 * der Lizenz oder (nach Ihrer Option) jeder späteren Version.
 *
 * Die Veröffentlichung dieses Programms erfolgt in der Hoffnung, daß es Ihnen 
 * von Nutzen sein wird, aber OHNE IRGENDEINE GARANTIE, sogar ohne die 
 * implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FÜR EINEN 
 * BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
 *
 * Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem 
 * Programm erhalten haben. Falls nicht, siehe <http://www.gnu.org/licenses/>.
 *
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
