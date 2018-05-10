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
package de.jdufner.sudoku.generator.service;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.dao.SudokuData;
import de.jdufner.sudoku.generator.pdf.PdfPrinter;
import de.jdufner.sudoku.generator.pdf.PdfSolution;
import de.jdufner.sudoku.generator.service.PdfGeneratorConfiguration.Page;
import de.jdufner.sudoku.generator.text.JavascriptGenerator;
import de.jdufner.sudoku.generator.text.RegExpReplacer;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.solver.service.Solution;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-20
 * @version $Revision$
 */
public final class PdfGeneratorService {

  private PdfPrinter pdfPrinter;
  private ExtendedSolver solver;
  private String fileDirectory;
  private String filePattern;

  public void generate(PdfGeneratorConfiguration config) throws DocumentException, IOException {
    final String fileBaseName = new SimpleDateFormat(getFilePattern()).format(new Date());
    final String filePathName = getFileDirectory() + System.getProperty("file.separator");
    final String packageFileBaseName = fileBaseName + ".pdf";
    final String packageFileName = filePathName + packageFileBaseName;
    final String frontpageFileBaseName = fileBaseName + "_Frontpage.pdf";
    final String frontpageFileName = filePathName + frontpageFileBaseName;
    final String questsFileBaseName = fileBaseName + "_Quests.pdf";
    final String questsFileName = filePathName + questsFileBaseName;
    final String resultsFileBaseName = fileBaseName + "_Results.pdf";
    final String resultsFileName = filePathName + resultsFileBaseName;
    final String htmlFileBaseName = fileBaseName + ".html";
    final String htmlFileName = filePathName + htmlFileBaseName;
    final StringBuilder javascriptSudokus = new StringBuilder();
    final List<SudokuData> allSudokuQuests = new ArrayList<SudokuData>();
    for (Page page : config.getPages()) {
      List<SudokuData> sudokus = null;
//          getSudokuDao().findSudokus(config.getSize(), page.getLevel(), page.getNumber(), Boolean
//              .FALSE);
      allSudokuQuests.addAll(sudokus);
    }
    getPdfPrinter().printQuests(allSudokuQuests, questsFileName);

    final List<PdfSolution> allSolutions = new ArrayList<PdfSolution>(allSudokuQuests.size());
    final List<SudokuData> allSudokuResults = new ArrayList<SudokuData>(allSudokuQuests.size());
    int index = 0;
    for (SudokuData sudokuData : allSudokuQuests) {
      SudokuData sudokuData2 = new SudokuData();
      Solution solution = solver.getSolution(SudokuFactory.INSTANCE.buildSudoku(sudokuData.getSudokuAsString()));
      allSolutions.add(new PdfSolution(sudokuData, solution));
      sudokuData2.setId(sudokuData.getId());
      sudokuData2.setLevel(sudokuData.getLevel());
      sudokuData2.setSize(sudokuData.getSize());
      sudokuData2.setSudokuAsString(solution.getResult().toString());
      if (index != 0) {
        javascriptSudokus.append(",");
      }
      javascriptSudokus.append(JavascriptGenerator.toJavascript(sudokuData.getId(), solution));
      allSudokuResults.add(sudokuData2);
      index++;
    }
    getPdfPrinter().printFrontpage(fileBaseName, allSolutions, frontpageFileName);
    getPdfPrinter().printResults(allSudokuResults, resultsFileName);

    Date now = new Date();
    for (SudokuData sudoku : allSudokuQuests) {
      //getSudokuDao().updatePrintedAt(sudoku.getId(), now);
    }
    RegExpReplacer.replace(htmlFileName, fileBaseName, javascriptSudokus.toString());

    packPdf(filePathName, packageFileBaseName, frontpageFileBaseName, questsFileBaseName, resultsFileBaseName,
        htmlFileBaseName);
  }

  public void packPdf(String filePathName, String packageFileBaseName, String frontpageFileBaseName,
      String questsFileBaseName, String resultsFileBaseName, String htmlFileName) throws FileNotFoundException,
      DocumentException, IOException {
    PdfStamper pdfStamper = new PdfStamper(new PdfReader(filePathName + frontpageFileBaseName), new FileOutputStream(
        filePathName + packageFileBaseName));
    pdfStamper.addFileAttachment("Quests", null, filePathName + questsFileBaseName, questsFileBaseName);
    pdfStamper.addFileAttachment("Results", null, filePathName + resultsFileBaseName, resultsFileBaseName);
    pdfStamper.addFileAttachment("HTML", null, filePathName + htmlFileName, htmlFileName);
    pdfStamper.makePackage(PdfName.T);
    pdfStamper.close();
  }

  //
  // Spring-Wiring
  //

  public PdfPrinter getPdfPrinter() {
    return pdfPrinter;
  }

  public void setPdfPrinter(PdfPrinter pdfPrinter) {
    this.pdfPrinter = pdfPrinter;
  }

  public ExtendedSolver getSolver() {
    return solver;
  }

  public void setSolver(ExtendedSolver solver) {
    this.solver = solver;
  }

  public String getFileDirectory() {
    return fileDirectory;
  }

  public void setFileDirectory(String fileDirectory) {
    this.fileDirectory = fileDirectory;
  }

  public String getFilePattern() {
    return filePattern;
  }

  public void setFilePattern(String filePattern) {
    this.filePattern = filePattern;
  }

}
