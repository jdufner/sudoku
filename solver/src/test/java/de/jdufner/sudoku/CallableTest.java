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
package de.jdufner.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class CallableTest extends AbstractSolverTestCase {
  private static final Logger LOG = Logger.getLogger(CallableTest.class);
  private ExecutorService executorService;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    executorService = Executors.newFixedThreadPool(3);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    executorService.shutdown();
  }

  public void testIsDone() throws InterruptedException {
    long start = System.currentTimeMillis(), end = System.currentTimeMillis();
    LOG.info("a) " + (end - start) + " ms");
    List<WaitSeconds> waiter = new ArrayList<WaitSeconds>();
    waiter.add(new WaitSeconds(2));
    waiter.add(new WaitSeconds(4));
    waiter.add(new WaitSeconds(6));
    end = System.currentTimeMillis();
    LOG.info("b) " + (end - start) + " ms");
    List<Future<Boolean>> results = executorService.invokeAll(waiter);
    end = System.currentTimeMillis();
    LOG.info("c) " + (end - start) + " ms");
    if (results.get(0).isDone() && results.get(1).isDone() && results.get(2).isDone()) {
      end = System.currentTimeMillis();
      LOG.info("d) " + (end - start) + " ms");
    }
    end = System.currentTimeMillis();
    LOG.info("e) " + (end - start) + " ms");
  }

  private class WaitSeconds implements Callable<Boolean> {
    private int seconds = 10;

    private WaitSeconds(int seconds) {
      this.seconds = seconds;
    }

    @Override
    public Boolean call() {
      long start = 0, end = 0;
      try {
        start = System.currentTimeMillis();
        Thread.sleep(seconds * 1000);
        end = System.currentTimeMillis();
      } catch (InterruptedException ie) {
        CallableTest.LOG.error(ie.getMessage(), ie);
      } finally {
        CallableTest.LOG.error((end - start) + " ms waited.");
      }
      return false;
    }
  }

}
