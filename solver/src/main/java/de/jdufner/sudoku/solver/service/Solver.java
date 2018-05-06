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
package de.jdufner.sudoku.solver.service;

import de.jdufner.sudoku.common.board.Grid;

/**
 * Einfache Service zum Lösen von Sudokus.
 * 
 * Es existieren zwei einfache Implementierungen dazu:
 * <ul>
 * <li>Backtracking-Variante (brute force)</li>
 * <li>Intelligente Variante (aktuell nur eine parallele Implementierung)</li>
 * </ul>
 * 
 * TODO Muss ich diese Liste selbst schreiben oder kann das javadoc selbst erledigen? Eigentlich gehört das ja nicht in
 * diese Klasse.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public interface Solver {

  /**
   * Liefert <code>true</code> zurück, wenn ein Sudoku überhaupt eine Lösung, sonst <code>false</code>. Der
   * Rechenaufwand ist derselbe wie bei {@link #solve(Grid)}
   * 
   * TODO throws InvalidSudokuException
   * 
   * @param sudoku
   * @return <code>true</code> if Sudoku is solvable, else <code>false</code>
   */
  boolean isSolvable(Grid sudoku);

  /**
   * Finds a solution of the Sudoku, if exists. Maybe there are more than one solution.
   * 
   * TODO throws InvalidSudokuException
   * 
   * TODO throws NoSolutionFoundException
   * 
   * @param sudoku
   * @return A solution, if exists.
   */
  Grid solve(Grid sudoku);

  /**
   * Liefert <code>true</code> zurück, wenn genau eine Lösung existiert, sonst <code>false</code>.
   * 
   * TODO throws InvalidSudokuException
   * 
   * TODO throws NoSolutionFoundException
   * 
   * @param sudoku
   * @return <code>true</code>, wenn das Sudoku genau eine Lösung hat, sonst <code>false</code>.
   */
  boolean isUnique(Grid sudoku);

}
