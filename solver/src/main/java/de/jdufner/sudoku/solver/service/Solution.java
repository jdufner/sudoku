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


package de.jdufner.sudoku.solver.service;

import java.util.Collection;
import java.util.List;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandUtils;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.StrategyResult;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Diese Klasse beschreibt eine Lösung eines Sudokus. Eine Lösung setzt sich aus mehreren Einzelschritten zusammen. Eine
 * vorhandene Lösung ist eine nur eine Lösung, möglicherweise existieren weitere Lösungen, sowohl in dem Sinne, dass
 * dieselbe Lösung anders erzeugt werden kann durch andere Lösungsschritte als auch, dass möglicherweise das Sudoku
 * unterbestimmt ist und weitere Lösungen existieren.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public class Solution {

  private final transient Grid quest;
  private transient Grid result;
  private transient boolean unique;
  private transient Level level;
  private transient List<StrategyResult> results;

  /**
   * @param quest
   *          Das Start-Sudoku, für das die Lösung berechnet werden. Hier muss schon eine Kopie / Klon übergeben werden.
   */
  public Solution(final Grid quest) {
    this.quest = quest;
  }

  public int getNumberSuccessfulCommand(final StrategyNameEnum strategyNameEnum) {
    int number = 0;
    for (StrategyResult strategyResult : results) {
      for (Command command : strategyResult.getCommands()) {
        if (command.isSuccessfully() && command.getStrategyName().equals(strategyNameEnum.name())) {
          number++;
        }
      }
    }
    return number;
  }

  public Grid getQuest() {
    return quest;
  }

  public Grid getResult() {
    return result;
  }

  public void setResult(final Grid result) {
    this.result = result;
  }

  public boolean isUnique() {
    return unique;
  }

  public void setUnique(final boolean unique) {
    this.unique = unique;
  }

  public Level getLevel() {
    return level;
  }

  public void setLevel(final Level level) {
    this.level = level;
  }

  public List<StrategyResult> getResults() {
    return results;
  }

  public void setResults(final List<StrategyResult> results) {
    this.results = results;
  }

  @Override
  public String toString() {
    final String lineSeparator = System.getProperty("line.separator");
    final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append(lineSeparator);
    sb.append("Rätsel: ").append(getQuest()).append(lineSeparator);
    sb.append("Lösung: ").append(getResult()).append(lineSeparator);
    sb.append("Eindeutigkeit: ").append(isUnique()).append(lineSeparator);
    sb.append("Schwierigkeitsgrad: ").append(getLevel()).append(lineSeparator);

    int step = 0;
    if (getResult() == null) {
      sb.append("Kein Ergebnis gefunden.");
    } else {
      for (StrategyResult strategyResult : getResults()) {
        step += 1;
        sb.append(step).append(". Zwischenschritt(").append(strategyResult.getNumberFixedBefore()).append(" Zellen /")
            .append(strategyResult.getNumberCandidatesBefore()).append(" Kandidaten): ").append(
                strategyResult.getSudokuBefore()).append(lineSeparator);
        final Collection<? extends Command> cmds = CommandUtils.aggregateCommandsIfPossible(strategyResult
            .getCommands());
        sb.append(strategyResult.getStrategyName()).append('(').append(cmds.size()).append(')').append(": ");
        boolean first = true;
        for (Command cmd : cmds) {
          if (first) {
            first = false;
          } else {
            sb.append(',');
          }
          sb.append(cmd.toJavascriptString());
        }
        sb.append(lineSeparator);
      }
    }
    return sb.toString();
  }

}
