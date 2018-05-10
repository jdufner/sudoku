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
package de.jdufner.sudoku.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import de.jdufner.sudoku.test.AbstractGeneratorTestCase;
import org.apache.log4j.Logger;

public class SudokuDataTest extends AbstractGeneratorTestCase {

  private static final Logger LOG = Logger.getLogger(SudokuData.class);

  public void testEqualsAndHashCode() {
    SudokuData sudokuData1 = new SudokuData();
    sudokuData1.setId(1);
    SudokuData sudokuData2 = new SudokuData();
    sudokuData2.setId(1);
    SudokuData sudokuData3 = new SudokuData();
    sudokuData3.setId(1);
    assertEquals(sudokuData1.hashCode(), sudokuData2.hashCode());
    assertEquals(sudokuData2.hashCode(), sudokuData3.hashCode());
    assertEquals(sudokuData1.hashCode(), sudokuData3.hashCode());
    assertEquals(sudokuData1, sudokuData2);
    assertEquals(sudokuData2, sudokuData3);
    assertEquals(sudokuData1, sudokuData3);
    assertNotNull(sudokuData1.toString());
    LOG.debug(sudokuData1);
  }

}
