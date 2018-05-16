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
package de.jdufner.sudoku.generator.pdf;

import java.awt.Color;
import java.util.Properties;

import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import de.jdufner.sudoku.common.board.BoxUtils;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.SudokuSize;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-07
 * @version $Revision$
 */
public final class PdfCellHandler implements CellHandler {

  private SudokuSize sudokuSize;
  private Properties pdfStyle;

  private PdfPTable table = null;

  public PdfCellHandler(SudokuSize sudokuSize, Properties pdfStyle) {
    this.sudokuSize = sudokuSize;
    this.pdfStyle = pdfStyle;
  }

  @Override
  public void handleCell(Cell cell) {
    PdfPCell pdfCell = null;
    if (cell.isFixed()) {
      pdfCell = new PdfPCell(new Paragraph(cell.getValue().toString()));
    } else {
      if (Boolean.getBoolean(getPdfStyle().getProperty("sudoku.board.candidates.print"))) {
        pdfCell = new PdfPCell(buildCandidates());
      } else {
        pdfCell = new PdfPCell();
      }
    }
    formatZelle(cell.getRowIndex(), cell.getColumnIndex(), pdfCell);
    table.addCell(pdfCell);
  }

  @Override
  public void initialize() {
    table = new PdfPTable(getSudokuSize().getHouseSize());
  }

  public PdfPTable getTable() {
    return table;
  }

  private boolean isEvenBlockIndex(final int rowIndex, final int columnIndex) {
    if (BoxUtils.getBlockIndexByRowIndexAndColumnIndex(rowIndex, columnIndex, getSudokuSize()) % 2 == 0) {
      return true;
    }
    return false;
  }

  private void formatZelle(final int zeile, final int spalte, final PdfPCell cell) {
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell.setBorderColor(new Color(//
        Integer.parseInt(getPdfStyle().getProperty("sudoku.board.border.color.red")), //
        Integer.parseInt(getPdfStyle().getProperty("sudoku.board.border.color.green")), //
        Integer.parseInt(getPdfStyle().getProperty("sudoku.board.border.color.blue"))));

    if (isEvenBlockIndex(zeile, spalte)) {
      cell.setBackgroundColor(new Color(//
          Integer.parseInt(getPdfStyle().getProperty("sudoku.board.background.odd.color.red")), //
          Integer.parseInt(getPdfStyle().getProperty("sudoku.board.background.odd.color.green")), //
          Integer.parseInt(getPdfStyle().getProperty("sudoku.board.background.odd.color.blue"))));
    } else {
      cell.setBackgroundColor(new Color(//
          Integer.parseInt(getPdfStyle().getProperty("sudoku.board.background.even.color.red")), //
          Integer.parseInt(getPdfStyle().getProperty("sudoku.board.background.even.color.green")), //
          Integer.parseInt(getPdfStyle().getProperty("sudoku.board.background.even.color.blue"))));
    }
    cell.setBorderWidth(PdfConstants.RAHMEN_DUENN);
    if (BoxUtils.isFirstRowInBlock(zeile, getSudokuSize())) {
      cell.setBorderWidthTop(PdfConstants.RAHMEN_DICK);
    } else {
      cell.setBorderWidthTop(PdfConstants.RAHMEN_DUENN);
    }
    if (BoxUtils.isLastColumnInRow(spalte, getSudokuSize())) {
      cell.setBorderWidthRight(PdfConstants.RAHMEN_DICK);
    } else {
      cell.setBorderWidthRight(PdfConstants.RAHMEN_DUENN);
    }
    if (BoxUtils.isLastRowInColumn(zeile, getSudokuSize())) {
      cell.setBorderWidthBottom(PdfConstants.RAHMEN_DICK);
    } else {
      cell.setBorderWidthBottom(PdfConstants.RAHMEN_DUENN);
    }
    if (BoxUtils.isFirstColumnInBlock(spalte, getSudokuSize())) {
      cell.setBorderWidthLeft(PdfConstants.RAHMEN_DICK);
    } else {
      cell.setBorderWidthLeft(PdfConstants.RAHMEN_DUENN);
    }
    cell.setFixedHeight(27f);
  }

  /**
   * 
   * @return 3x3 Tabelle gefüllt mit Kandidaten 1-9
   */
  private PdfPTable buildCandidates() {
    final float CANDIDATE_FONT_SIZE = 6f;
    final float CANDIDATE_PADDING = 1f;
    PdfPTable candidates = new PdfPTable(3);
    PdfPCell[][] candidate = new PdfPCell[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Paragraph p = new Paragraph(String.valueOf(i * 3 + j + 1));
        p.getFont().setSize(CANDIDATE_FONT_SIZE);
        candidate[i][j] = new PdfPCell(p);
        candidate[i][j].setPadding(CANDIDATE_PADDING);
        candidate[i][j].setHorizontalAlignment(Element.ALIGN_CENTER);
        candidate[i][j].setVerticalAlignment(Element.ALIGN_MIDDLE);
        candidate[i][j].setBorderColor(new Color(PdfConstants.RAHMEN_FARBE[0], PdfConstants.RAHMEN_FARBE[1],
            PdfConstants.RAHMEN_FARBE[2]));
        candidate[i][j].setBorderWidth(PdfConstants.RAHMEN_KEIN);
        if (i > 0) {
          candidate[i][j].setBorderWidthTop(PdfConstants.RAHMEN_DUENN);
        }
        if (j > 0) {
          candidate[i][j].setBorderWidthLeft(PdfConstants.RAHMEN_DUENN);
        }
        candidates.addCell(candidate[i][j]);
      }
    }
    return candidates;
  }

  public SudokuSize getSudokuSize() {
    return sudokuSize;
  }

  public void setSudokuSize(SudokuSize sudokuSize) {
    this.sudokuSize = sudokuSize;
  }

  public Properties getPdfStyle() {
    return pdfStyle;
  }

  public void setPdfStyle(Properties pdfStyle) {
    this.pdfStyle = pdfStyle;
  }

}
