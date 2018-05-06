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

import java.util.List;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.dao.SudokuDao;
import de.jdufner.sudoku.dao.SudokuData;
import de.jdufner.sudoku.dao.SudokuMapper;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * Applikation zum Starten der SudokuDBMaintenance. Die Klasse SudokuDBMaintenance liest die Sudokus aus der Datenbank,
 * führt eine Logik auf dem Sudokus aus und aktualisiert das Sudoku in der Datenbank.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-02-26
 * @version $Revision$
 */
public final class SudokuDbMaintenance extends AbstractMainClass {

  private static final Logger LOG = Logger.getLogger(SudokuDbMaintenance.class);

  /**
   * Instanziert und startet den SudokuDbMaintenance.
   * 
   * @param args
   *          Es werden keine Parameter ausgewertet.
   */
  public static void main(String[] args) throws Exception {
    SudokuDbMaintenance sudokuDbMaintenance = new SudokuDbMaintenance();
    sudokuDbMaintenance.start();
  }

  /**
   * Implementiert die Logik und ruft den Service zum Laden, Berechnen und Speichern des Sudokus.
   */
  protected void run() {
    LOG.debug("START");
    SudokuDao sudokuDao = (SudokuDao) GeneratorServiceFactory.INSTANCE.getBean(SudokuDao.class);
    ExtendedSolver solver = (ExtendedSolver) SolverServiceFactory.INSTANCE
        .getBean(SolverServiceFactory.STRATEGY_SOLVER_WITH_BACKTRACKING);
    boolean weitereObjekteVorhanden = true;
    int index = 0;
    int number = 10;
    do {
      List<SudokuData> sudokuDataList = sudokuDao.findSudokus(index, number);
      for (SudokuData sudokuData : sudokuDataList) {
        Grid sudoku = SudokuFactory.INSTANCE.buildSudoku(sudokuData.getSudokuAsString());
        Solution solution = solver.getSolution(sudoku);
        SudokuMapper.map(sudokuData, solution);
      }
      index += sudokuDataList.size();
      LOG.debug("Index: " + index);
      if (sudokuDataList.size() < number) {
        weitereObjekteVorhanden = false;
      }
      sudokuDao.update(sudokuDataList);
    } while (weitereObjekteVorhanden);
    LOG.debug("ENDE");
  }

}
