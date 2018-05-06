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
package de.jdufner.sudoku.builder.utils;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.service.Solver;
import de.jdufner.sudoku.test.AbstractGeneratorTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-28
 * @version $Revision$
 */
public class NachbarschaftUtilsTest extends AbstractGeneratorTestCase {

  private static final Logger LOG = Logger.getLogger(NachbarschaftUtilsTest.class);

  private Solver solver = null;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    solver = getStrategySolverWithBacktracking();
  }

  public void testCheckNachbarschaft1() {
    Grid sudoku = SudokuFactory.INSTANCE.buildFilled(SudokuSize.DEFAULT);
    LOG.debug(sudoku);
    assertFalse(NachbarschaftUtils.checkNachbarschaft(sudoku));
  }

  public void testCheckNachbachschaft2() {
    Grid underDeterminedSudoku = SudokuFactory.INSTANCE.buildShuffled(SudokuSize.DEFAULT);
    Grid sudoku = solver.solve(underDeterminedSudoku);
    LOG.debug(sudoku);
    assertTrue(NachbarschaftUtils.checkNachbarschaft(sudoku));
  }

}
