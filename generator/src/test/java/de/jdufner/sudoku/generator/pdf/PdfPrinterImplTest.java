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
package de.jdufner.sudoku.generator.pdf;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.lowagie.text.DocumentException;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.dao.SudokuData;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-08
 * @version $Revision$
 */
public final class PdfPrinterImplTest extends TestCase {

  private static final String EXAMPLE = ".5..9...2.6..2...8..7...9...81.............5...69.3......7.2..5...6..4..8.9.5...3";

  public void testPrint() throws FileNotFoundException, DocumentException {
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
}
