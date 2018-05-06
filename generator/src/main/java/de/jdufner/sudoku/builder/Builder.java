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
package de.jdufner.sudoku.builder;

import java.util.Map;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public interface Builder {

  /**
   * Bevor der {@link Builder} mittels {@link #build()} oder {@link #buildSudokus()} ausgeführt wird, muss die
   * gewünschte Größe gesetzt werden.
   * 
   * @param sudokuSize
   */
  public void setSize(SudokuSize sudokuSize);

  /**
   * @return Gibt das letzte und damit das schwierigste {@link Grid} zurück.
   */
  public Grid build();

  /**
   * Gibt das letzte jeweils zu einem Schwierigkeitsgrad ({@link Level}) gefundene {@link Grid} zurück. Wird zu einem
   * {@link Level} kein Sudoku gefunden, wird dafür eben auch keins zurückgegeben, mit anderen Worten es werden also
   * maximal soviele {@link Grid} zurückgegeben, wie es {@link Level} gibt.
   * 
   * @return Eine Map, bestehend aus dem {@link Level} und dem letzen zu diesem {@link Level} gefundenen
   *         {@link Solution} inkl. {@link Grid}.
   */
  public Map<Level, Solution> buildSudokus();

}
