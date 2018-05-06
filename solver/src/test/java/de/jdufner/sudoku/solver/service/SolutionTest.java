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
package de.jdufner.sudoku.solver.service;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

public class SolutionTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(SolutionTest.class);

  public void testToString() {
    Grid quest = SudokuFactory.INSTANCE
        .buildSudoku(".1...4..3.3..5..7.74....1.......25....1...6....35.......2....47.9..7..2.4..3...1.");
    Solution solution = new Solution(quest);
    solution.setLevel(Level.SEHR_SCHWER);
    solution.setUnique(true);
    String solutionString = solution.toString();
    LOG.debug(solutionString);
    assertTrue(solutionString.contains("Rätsel"));
    assertTrue(solutionString.contains("Lösung"));
    assertTrue(solutionString.contains("Eindeutigkeit"));
    assertTrue(solutionString.contains("Schwierigkeitsgrad"));
  }

}
