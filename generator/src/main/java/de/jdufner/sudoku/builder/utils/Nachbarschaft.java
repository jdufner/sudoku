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
package de.jdufner.sudoku.builder.utils;

import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-28
 * @version $Revision$
 */
public final class Nachbarschaft {

  private static final Logger LOG = Logger.getLogger(Nachbarschaft.class);

  private Literal subjekt;
  private Collection<Literal> nachbarn;

  public Nachbarschaft(Cell subjekt, Collection<Cell> nachbarn) {
    this.subjekt = subjekt.getValue();
    this.nachbarn = new HashSet<Literal>();
    for (Cell cell : nachbarn) {
      this.nachbarn.add(cell.getValue());
    }
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof Nachbarschaft) {
      Nachbarschaft that = (Nachbarschaft) other;
      if (this.subjekt.equals(that.subjekt) //
          && this.nachbarn.size() == that.nachbarn.size() //
          && this.nachbarn.containsAll(that.nachbarn)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return subjekt.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(subjekt).append(" ").append(nachbarn);
    return sb.toString();
  }

}
