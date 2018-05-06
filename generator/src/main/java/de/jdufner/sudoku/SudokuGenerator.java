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
