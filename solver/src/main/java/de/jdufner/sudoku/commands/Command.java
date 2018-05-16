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


package de.jdufner.sudoku.commands;

import de.jdufner.sudoku.common.board.Grid;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since
 * 
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
