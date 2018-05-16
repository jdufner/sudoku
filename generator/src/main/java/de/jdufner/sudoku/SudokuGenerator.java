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

// $Id$
package de.jdufner.sudoku;

import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.generator.service.SudokuGeneratorService;

/**
 * Applikation zum Starten des SudokuGenerators. Der SudokuGenerator arbeitet selbständig und speichert die erzeugten
 * Sudokus in der Datenbank ab.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-27
 * @version $Revision$
 */
public final class SudokuGenerator extends AbstractMainClass {

  /**
   * Instanziert und startet den SudokuGenerator.
   * 
   * @param args
   *          Es werden keine Parameter ausgewertet.
   */
  public static void main(String[] args) throws Exception {
    SudokuGenerator sudokuGenerator = new SudokuGenerator();
    sudokuGenerator.start();
  }

  /**
   * Implementiert die Logik und ruft den SudokuGeneratorService auf.
   */
  protected void run() {
    SudokuGeneratorService sudokuGeneratorService = (SudokuGeneratorService) GeneratorServiceFactory.INSTANCE
        .getBean(SudokuGeneratorService.class);
    while (true) {
      sudokuGeneratorService.generate();
    }
  }

}
