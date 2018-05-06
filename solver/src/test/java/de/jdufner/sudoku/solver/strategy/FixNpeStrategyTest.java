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
package de.jdufner.sudoku.solver.strategy;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.service.Solver;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class FixNpeStrategyTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(FixNpeStrategyTest.class);

  public FixNpeStrategyTest(String name) {
    super(name);
  }

  public void testFirstSolution1() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:0,2,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,3,0,7,4,0,8,0,0,0,0,0,0,0,0,0,3,0,0,2,0,8,0,0,4,0,0,1,0,6,0,0,5,0,0,0,0,0,0,0,0,0,1,0,7,8,0,5,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,4,0");
    Solver solver = getStrategySolver();
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
  }

  public void testFirstSolution2() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:0,0,0,0,0,9,7,0,0,0,1,0,0,0,0,9,0,0,4,0,0,0,5,3,0,2,0,0,0,0,0,0,7,0,0,0,0,3,0,0,0,0,0,4,0,0,0,0,8,0,0,0,0,0,0,6,0,4,1,0,0,0,2,0,0,9,0,0,0,0,5,0,0,0,8,6,0,0,0,0,0");
    Solver solver = getStrategySolver();
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
  }

  public void testFirstSolutionHelper() {
    Grid sudoku = SudokuFactory.INSTANCE
        .buildSudoku("9:0,2,6,0,0,0,0,0,0,0,0,0,6,0,0,0,0,3,0,7,4,0,8,0,0,0,0,0,0,0,0,0,3,0,0,2,0,8,0,0,4,0,0,1,7,6,0,0,5,0,0,0,0,0,0,0,0,0,1,0,7,8,0,5,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,4,0");
    Solver solver = getStrategySolver();
    Grid result = solver.solve(sudoku);
    LOG.debug(result);
  }

}
