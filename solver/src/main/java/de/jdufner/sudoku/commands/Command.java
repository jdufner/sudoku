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
package de.jdufner.sudoku.commands;

import de.jdufner.sudoku.common.board.Grid;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since
 * @version $Revision$
 */
public interface Command {

  /**
   * Führt den Befehl auf dem übergebenen {@link Grid} aus.
   * 
   * TODO Prüfen, dass nach Ausführung eines Commands die Zelle, der Block, die Spalte und die Reihe valide sind.
   * 
   * @param sudoku
   *          Das Sudoku auf dem der Befehl ausgeführt werden soll.
   */
  void execute(Grid sudoku);

  /**
   * Macht den Befehl auf dem übergebenen {@link Grid} rückgängig.
   * 
   * @param sodoku
   *          Das Sudoku auf dem der Befehl rückgängig gemacht werden soll.
   */
  void unexecute(Grid sudoku);

  /**
   * Gibt an, ob der Befehl rückgängig gemacht werden kann.
   * 
   * @return <code>true</code>, wenn der Befehl rückgängig gemacht werden kann, sonst <code>false</code>.
   */
  boolean reversible();

  /**
   * Gibt an, ob der Befehl erfolgreich (es wurde mindestens eine Zelle oder Kandidat verändert) ausgeführt wurde.
   * 
   * @return <code>true</code>, wenn der Befehl erfolgreich ausgeführt wurde und mindestens ein Kandidat entfernt oder
   *         eine Zelle gesetzt wurde, sonst <code>false</code>.
   */
  boolean isSuccessfully();

  /**
   * Liefert das Ergebnis der {@link #toString()}-Methode zum Zeitpunkt der Erstellung des Befehls zurück.
   * 
   * @return Das Ergebnis der {@link #toString()}-Methode zum Zeitpunkt der Erstellung des Befehls.
   */
  String getFrozenString();

  /**
   * TODO Typesafe machen!!!
   * 
   * Gibt den Erzeuger an. Ist im allgemeinen der Name einer Strategie oder der Client.
   * 
   * @return Der Erzeuger des Befehls.
   */
  String getStrategyName();

  /**
   * TODO Dokumentieren!
   * 
   * @return
   */
  int getRowIndex();

  /**
   * TODO Dokumentieren!
   * 
   * @return
   */
  int getColumnIndex();

  String toJavascriptString();

}
