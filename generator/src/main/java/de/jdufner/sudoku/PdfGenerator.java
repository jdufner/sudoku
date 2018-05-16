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

import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.generator.service.PdfGeneratorConfiguration;
import de.jdufner.sudoku.generator.service.PdfGeneratorService;

/**
 * Applikation zum Starten des PdfGenerators. Die Klasse liest die Sudokus aus
 * der Datenbank und erzeugt ein PDF mit den Rätsel und den Lösungen. Nebenbei
 * werden auch noch die Lösungswege in Textdateien gespeichert und komprimiert.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-02-26
 * @version $Revision$
 */
public final class PdfGenerator extends AbstractMainClass {

  /**
   * Instanziert und startet den PdfGenerator.
   * 
   * @param args
   *          Es werden keine Parameter ausgewertet.
   */
  public static void main(String[] args) throws Exception {
    PdfGenerator pdfGenerator = new PdfGenerator();
    pdfGenerator.start();
  }

  /**
   * Implementiert die Logik und ruft den Service und Laden und Erzeugen des
   * PDFs auf.
   */
  protected void run() throws Exception {
    PdfGeneratorService pdfGeneratorService = (PdfGeneratorService) GeneratorServiceFactory.INSTANCE
        .getBean(PdfGeneratorService.class);
    // pdfGeneratorService.generate(new
    // PdfGeneratorConfiguration.Builder().numberPerLevel(Level.LEICHT, 24)
    // .numberPerLevel(Level.MITTEL, 24).numberPerLevel(Level.SCHWER,
    // 24).build());
    pdfGeneratorService.generate(new PdfGeneratorConfiguration.Builder().numberPerLevel(Level.LEICHT, 1).build());
  }
}
