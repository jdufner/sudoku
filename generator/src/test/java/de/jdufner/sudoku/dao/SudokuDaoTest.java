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
package de.jdufner.sudoku.dao;

import java.util.List;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.test.AbstractGeneratorTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SudokuDaoTest extends AbstractGeneratorTestCase {

  private static final Logger LOG = Logger.getLogger(SudokuDaoTest.class);

  private SudokuDao sudokuDao;
  private ExtendedSolver solver;

  @Override
  public void setUp() {
    sudokuDao = (SudokuDao) GeneratorServiceFactory.INSTANCE.getBean(SudokuDao.class);
    solver = getStrategySolverWithBacktracking();
  }

  public void testLoadSudoku1() {
    SudokuData sudokuData = sudokuDao.loadSudoku(26532);
    assertNotNull(sudokuData);
    assertEquals(24, sudokuData.getFixed());
    assertEquals("N", sudokuData.getxSudoku());
    assertEquals(256, sudokuData.getStrategySimple());
    assertEquals(25, sudokuData.getStrategyHiddenSingle());
    assertEquals(0, sudokuData.getStrategyNakedPair());
    assertEquals(0, sudokuData.getStrategyNakedTriple());
    assertEquals(0, sudokuData.getStrategyNakedQuad());
    assertEquals(0, sudokuData.getStrategyHiddenPair());
    assertEquals(0, sudokuData.getStrategyHiddenTriple());
    assertEquals(0, sudokuData.getStrategyHiddenQuad());
    assertEquals(0, sudokuData.getStrategyIntersectionRemoval());
    assertEquals(0, sudokuData.getStrategyYwing());
    assertEquals(0, sudokuData.getStrategyXwing());
    assertEquals(0, sudokuData.getStrategySwordfish());
    assertEquals(0, sudokuData.getStrategyJellyfish());
    assertEquals(0, sudokuData.getStrategyBacktracking());
    LOG.debug(sudokuData);
  }

  public void testLoadSudoku2() {
    SudokuData sudokuData = sudokuDao.loadSudoku(26539);
    assertNotNull(sudokuData);
    assertEquals(25, sudokuData.getFixed());
    assertEquals("N", sudokuData.getxSudoku());
    assertEquals(177, sudokuData.getStrategySimple());
    assertEquals(28, sudokuData.getStrategyHiddenSingle());
    assertEquals(4, sudokuData.getStrategyNakedPair());
    assertEquals(0, sudokuData.getStrategyNakedTriple());
    assertEquals(0, sudokuData.getStrategyNakedQuad());
    assertEquals(0, sudokuData.getStrategyHiddenPair());
    assertEquals(0, sudokuData.getStrategyHiddenTriple());
    assertEquals(0, sudokuData.getStrategyHiddenQuad());
    assertEquals(0, sudokuData.getStrategyIntersectionRemoval());
    assertEquals(0, sudokuData.getStrategyYwing());
    assertEquals(0, sudokuData.getStrategyXwing());
    assertEquals(0, sudokuData.getStrategySwordfish());
    assertEquals(0, sudokuData.getStrategyJellyfish());
    assertEquals(28, sudokuData.getStrategyBacktracking());
    LOG.debug(sudokuData);
  }

  public void testLoadSudokuOfDay() {
    Grid sudoku = sudokuDao.loadSudokuOfDay();
    LOG.debug(sudoku.toString());
  }

  public void testFindSudokus1() {
    List<SudokuData> sudokuDataList = sudokuDao.findSudokus(SudokuSize.DEFAULT, Level.LEICHT, 3, Boolean.FALSE);
    LOG.debug(sudokuDataList);
    assertEquals(3, sudokuDataList.size());
  }

  public void testFindSudokus2() {
    boolean weitereObjekteVorhanden = true;
    int anzahlObjekteGesamt = 0;
    int index = 0;
    int number = 1000;
    do {
      List<SudokuData> sudokuDataList = sudokuDao.findSudokus(index, number);
      anzahlObjekteGesamt += sudokuDataList.size();
      index += sudokuDataList.size();
      if (sudokuDataList.size() < number) {
        weitereObjekteVorhanden = false;
      }
    } while (weitereObjekteVorhanden);
    LOG.debug(anzahlObjekteGesamt);
    assertTrue(anzahlObjekteGesamt > 25000);
  }

}
