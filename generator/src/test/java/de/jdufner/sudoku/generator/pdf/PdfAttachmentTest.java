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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import de.jdufner.sudoku.test.AbstractGeneratorTestCase;

public final class PdfAttachmentTest extends AbstractGeneratorTestCase {

  public void testAttach1() throws Exception {
    Document document = new Document(PageSize.A4, 10, 10, 10, 10);
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:\\tmp\\PdfAttachmentTest.pdf"));
    document.open();
    document.addCreationDate();
    document.addCreator("de.jdufner.sudoku.Generator");
    document.add(new Paragraph("PdfAttachmentTest"));
    document.close();
    writer.addFileAttachment("Eingebundenes HTML", null, "D:\\tmp\\Sudoku.html", "Sudoku.html");
    writer.close();
  }

  private StringBuffer readFile() throws FileNotFoundException, IOException {
    FileInputStream fis = new FileInputStream("D:\\tmp\\Sudoku.html");
    InputStreamReader isr = new InputStreamReader(fis);
    BufferedReader br = new BufferedReader(isr);
    String line = null;
    StringBuffer sb = new StringBuffer();
    do {
      line = br.readLine();
      sb.append(line);
    } while (line != null);
    br.close();
    return sb;
  }

  public void testAttach2() throws Exception {
    StringBuffer sb = readFile();
    Document document = new Document(PageSize.A4, 10, 10, 10, 10);
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:\\tmp\\PdfAttachmentTest.pdf"));
    document.open();
    document.addCreationDate();
    document.addCreator("de.jdufner.sudoku.Generator");
    document.add(new Paragraph("PdfAttachmentTest"));
    document.close();
    writer.addFileAttachment("Eingebundenes HTML", sb.toString().getBytes(), null, "Sudoku.html");
    writer.close();
  }

  public void testAttach3() throws Exception {
    PdfStamper pdfStamper = new PdfStamper(new PdfReader("D:\\tmp\\Sudoku.pdf"), new FileOutputStream(
        "D:\\tmp\\PdfAttachmentTest.pdf"));
    pdfStamper.addFileAttachment("Eingebundenes HTML", null, "D:\\tmp\\Sudoku.html", "Sudoku.html");
    //pdfStamper.makePackage(PdfName.T);
    pdfStamper.close();
  }
}
