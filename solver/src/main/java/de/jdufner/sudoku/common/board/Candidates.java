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

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;


/**
 * Die Klasse Candidates ist die normale Repräsentation der Kandidaten einer
 * Zelle {@link Cell}. Daneben gibt es nocht die sortierten Kandidaten
 * {@link Candidates}, die allerdings nur für spezielle Algorithmen benötigt
 * werden. Diese Implementierung wird standardmäßig verwendet, weil die
 * {@link #add(Object)}-Operation schneller sind.
 * 
 * TODO Künftig nicht mehr von {@link ArrayList} ableiten. Problem: Literale
 * können doppelt hinzugefügt werden.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * @see <a
 *      href="http://sudopedia.org/wiki/Candidate">http://sudopedia.org/wiki/Candidate</a>
 */
public final class Candidates<T extends Comparable<? super T>> extends ExtendedTreeSet<T> implements Cloneable {

  private static final Logger LOG = Logger.getLogger(Candidates.class);

  /**
   * 
   */
  private static final long serialVersionUID = -797343079629100287L;

  /**
   * Standardkonstruktor
   */
  public Candidates() {
    super();
  }

  /**
   * Konstruktor für eine Collection.
   * 
   * @param collection
   *          Collection mit Elementen, kann auch leer sein.
   */
  public Candidates(final Collection<T> collection) {
    super(collection);
  }

  /**
   * @param cell
   * @return <code>true</code>, genau dann wenn die Kandidaten der mit denen des
   *         übergebenen Felds gleich sind, sonst <code>false</code>.
   */
  public boolean isEquals(final Collection<T> that) {
    if (this == that) {
      return true;
    }
    if (that == null) {
      return false;
    }
    if (size() == that.size() && containsAll(that) && that.containsAll(this)) {
      return true;
    }
    return false;
  }

  /**
   * @param testCandidates
   * @return <code>true</code>, genau dann wenn höchstens die übergebenen
   *         Testkandidaten vorhanden in den Kandidaten der Zelle enthalten
   *         sind, sonst <code>false</code>, also es existiert mindestens ein
   *         Zellkandidat, der nicht in der Liste der Testkandidaten enthalten
   *         ist.
   */
  public boolean containsAtMost(final Collection<T> testCandidates) {
    for (T thisCandidate : this) {
      if (!testCandidates.contains(thisCandidate)) {
        return false;
      }
    }
    return true;
  }

  /**
   * @param testCandidates
   * @return <code>true</code>, genau dann wenn mindestens die übergebenen
   *         Testkandidaten in den Kandidaten der Zelle enthalten sind, sonst
   *         <code>false</code>, also es existiert mindestens ein Testkandidat,
   *         der in der Liste der Zellkandidaten nicht enthalten ist.
   */
  public boolean containsAtLeastOneOf(final Collection<T> testCandidates) {
    for (T testCandidate : testCandidates) {
      if (this.contains(testCandidate)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean add(final T element) {
    if (contains(element)) {
      LOG.info(this + " enthält bereits " + element + " und wird nicht erneut hinzugefügt.");
      return false;
    } else {
      return super.add(element);
    }
  }

  @Override
  public Candidates<T> clone() {
    return (Candidates<T>) super.clone();
  }

}
