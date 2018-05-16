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


package de.jdufner.sudoku.generator.pdf;

import java.io.FileNotFoundException;
import java.util.List;

import com.lowagie.text.DocumentException;

import de.jdufner.sudoku.dao.SudokuData;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-08
 * @version $Revision$
 */
public interface PdfPrinter {

  void printFrontpage(String name, List<PdfSolution> solutions, String fileName) throws DocumentException,
      FileNotFoundException;

  /**
   * 
   * @param sudokus
   * @param fileName
   */
  void printQuests(List<SudokuData> sudokus, String fileName) throws DocumentException, FileNotFoundException;

  void printResults(List<SudokuData> sudokus, String fileName) throws DocumentException, FileNotFoundException;

}
