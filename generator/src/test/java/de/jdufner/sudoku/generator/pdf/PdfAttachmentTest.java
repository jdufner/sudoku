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
