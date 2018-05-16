/*
 * Sudoku is a puzzle game. It solves and generates puzzles in different
 * formats.
 * Copyright (C) 2008-2018  Juergen Dufner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package de.jdufner.sudoku;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.dao.SudokuData;
import de.jdufner.sudoku.dao.SudokuMapper;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.solver.service.Solution;
import java.util.List;
import org.apache.log4j.Logger;

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
    ExtendedSolver solver = (ExtendedSolver) SolverServiceFactory.INSTANCE
        .getBean(SolverServiceFactory.STRATEGY_SOLVER_WITH_BACKTRACKING);
    boolean weitereObjekteVorhanden = true;
    int index = 0;
    int number = 10;
    do {
      List<SudokuData> sudokuDataList = null; //sudokuDao.findSudokus(index, number);
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
//      sudokuDao.update(sudokuDataList);
    } while (weitereObjekteVorhanden);
    LOG.debug("ENDE");
  }

}
