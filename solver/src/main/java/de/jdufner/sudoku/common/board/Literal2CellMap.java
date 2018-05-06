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
package de.jdufner.sudoku.common.board;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * Erstellt eine Map in der jedes Literal als Schlüssel dient und als Wert eine Liste der Zellen hat, in welchen das
 * Literal existiert.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class Literal2CellMap {

  private final Map<Literal, SortedSet<Cell>> map = new HashMap<Literal, SortedSet<Cell>>(); // NOPMD by Jürgen on 11.11.09 22:11

  /**
   * Erzeugt eine Map aus den übergebenen Zellen.
   * 
   * TODO Sollte der Parameter nicht besser Unit sein?
   * 
   * @param cells
   */
  public Literal2CellMap(final Collection<Cell> cells) {
    for (Cell cell : cells) {
      for (Literal literal : cell.getCandidates()) {
        if (map.get(literal) == null) {
          map.put(literal, new TreeSet<Cell>()); // NOPMD by Jürgen on 11.11.09 22:14
        }
        map.get(literal).add(cell);
      }
    }
  }

  /**
   * Gibt die Anzahl der Zellen zurück, die das übergebene Literal enthalten. Wenn das Literal nicht vorhanden ist, wird
   * der Wert 0 zurückgegeben. Es findet keine Prüfung statt, ob das übergeben Literal innerhalb definierter Grenzen
   * liegt.
   * 
   * @param literal
   *          Gesuchtes Literal. Keine Prüfung, ob das Literal innerhalb definierter Grenzen liegt.
   * @return Die Anzahl der Zellen, die das übergebene Literal enthalten. Wenn das Literal nicht vorhanden ist, wird der
   *         Wert 0 zurückgegeben.
   */
  public int getNumber(final Literal literal) {
    if (getCellsContainingLiteral(literal) == null) {
      return 0;
    }
    return getCellsContainingLiteral(literal).size();
  }

  public SortedSet<Cell> getCellsContainingLiteral(final Literal literal) {
    return map.get(literal);
  }

  /**
   * Gibt die Kandidaten zurück, die in der übergebenen Anzahl in der Map vorkommen.
   * 
   * @param number
   *          Die Häufigkeit, wie oft ein Literal vorkommen soll.
   * @return Die Kandidaten, die in der übergebenen Anzahl in der Map vorkommen.
   */
  public SortedSet<Literal> getCandidatesByNumber(final int number) {
    final SortedSet<Literal> literals = new TreeSet<Literal>();
    for (Literal literal : map.keySet()) {
      if (map.get(literal).size() == number) {
        literals.add(literal);
      }
    }
    return literals;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder(); // NOPMD by Jürgen on 11.11.09 22:52
    for (Literal literal : map.keySet()) {
      sb.append(literal).append(": ");
      for (Cell cell : map.get(literal)) {
        sb.append(cell);
        if (!map.get(literal).last().equals(cell)) {
          sb.append(", ");
        }
      }
      sb.append("; ");
    }
    return super.toString();
  }
}
