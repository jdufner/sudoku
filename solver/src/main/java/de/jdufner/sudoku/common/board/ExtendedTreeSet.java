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
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Das ist eine Erweiterung des Klasse {@link TreeSet} und bietet weitere
 * Methoden an um Elemente aus einem TreeSet, also einer sortierten Menge, zu
 * holen.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * @param <E>
 *          Der Typ von den Elementen innerhalb des {@link Set}.
 */
public class ExtendedTreeSet<E extends Comparable<? super E>> extends TreeSet<E> implements Cloneable {

  /**
   * 
   */
  private static final long serialVersionUID = 7383868479762527323L;

  /**
   * Standardkonstruktor
   */
  public ExtendedTreeSet() {
    super();
  }

  /**
   * Konstruktor für eine Collection.
   * 
   * @param collection
   *          Collection mit Elementen, kann auch leer sein.
   */
  public ExtendedTreeSet(final Collection<E> collection) {
    super(collection);
  }

  /**
   * Gibt das nächstgrößere Element als das übergebene zurück. Wenn kein
   * größeres Element existiert, wird <code>null</code> geliefert.
   * 
   * @param searchElement
   * @return
   */
  public E getSmallestElementGreatherThan(final E searchElement) {
    final SortedSet<E> tailSet = tailSet(searchElement);
    if (tailSet.isEmpty()) {
      return null;
    }
    final E firstElement = tailSet.first();
    if (firstElement.compareTo(searchElement) > 0) {
      return firstElement;
    } else {
      if (tailSet.size() <= 1) {
        return null;
      }
      final Iterator<E> iterator = tailSet.iterator();
      int index = 0;
      while (iterator.hasNext()) {
        final E nextElement = iterator.next();
        index++;
        if (index >= 2) {
          return nextElement;
        }
      }
    }
    return null;
  }

  /**
   * Liefert die ersten <code>size</code> Elemente. Wenn
   * <code>smallerAllowed == TRUE</code>, dann wird eine kleine Menge geliefert.
   * 
   * @param size
   * @param smallerAllowed
   * @return
   */
  public SortedSet<E> headSetBySize(final int size, final boolean smallerAllowed) {
    final SortedSet<E> headSet = new ExtendedTreeSet<E>();
    if (smallerAllowed) {
      if (size() < size) {
        headSet.addAll(this);
        return headSet;
      }
    } else {
      if (size() < size) {
        return null;
      }
    }
    if (size() == size) {
      headSet.addAll(this);
      return headSet;
    }
    final Iterator<E> iterator = iterator();
    int counter = 0;
    while (iterator.hasNext() && counter < size) {
      headSet.add(iterator.next());
      counter++;
    }
    return headSet;
  }

  /**
   * Gibt die <code>numberOfElements</code> nächstgrößeren Element nach
   * <code>e</code> zurück. Wenn es weniger als die gewünschte Anzahl
   * <code>numberOfElements</code> von Elementen gibt, wird <code>null</code>
   * geliefert.
   * 
   * @param searchElement
   * @param numberOfElements
   * @return
   */
  public SortedSet<E> getSmallestElementsGreatherThan(final E searchElement, final int numberOfElements) {
    final ExtendedTreeSet<E> tailSet = new ExtendedTreeSet<E>();
    tailSet.addAll(tailSet(searchElement));
    if (tailSet.isEmpty()) {
      return null;
    }
    final E firstElement = tailSet.first();
    if (firstElement.equals(searchElement)) {
      tailSet.remove(firstElement);
    }
    if (tailSet.size() < numberOfElements) {
      return null;
    }
    return tailSet.headSetBySize(numberOfElements, false);
  }

  /**
   * Gibt das größte Element, das kleiner ist als das übergebene Element
   * <code>e</code> ist, zurück.
   * 
   * @param searchElement
   * @return
   */
  public E getGreatestElementSmallerThan(final E searchElement) {
    final SortedSet<E> headSet = headSet(searchElement);
    if (headSet.isEmpty()) {
      return null;
    }
    return headSet.last();
  }

  /**
   * <code>TRUE</code>, wenn in der Menge mindestends ein Element enthalten ist,
   * das größer sind als <code>e</code>.
   * 
   * @param searchElement
   * @return <code>true</code>, wenn es mindestens ein Element existiert, das
   *         größer ist als das Übergebene, sonst <code>false</code>.
   */
  public boolean containsGreaterElementThan(final E searchElement) {
    if (last().compareTo(searchElement) > 0) {
      return true;
    }
    return false;
  }

  public E get(final int searchIndex) {
    if (searchIndex < 0 || searchIndex >= size()) {
      throw new IllegalArgumentException("Ungültiger Index " + searchIndex + " liegt nicht zwischen 0 (inklusiv) und "
          + size() + " (exklusiv).");
    }
    final Iterator<E> iterator = iterator();
    int index = 0;
    while (iterator.hasNext()) {
      final E type = iterator.next();
      if (index == searchIndex) {
        return type;
      }
      index++;
    }
    return null;
  }

}
