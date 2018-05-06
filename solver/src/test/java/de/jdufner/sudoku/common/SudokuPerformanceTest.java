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
package de.jdufner.sudoku.common;

import org.apache.commons.math3.stat.inference.TTest;
import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.common.validator.SudokuValidator;
import de.jdufner.sudoku.common.validator.impl.ParallelSudokuValidator;
import de.jdufner.sudoku.common.validator.impl.SerialSudokuValidator;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SudokuPerformanceTest extends AbstractSolverTestCase {
  private static final Logger LOG = Logger.getLogger(SudokuPerformanceTest.class);
  private static final int ITERATIONS = 1000;
  private static final int TEST_ITERATIONS = 10;
  private static double[] parallelResults = new double[TEST_ITERATIONS];
  private static double[] serialResults = new double[TEST_ITERATIONS];

  private Grid sudoku = null;
  private SudokuValidator serial = new SerialSudokuValidator();
  private SudokuValidator parallel = new ParallelSudokuValidator();
  private long startTime = 0;
  private long endTime = 0;

  @Override
  public void setUp() throws Exception {
    sudoku = SudokuFactory.INSTANCE.buildSudoku(Examples.SCHWER);
  }

  public void testIsValid() throws IllegalArgumentException {
    for (int j = 0; j < TEST_ITERATIONS; j++) {
      startTime = System.currentTimeMillis();
      for (int i = 0; i < ITERATIONS; i++) {
        parallel.isValid(sudoku);
      }
      endTime = System.currentTimeMillis();
      parallelResults[j] = endTime - startTime;
      LOG.info("Dauer isValidParallel(): " + (endTime - startTime) + " ms");
    }

    for (int j = 0; j < TEST_ITERATIONS; j++) {
      startTime = System.currentTimeMillis();
      for (int i = 0; i < ITERATIONS; i++) {
        serial.isValid(sudoku);
      }
      endTime = System.currentTimeMillis();
      serialResults[j] = endTime - startTime;
      LOG.info("Dauer isValidSerial(): " + (endTime - startTime) + " ms");
    }

    TTest ttest = new TTest();
    LOG.info("homoscedasticT=" + ttest.homoscedasticT(parallelResults, serialResults));
    LOG.info("pairedT=" + ttest.pairedT(parallelResults, serialResults));
    LOG.info("t=" + ttest.t(parallelResults, serialResults));

    // analyseTTest();
  }

  public void analyseTTest() throws IllegalArgumentException {
    double[] hundredResults = new double[TEST_ITERATIONS];
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      hundredResults[i] = 100;
    }
    double[] hundredOneResults = new double[TEST_ITERATIONS];
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      hundredOneResults[i] = 101;
    }
    double[] hundredTenResults = new double[TEST_ITERATIONS];
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      hundredTenResults[i] = 110;
    }
    double[] hundredFiftyResults = new double[TEST_ITERATIONS];
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      hundredFiftyResults[i] = 150;
    }
    double[] twoHundredResults = new double[TEST_ITERATIONS];
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      twoHundredResults[i] = 200;
    }
    TTest ttest = new TTest();
    LOG.info("100, 100: homoscedasticT=" + ttest.homoscedasticT(hundredResults, hundredResults));
    LOG.info("100, 100: pairedT=" + ttest.pairedT(hundredResults, hundredResults));
    LOG.info("100, 100: t=" + ttest.t(hundredResults, hundredResults));
    LOG.info("100, 101: homoscedasticT=" + ttest.homoscedasticT(hundredResults, hundredOneResults));
    LOG.info("100, 101: pairedT=" + ttest.pairedT(hundredResults, hundredOneResults));
    LOG.info("100, 101: t=" + ttest.t(hundredResults, hundredOneResults));
    LOG.info("100, 110: homoscedasticT=" + ttest.homoscedasticT(hundredResults, hundredTenResults));
    LOG.info("100, 110: pairedT=" + ttest.pairedT(hundredResults, hundredTenResults));
    LOG.info("100, 110: t=" + ttest.t(hundredResults, hundredTenResults));
    LOG.info("100, 150: homoscedasticT=" + ttest.homoscedasticT(hundredResults, hundredFiftyResults));
    LOG.info("100, 150: pairedT=" + ttest.pairedT(hundredResults, hundredFiftyResults));
    LOG.info("100, 150: t=" + ttest.t(hundredResults, hundredFiftyResults));
    LOG.info("100, 200: homoscedasticT=" + ttest.homoscedasticT(hundredResults, twoHundredResults));
    LOG.info("100, 200: pairedT=" + ttest.pairedT(hundredResults, twoHundredResults));
    LOG.info("100, 200: t=" + ttest.t(hundredResults, twoHundredResults));
  }

}
