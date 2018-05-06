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
package de.jdufner.sudoku;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandManager;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.file.PropertiesLoader;
import de.jdufner.sudoku.solver.service.ExtendedSolver;

/**
 * Das Spiel ({@link Game}) ist die zentrale Schnittstelle zur Steuerung des Sudokus.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class Game {

  final private int id;
  final private Grid quest;
  final private Grid solution;
  final private CommandManager commandManager;

  public Game(final int id) {
    this.id = id;

    // quest = SudokuFactory.buildSudoku(Examples.ING_DIBA);

    final PropertiesLoader propertiesLoader = new PropertiesLoader();
    final String sudokuAsString = propertiesLoader.getSudokuAsString(id);
    quest = SudokuFactory.INSTANCE.buildSudoku(sudokuAsString);
    ExtendedSolver extendedSolver = (ExtendedSolver) SolverServiceFactory.INSTANCE
        .getBean(SolverServiceFactory.STRATEGY_SOLVER_WITH_BACKTRACKING);
    solution = extendedSolver.solve(quest);

    commandManager = new CommandManager();
  }

  public int getId() {
    return id;
  }

  public Grid getQuest() {
    return quest;
  }

  public Grid getSolution() {
    return solution;
  }

  public Cell doCommand(final Command command) {
    commandManager.doCommand(quest, command);
    return quest.getCell(command.getRowIndex(), command.getColumnIndex());
  }

  public Cell undo() {
    final Command command = commandManager.undoCommand(quest);
    return quest.getCell(command.getRowIndex(), command.getColumnIndex());
  }

  public Cell redo() {
    final Command command = commandManager.redoCommand(quest);
    return quest.getCell(command.getRowIndex(), command.getColumnIndex());
  }

  public boolean isUndoPossible() {
    return commandManager.isUndoPossible();
  }

  public boolean isRedoPossible() {
    return commandManager.isRedoPossible();
  }

  public boolean isCorrect(final int rowIndex, final int columnIndex) {
    final Cell questCell = quest.getCell(rowIndex, columnIndex);
    final Cell solutionCell = solution.getCell(rowIndex, columnIndex);
    return questCell.equals(solutionCell);
  }

}
