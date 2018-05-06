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

import java.util.HashSet;
import java.util.Set;

/**
 * Diese Klasse repräsentiert die Konfiguration eines Sudoku-Knackers. Die Konfiguration ist selbst unabhängig von der
 * Implementierung der Lösungsstrategien. Sie kennt allerdings die Lösungstechniken, die für die Lösung von Sudokus
 * angewendet werden können.
 * 
 * Derzeit ist es noch unklar, ob die Lösungstechniken in einer bestimmten Reihenfolge ausgeführt werden müssen oder die
 * Auswahl der Lösungstechniken vollkommen frei ist. Das soll bedeuten, wenn in der Konfiguration die Lösungstechnik
 * {@link StrategyNameEnum#XWING} ausgewählt wurde, ob vorher irgendwelche anderen Lösungstechniken ausprobiert werden
 * müssen.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 2009-11-24
 * @version $Revision$
 */
public final class StrategyConfiguration {

  private final transient Set<StrategyNameEnum> strategies = new HashSet<StrategyNameEnum>();
  private final transient StrategyThreadingEnum threading;

  /**
   * Erzeugt eine Konfiguration. Es ist eine zwingende Angabe, ob die Lösung serial oder parallel berechnet werden soll.
   * 
   * @param strategyThreadingEnum
   */
  public StrategyConfiguration(final StrategyThreadingEnum strategyThreadingEnum) {
    threading = strategyThreadingEnum;
  }

  /**
   * Fügt eine Lösungstechnik ein.
   * 
   * @param strategyNameEnum
   * @return Die Konfiguration selbst, wodurch Fluent-API möglich sein soll.
   */
  public StrategyConfiguration add(final StrategyNameEnum strategyNameEnum) {
    strategies.add(strategyNameEnum);
    return this;
  }

  /**
   * Entfernt eine Lösungstechnik.
   * 
   * @param strategyNameEnum
   * @return Die Konfiguration selbst, wodurch Fluent-API möglich sein soll.
   */
  public StrategyConfiguration remove(final StrategyNameEnum strategyNameEnum) {
    strategies.remove(strategyNameEnum);
    return this;
  }

  /**
   * Fügt ein Array von Lösungstechniken ein.
   * 
   * @param strategyNameEnums
   * @return Die Konfiguration selbst, wodurch Fluent-API möglich sein soll.
   */
  public StrategyConfiguration add(final StrategyNameEnum[] strategyNameEnums) {
    for (StrategyNameEnum strategyNameEnum : strategyNameEnums) {
      strategies.add(strategyNameEnum);
    }
    return this;
  }

  /**
   * @return Die Menge (keine doppelten) der aktuell ausgewählten Lösungstechniken. Wenn keine eingefügt wurde, wird
   *         eine leere Menge zurück geliefert.
   */
  public Set<StrategyNameEnum> getStrategies() {
    return strategies;
  }

  /**
   * 
   * @return Die ausgewählte Nebenläufigkeit.
   */
  public StrategyThreadingEnum getThreading() {
    return threading;
  }

}
