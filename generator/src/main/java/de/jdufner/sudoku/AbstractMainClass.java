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

import org.apache.log4j.Logger;

import de.jdufner.sudoku.context.SolverServiceFactory;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-03-06
 * @version $Revision$
 */
public abstract class AbstractMainClass {

  private static Logger LOG = Logger.getLogger(AbstractMainClass.class);

  /**
   * Startet die Applikation und führt die allgemeine Logik vorher und nachher aus.
   * 
   * @throws Exception
   */
  protected void start() throws Exception {
    setUp();
    run();
    tearDown();
  }

  /**
   * Allgemeine Logik vor dem Ausführen der Applikation.
   */
  protected void setUp() {

  }

  /**
   * Allgemeine Logik nach dem Ausführen der Applikation.
   */
  protected void tearDown() {
    LOG.debug("ExecutorService runterfahren");
    SolverServiceFactory.INSTANCE.shutdown();
  }

  /**
   * Methode, welche die eigentliche Logik der Applikation enthält.
   * 
   * @throws Exception
   */
  protected abstract void run() throws Exception;

}
