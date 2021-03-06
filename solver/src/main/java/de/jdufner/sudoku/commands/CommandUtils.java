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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.CellUtils;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.SudokuSize;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 14.03.2010
 * 
 */
public final class CommandUtils {

  private static final Logger LOG = Logger.getLogger(CommandUtils.class);

  private CommandUtils() {
  }

  public static boolean isEqual(final Collection<Command> commands1, final Collection<Command> commands2) {
    return isEqualSet(commands1, commands2);
  }

  private static boolean isEqualCollection(final Collection<Command> commands1, final Collection<Command> commands2) {
    return (commands1.containsAll(commands2) && commands2.containsAll(commands1));
  }

  private static boolean isEqualSet(final Collection<Command> commands1, final Collection<Command> commands2) {
    final Set<Command> cmd1 = new HashSet<Command>(commands1);
    final Set<Command> cmd2 = new HashSet<Command>(commands2);
    return (cmd1.containsAll(cmd2) && cmd2.containsAll(cmd1));
  }

  @SuppressWarnings("unchecked")
  public static Collection<? extends Command> aggregateCommandsIfPossible(final Collection<? extends Command> commands) {
    if (!commands.isEmpty()) {
      Command cmd = commands.iterator().next();
      if (cmd instanceof RemoveCandidatesCommand) {
        return aggregateCommands((Collection<RemoveCandidatesCommand>) commands);
      }
    }
    return commands;
  }

  public static Collection<RemoveCandidatesCommand> aggregateCommands(final Collection<RemoveCandidatesCommand> commands) {
    final Map<Integer, Collection<RemoveCandidatesCommand>> cell2CommandMap = buildCell2CommandMap(commands);
    final List<RemoveCandidatesCommand> aggregatedCmds = new ArrayList<RemoveCandidatesCommand>();
    for (Integer cellNumber : cell2CommandMap.keySet()) {
      final Set<Literal> removeCandidates = new TreeSet<Literal>();
      LOG.debug("Ersetze in Zelle " + cellNumber + " die Commands: " + cell2CommandMap.get(cellNumber));
      for (RemoveCandidatesCommand cmd : cell2CommandMap.get(cellNumber)) {
        final RemoveCandidatesCommand removeCmd = cmd;
        removeCandidates.addAll(removeCmd.getCandidates());
      }
      int index = 0;
      for (final Iterator<RemoveCandidatesCommand> cmdIt = cell2CommandMap.get(cellNumber).iterator(); cmdIt.hasNext();) {
        final RemoveCandidatesCommand removeCmd = cmdIt.next();
        index += 1;
        if (index == 1) {
          removeCmd.setCandidates(removeCandidates);
          LOG.debug("Durch neues Command: " + removeCmd);
          aggregatedCmds.add(removeCmd);
        } else {
          cmdIt.remove();
        }
      }
    }
    return aggregatedCmds;
  }

  private static <T extends Command> Map<Integer, Collection<T>> buildCell2CommandMap(final Collection<T> commands) {
    final Map<Integer, Collection<T>> cell2CommandMap = new HashMap<Integer, Collection<T>>();
    Class<?> cmdClass = null;
    for (T cmd : commands) {
      if (cmdClass == null) {
        cmdClass = cmd.getClass();
      }
      if (!cmdClass.equals(cmd.getClass())) {
        throw new IllegalStateException(
            "Achtung hier existiert eine Collection mit unterschiedlichen Commands. Erwartet wurde " + cmdClass
                + ", aber war " + cmd.getClass());
      }
      final Integer cellNumber = CellUtils.getNumber(cmd.getRowIndex(), cmd.getColumnIndex(), SudokuSize.NEUN);
      if (cell2CommandMap.get(cellNumber) == null) {
        cell2CommandMap.put(cellNumber, new ArrayList<T>());
      }
      cell2CommandMap.get(cellNumber).add(cmd);
    }
    return cell2CommandMap;
  }

}
