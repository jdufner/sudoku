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
package de.jdufner.sudoku.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public enum SolverServiceFactory {

  INSTANCE;

  //private static final Logger LOG = Logger.getLogger(SolverServiceFactory.class);
  private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);

  public static final String BACKTRACKING_SOLVER = "backtrackingSolver";
  public static final String STRATEGY_SOLVER = "strategySolver";
  public static final String STRATEGY_SOLVER_WITH_BACKTRACKING = "strategySolverWithBacktracking";

  private final transient ApplicationContext applicationContext;

  private SolverServiceFactory() {
    applicationContext = new ClassPathXmlApplicationContext("solver-context.xml");
//    try {
//      Log4jConfigurer.initLogging(Log4jConfigurer.CLASSPATH_URL_PREFIX + "log4j.properties", 10000);
//    } catch (FileNotFoundException fnfe) {
//      throw new RuntimeException(fnfe); // NOPMD
//    }
  }

  public Object getBean(final String name) {
    return applicationContext.getBean(name);
  }

  public Object getBean(final Class<?> clazz) {
    final String[] beanNames = applicationContext.getBeanNamesForType(clazz);
    if (beanNames.length == 0) {
      throw new IllegalStateException("Klasse " + clazz + " existiert im Kontext "
          + applicationContext.getDisplayName() + " nicht.");
    }
    if (beanNames.length > 1) {
      throw new IllegalStateException("Klasse " + clazz + " existiert im Kontext "
          + applicationContext.getDisplayName() + " mehrfach und ist nicht eindeutig.");
    }
    return applicationContext.getBean(beanNames[0]);
  }

  public ExecutorService getExecutorService() {
    return EXECUTOR_SERVICE;
  }

  public void shutdown() {
    getExecutorService().shutdown();
  }

}
