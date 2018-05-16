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

import java.util.Stack;

import de.jdufner.sudoku.common.board.Grid;

/**
 * Der CommandManager führt Befehle ({@link AbstractCommand}) aus oder macht sie rückgängig, wenn möglich.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class CommandManager {

  /**
   * Die bereits ausgeführten Befehle ({@link Command})
   */
  private final transient Stack<Command> previousCommands = new Stack<Command>();
  /**
   * Die noch auszuführenden Befehle ({@link Command}).
   */
  private final transient Stack<Command> nextCommands = new Stack<Command>();

  /**
   * Führt den nächsten Befehl aus.
   * 
   * @param sudoku
   *          Das {@link Grid}, auf dem der Befehl ({@link AbstractCommand}) auszuführen ist.
   * @return Der oberste Befehl ({@link AbstractCommand}) auf dem Stapel.
   */
  public Command redoCommand(final Grid sudoku) {
    Command command = null;
    if (isRedoPossible()) {
      command = nextCommands.pop();
      command.execute(sudoku);
      previousCommands.push(command);
    }
    return command;
  }

  /**
   * @return <code>true</code>, wenn Befehle ({@link AbstractCommand}) zur Ausführung vorliegen, sonst
   *         <code>false</code>.
   */
  public boolean isRedoPossible() {
    return !nextCommands.isEmpty();
  }

  /**
   * Macht den letzten Befehl ({@link AbstractCommand})rückgängig.
   * 
   * @param sudoku
   *          Das {@link Grid}, auf dem der Befehl ({@link AbstractCommand}) rückgängig gemacht werden soll.
   * @return Der der letzte Befehl ({@link AbstractCommand}), der rückgängig zu machen ist.
   */
  public Command undoCommand(final Grid sudoku) {
    Command command = null;
    if (isUndoPossible()) {
      command = previousCommands.pop();
      command.unexecute(sudoku);
      nextCommands.push(command);
    }
    return command;
  }

  /**
   * @return <code>true</code>, wenn Befehle ({@link AbstractCommand}) rückgängig gemacht werden können, sonst
   *         <code>false</code>.
   */
  public boolean isUndoPossible() {
    return !previousCommands.isEmpty();
  }

  /**
   * @param sudoku
   * @param command
   */
  public void doCommand(final Grid sudoku, final Command command) {
    command.execute(sudoku);
    previousCommands.push(command);
    nextCommands.clear();
  }

}
