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

import com.lowagie.text.DocumentException;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.dao.SudokuData;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import junit.framework.TestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-08
 * 
 */
public final class PdfPrinterImplTest extends TestCase {

  private static final String EXAMPLE = ".5..9...2.6..2...8..7...9...81.............5...69.3......7.2..5...6..4..8.9.5...3";

  public void _testPrint() throws Exception {
    SudokuData sudokuData = new SudokuData();
    sudokuData.setSudokuAsString(EXAMPLE);
    Grid s = SudokuFactory.INSTANCE.buildSudoku(sudokuData.getSudokuAsString());
    sudokuData.setFixed(s.getNumberOfFixed());
    sudokuData.setGeneratedAt(new Date());
    sudokuData.setLevel(5);
    sudokuData.setSize(s.getSize().getHouseSize());
    List<SudokuData> sudokus = new ArrayList<SudokuData>();
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    String fileName = "D:\\tmp\\PdfPrinterImplTest.pdf";
    PdfPrinter pdfPrinter = (PdfPrinter) GeneratorServiceFactory.INSTANCE.getBean(PdfPrinter.class);
    pdfPrinter.printQuests(sudokus, fileName);
  }

  public void testDummy() {
    assertTrue(true);
  }

}
