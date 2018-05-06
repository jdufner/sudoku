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
package de.jdufner.sudoku.common.validator.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.validator.SudokuValidator;
import de.jdufner.sudoku.context.SolverServiceFactory;

/**
 * Prüft mit einem parallel Algorithmus ob das Sudoku gültig ist.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class ParallelSudokuValidator implements SudokuValidator {

  private static final Logger LOG = Logger.getLogger(Grid.class);

  @Override
  public boolean isValid(final Grid sudoku) {
    try {
      final AtomicBoolean validity = new AtomicBoolean(true);
      final Collection<UnitValidChecker> checkTasks = new ArrayList<UnitValidChecker>();
      checkTasks.add(new UnitValidChecker(validity, sudoku.getBlocks()));
      checkTasks.add(new UnitValidChecker(validity, sudoku.getColumns()));
      checkTasks.add(new UnitValidChecker(validity, sudoku.getRows()));
      SolverServiceFactory.INSTANCE.getExecutorService().invokeAll(checkTasks);
      return validity.get();
    } catch (InterruptedException ie) {
      LOG.error(ie.getMessage(), ie);
      return false;
    }
  }

}
