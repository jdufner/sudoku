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
package de.jdufner.sudoku.solver.ddt.sudoku_sf_net;

import static org.junit.Assert.assertEquals;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.ddt.AbstractSolverExcelTestCase;
import de.jdufner.sudoku.solver.service.Solver;
import java.util.Arrays;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
@RunWith(Parameterized.class)
public final class SudokuSfNetTest extends AbstractSolverExcelTestCase {

  private static final Logger LOG = Logger.getLogger(SudokuSfNetTest.class);

  private String sudokuAsString;
  private boolean solveable;

  public SudokuSfNetTest(final String sudokuAsString, final String solveable) {
    this.sudokuAsString = sudokuAsString;
    this.solveable = Boolean.parseBoolean(solveable);
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"9:0,0,0,0,0,0,0,1,0,4,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,5,0,4,0,7,0,0,8,0,0,0,3,0,0,0,0,1,0,9,0,0,0,0,3,0,0,4,0,0,2,0,0,0,5,0,1,0,0,0,0,0,0,0,0,8,0,6,0,0,0", "True"},
        {"9:0,0,0,0,0,0,0,1,2,0,0,0,0,3,5,0,0,0,0,0,0,6,0,0,0,7,0,7,0,0,0,0,0,3,0,0,0,0,0,4,0,0,8,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,2,0,0,0,0,0,8,0,0,0,0,0,4,0,0,5,0,0,0,0,6,0,0", "True"},
        {"9:0,0,0,0,4,0,0,3,0,9,8,0,6,0,1,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,1,0,0,4,0,5,0,7,0,0,6,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,9,0,8,0,7,6,0,7,0,0,3,0,0,0,0", "False"},
        {"9:0,0,0,0,3,0,0,0,0,0,2,0,5,0,1,0,0,0,4,0,0,0,0,0,9,7,0,0,0,0,0,0,0,0,0,5,0,0,8,0,7,0,6,0,0,3,0,0,0,0,0,0,0,0,0,5,9,0,0,0,0,0,1,0,0,0,6,0,4,0,3,0,0,0,0,0,8,0,0,0,0", "False"},
        {"9:0,0,0,0,0,9,0,0,0,0,0,0,0,1,4,7,0,0,0,0,2,0,0,0,0,0,0,7,0,0,0,0,0,0,8,6,5,0,0,0,3,0,0,0,2,9,4,0,0,0,0,0,0,1,0,0,0,0,0,0,4,0,0,0,0,6,2,5,0,0,0,0,0,0,0,8,0,0,0,0,0", "True"},
        {"9:0,2,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,3,0,7,4,0,8,0,0,0,0,0,0,0,0,0,3,0,0,2,0,8,0,0,4,0,0,1,0,6,0,0,5,0,0,0,0,0,0,0,0,0,1,0,7,8,0,5,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,4,0", "True"},
        {"9:0,0,0,0,0,9,7,0,0,0,1,0,0,0,0,9,0,0,4,0,0,0,5,3,0,2,0,0,0,0,0,0,7,0,0,0,0,3,0,0,0,0,0,4,0,0,0,0,8,0,0,0,0,0,0,6,0,4,1,0,0,0,2,0,0,9,0,0,0,0,5,0,0,0,8,6,0,0,0,0,0", "False"},
        {"9:0,0,9,6,0,0,0,5,0,6,0,0,0,0,0,0,0,0,3,0,0,0,2,1,0,8,0,0,0,0,0,0,5,0,0,0,0,7,0,0,0,0,0,2,0,0,0,0,9,0,0,0,0,0,0,8,0,5,6,0,0,0,3,0,0,0,0,0,0,0,0,4,0,1,0,0,0,8,7,0,0", "True"},
        {"9:0,0,0,0,0,0,6,0,3,0,0,0,0,4,0,2,0,0,0,5,0,9,7,0,0,0,8,0,0,0,1,0,0,0,0,0,0,9,0,0,0,0,0,4,0,0,0,0,0,0,3,0,0,0,1,0,0,0,9,4,0,7,0,0,0,8,0,5,0,0,0,0,6,0,4,0,0,0,0,0,0", "True"},
        {"9:0,0,8,0,4,0,0,0,5,0,0,6,3,0,0,0,0,0,7,2,0,0,0,0,0,0,0,5,0,0,1,0,0,0,0,0,3,0,0,0,0,0,0,0,4,0,0,0,0,0,6,0,0,8,0,0,0,0,0,0,0,1,3,0,0,0,0,0,5,9,0,0,4,0,0,0,7,0,2,0,0", "True"},
        {"9:0,0,0,0,0,0,0,6,0,1,0,0,9,0,4,0,0,0,0,3,0,7,0,0,8,0,0,0,0,6,0,0,0,9,0,0,0,8,0,0,3,0,0,7,0,0,0,2,0,0,0,5,0,0,0,0,9,0,0,2,0,4,0,0,0,0,1,0,5,0,0,3,0,7,0,0,0,0,0,0,0", "False"},
        {"9:0,0,0,0,0,0,0,7,0,4,0,0,0,9,0,1,0,0,0,0,3,6,0,2,0,0,0,0,5,0,0,0,0,6,3,0,0,0,0,0,8,0,0,0,0,0,1,9,0,0,0,0,2,0,0,0,0,4,0,3,5,0,0,0,0,2,0,1,0,0,0,8,0,7,0,0,0,0,0,0,0", "True"}
    });
  }

  @Test
  public void testStrategySolver() {
    Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(sudokuAsString);
    Solver solver = getStrategySolver();
    Grid result = solver.solve(sudoku);
    if (LOG.isDebugEnabled()) {
      LOG.debug(result.toString());
    }
    assertEquals(solveable, result.isSolved());
  }

  // public void testBacktrackingSolver() {
  // Sudoku sudoku = SudokuFactory.buildSudoku(sudokuAsString);
  // Solver solver = getBacktrackingSolver();
  // Sudoku result = solver.getSolution(sudoku);
  // if (log.isDebugEnabled()) {
  // log.debug(result.toString());
  // }
  // assertTrue(result.isSolved());
  // }

}
