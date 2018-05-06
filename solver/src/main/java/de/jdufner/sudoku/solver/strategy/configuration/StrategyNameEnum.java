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
package de.jdufner.sudoku.solver.strategy.configuration;

/**
 * Aufzählung aller verwendbaren Lösungstechniken.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2009-11-24
 * @version $Revision$
 */
public enum StrategyNameEnum {

  // TODO Lösungstechniken dokumentieren mit Hinweis auf sudopedia.org ?
  SIMPLE(), //
  NAKED_SINGLE(), //
  HIDDEN_SINGLE(), //
  NAKED_PAIR(), NAKED_TRIPLE(), NAKED_QUAD(), //
  HIDDEN_PAIR(), HIDDEN_TRIPLE(), HIDDEN_QUAD, //
  //POINTING_PAIR(), // Pointing Pair ist eine Unterform von Intersection Removal
  //BOX_LINE_REDUCTION(), // Box Line Reduction ist eine Unterform von Intersection Removal
  INTERSECTION_REMOVAL(), //
  YWING(), //
  XWING(), //
  SWORDFISH(), //
  JELLYFISH(), //
  BACKTRACKING();

}
