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
package de.jdufner.sudoku.solver.strategy;

import de.jdufner.sudoku.commands.AbstractCommand;
import de.jdufner.sudoku.common.misc.Level;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2009-12-27
 * @version $Revision$
 */
public interface Strategy {

  /**
   * @return Gibt den Schwierigkeitsgrad der Strategie zurück.
   */
  Level getLevel();

  /**
   * Führt eine konkrete Strategie.
   * 
   * @return Gibt beliebig viele {@link AbstractCommand}-Objekte zurück.
   */
  StrategyResult execute();

  /**
   * @return <code>true</code>, wenn für das bearbeitete Sudoku genau ein Lösung
   *         existiert, sonst <code>false</code>.
   */
  boolean isSudokuUnique();

}
