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

// $Id$
package de.jdufner.sudoku.generator.text;

import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandUtils;
import de.jdufner.sudoku.solver.service.Solution;
import de.jdufner.sudoku.solver.strategy.StrategyResult;

/**
 * Erzeugt eine Javascript-Repräsentation der Solution. Das Javascript-Objektmodell ist im HTML-Code vorgegeben.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-04-01
 * @version $Revision$
 */
public final class JavascriptGenerator {

  private JavascriptGenerator() {
  }

  public static String toJavascript(int id, Solution solution) {
    final String lineSeparator = System.getProperty("line.separator");
    StringBuilder sb = new StringBuilder();
    sb.append("new solution(");
    sb.append(id).append(", \"").append(solution.getQuest()).append("\", \"").append(solution.getResult()).append(
        "\", \"").append(solution.getLevel().getName()).append("\", ").append(solution.isUnique()).append(", [")
        .append(lineSeparator);
    int i = 0;
    for (StrategyResult strategyResult : solution.getResults()) {
      if (i != 0) {
        sb.append(",");
      }
      sb.append("new step(");
      sb.append("\"").append(strategyResult.getStrategyName().name()).append("\", \"").append(
          strategyResult.getSudokuBefore()).append("\", ").append(strategyResult.getNumberFixedBefore()).append(", ")
          .append(strategyResult.getNumberCandidatesBefore()).append(", [");
      final Collection<? extends Command> cmds = CommandUtils.aggregateCommandsIfPossible(strategyResult.getCommands());
      int j = 0;
      for (Command cmd : cmds) {
        if (j != 0) {
          sb.append(",");
        }
        sb.append("\"").append(cmd.toJavascriptString()).append("\"");
        j++;
      }
      sb.append("])").append(lineSeparator);
      i++;
    }
    sb.append("])").append(lineSeparator);
    return sb.toString();
  }

}
