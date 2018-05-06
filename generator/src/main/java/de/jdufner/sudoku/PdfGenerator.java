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
