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
 * 
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
