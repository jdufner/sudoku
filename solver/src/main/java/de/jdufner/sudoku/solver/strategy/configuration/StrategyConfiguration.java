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
 * 
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
