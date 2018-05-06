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
