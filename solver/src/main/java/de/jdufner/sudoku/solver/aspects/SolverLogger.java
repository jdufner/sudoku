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
package de.jdufner.sudoku.solver.aspects;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SolverLogger implements MethodInterceptor {

  private static final Logger LOG = Logger.getLogger(SolverLogger.class);
  private static final String ERGEBNIS = "Rätsel ";
  private static final String GELOEST = "gelöst!";
  private static final String NICHT_GELOEST = "nicht gelöst!";

  public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
    final Grid sudoku = (Grid) methodInvocation.getArguments()[0];
    LOG.info(ERGEBNIS + sudoku.toShortString());
    final Object obj = methodInvocation.proceed();
    if (obj instanceof Solution) {
      final Solution solution = (Solution) obj;
      LOG.info(solution);
    } else if (obj instanceof Grid) {
      final Grid result = (Grid) obj;
      if (result == null) {
        LOG.info(ERGEBNIS + NICHT_GELOEST);
      } else {
        LOG.info(ERGEBNIS + GELOEST + " " + result.toShortString());
      }
    } else {
      LOG.info(ERGEBNIS + obj.toString());
      // TODO Warum?
      // throw new IllegalStateException("Methode wurde mit unerwartetem Parameter (nicht " + Sudoku.class.getName()
      // + ", sondern " + obj.getClass().getName() + ") aufgerufen. " + obj.toString());
    }
    return obj;
  }
}
