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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Entfernt alle Kandidaten {@link Candidates} von einer Zelle, außer die übergebenen Kandidaten auf der {@link Cell}.
 * Die übergebenen Kandidaten verbleiben in der Zelle.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * 
 */
public final class RetainCandidatesCommand extends AbstractCommand {

  private final static String JAVASCRIPT_COMMAND_NAME = "RM";

  /**
   * Die Kandidaten, die verbleiben sollen.
   */
  private final transient Collection<Literal> candidates;
  /**
   * Die Kandidaten, die entfernt werden sollen.
   */
  private final transient Collection<Literal> candidatesToRemove = new Candidates<Literal>();

  /**
   * Konstruktor eines Befehls, welcher alle anderen Kandiaten entfernt, außer die übergebenen.
   * 
   * @param strategyNameEnum
   *          Der Erzeuger des Befehls.
   * @param cell
   *          Die Zelle auf welcher der Befehl ausgeführt werden soll.
   * @param candidates
   *          Die Kandidaten, welche beibehalten werden sollen.
   */
  private RetainCandidatesCommand(final StrategyNameEnum strategyNameEnum, final Cell cell,
      final Collection<Literal> candidates) {
    super(strategyNameEnum);
    this.rowIndex = cell.getRowIndex();
    this.columnIndex = cell.getColumnIndex();
    this.candidates = candidates;
    for (Literal candidate : candidates) {
      if (!cell.getCandidates().contains(candidate)) {
        candidatesToRemove.add(candidate);
      }
    }
  }

  @Override
  public void executeCommand(final Grid sudoku) {
    final int numberOfCandidatesBefore = sudoku.getCell(getCell(sudoku).getNumber()).getCandidates().size();
    sudoku.getCell(getCell(sudoku).getNumber()).getCandidates().retainAll(candidates);
    final int numberOfCandatesAfter = sudoku.getCell(getCell(sudoku).getNumber()).getCandidates().size();
    if (numberOfCandatesAfter < numberOfCandidatesBefore) {
      successfully = true;
    }
  }

  @Override
  public void unexecuteCommand(final Grid sudoku) {
    getCell(sudoku).getCandidates().addAll(candidatesToRemove);
  }

  @Override
  public boolean reversible() {
    return true;
  }

  @Override
  public String toString() {
    return getStrategyName() + ": Behalte Kandidaten " + candidates + " in Zelle (" + rowIndex + ", " + columnIndex
        + ")";
  }

  @Override
  protected String toString(final Grid sudoku) {
    return getStrategyName() + ": Behalte Kandidaten " + candidates + " in Zelle " + getCell(sudoku);
  }

  @Override
  public boolean equals(final Object other) { // NOPMD Jürgen Dufner 15.03.2010
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof RetainCandidatesCommand) {
      final RetainCandidatesCommand that = (RetainCandidatesCommand) other;
      return (super.equals(other) && isEqual(this.candidates, that.candidates));
    }
    return false;
  }

  @Override
  public String toJavascriptString() {
    return getJavascriptCellNumber() + JAVASCRIPT_COMMAND_NAME + getJavascriptCandidates();
  }

  private String getJavascriptCandidates() {
    final StringBuilder sb = new StringBuilder();
    for (Literal l : candidatesToRemove) {
      sb.append(l.getValue());
    }
    return sb.toString();
  }

  public static class RetainCandidatesCommandBuilder {
    private final transient StrategyNameEnum strategyNameEnum;
    private transient Cell cell = null;
    private final transient int rowIndex;
    private final transient int columnIndex;
    private final transient Set<Literal> candidates = new HashSet<Literal>();

    public RetainCandidatesCommandBuilder(final StrategyNameEnum strategyNameEnum, final int rowIndex,
        final int columnIndex) {
      this.strategyNameEnum = strategyNameEnum;
      this.rowIndex = rowIndex;
      this.columnIndex = columnIndex;
    }

    public RetainCandidatesCommandBuilder(final StrategyNameEnum strategyNameEnum, final Cell cell) {
      this.strategyNameEnum = strategyNameEnum;
      this.cell = cell;
      this.rowIndex = -1;
      this.columnIndex = -1;
    }

    public RetainCandidatesCommandBuilder addCandidate(final Literal candiate) {
      candidates.add(candiate);
      return this;
    }

    public RetainCandidatesCommandBuilder addCandidate(final Collection<Literal> candiates) {
      candidates.addAll(candiates);
      return this;
    }

    public RetainCandidatesCommandBuilder addCandidate(final int... values) {
      for (int value : values) {
        candidates.add(Literal.getInstance(value));
      }
      return this;
    }

    public Command build() {
      if (candidates.isEmpty()) {
        throw new IllegalStateException("Es wurden keine Kandidaten hinzugefügt, Command kann nicht erzeugt werden.");
      }
      if (cell == null && rowIndex < 0 && columnIndex < 0) {
        throw new IllegalStateException(
            "Es wurde keine Zelle hinzugefügt oder Zellkoordinaten angegeben, Command kann nicht erzeugt werden.");
      }
      if (cell == null) {
        cell = new Cell(rowIndex, columnIndex, Literal.EMPTY, SudokuSize.DEFAULT);
      }
      return new RetainCandidatesCommand(strategyNameEnum, cell, candidates);
    }
  }

}
