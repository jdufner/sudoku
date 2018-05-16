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
package de.jdufner.sudoku.generator.pdf;

import java.util.Date;
import java.util.List;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.dao.SudokuData;
import de.jdufner.sudoku.solver.service.Solution;
import de.jdufner.sudoku.solver.strategy.StrategyResult;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

public final class PdfSolution {

  private SudokuData sudokuData;
  private Solution solution;

  public PdfSolution(SudokuData sudokuData, Solution solution) {
    this.sudokuData = sudokuData;
    this.solution = solution;
  }

  public Level getLevel() {
    return solution.getLevel();
  }

  public int getNumberSuccessfulCommand(StrategyNameEnum strategyNameEnum) {
    return solution.getNumberSuccessfulCommand(strategyNameEnum);
  }

  public Grid getQuest() {
    return solution.getQuest();
  }

  public Grid getResult() {
    return solution.getResult();
  }

  public List<StrategyResult> getResults() {
    return solution.getResults();
  }

  public boolean isUnique() {
    return solution.isUnique();
  }

  public void setLevel(Level level) {
    solution.setLevel(level);
  }

  public void setResult(Grid result) {
    solution.setResult(result);
  }

  public void setResults(List<StrategyResult> results) {
    solution.setResults(results);
  }

  public void setUnique(boolean unique) {
    solution.setUnique(unique);
  }

  public Date getCreated() {
    return sudokuData.getCreated();
  }

  public int getFixed() {
    return sudokuData.getFixed();
  }

  public Date getGeneratedAt() {
    return sudokuData.getGeneratedAt();
  }

  public int getId() {
    return sudokuData.getId();
  }

  public Date getModified() {
    return sudokuData.getModified();
  }

  public Date getPrintedAt() {
    return sudokuData.getPrintedAt();
  }

  public int getSize() {
    return sudokuData.getSize();
  }

  public int getStrategyBacktracking() {
    return sudokuData.getStrategyBacktracking();
  }

  public int getStrategyHiddenPair() {
    return sudokuData.getStrategyHiddenPair();
  }

  public int getStrategyHiddenQuad() {
    return sudokuData.getStrategyHiddenQuad();
  }

  public int getStrategyHiddenSingle() {
    return sudokuData.getStrategyHiddenSingle();
  }

  public int getStrategyHiddenTriple() {
    return sudokuData.getStrategyHiddenTriple();
  }

  public int getStrategyIntersectionRemoval() {
    return sudokuData.getStrategyIntersectionRemoval();
  }

  public int getStrategyJellyfish() {
    return sudokuData.getStrategyJellyfish();
  }

  public int getStrategyNakedPair() {
    return sudokuData.getStrategyNakedPair();
  }

  public int getStrategyNakedQuad() {
    return sudokuData.getStrategyNakedQuad();
  }

  public int getStrategyNakedTriple() {
    return sudokuData.getStrategyNakedTriple();
  }

  public int getStrategySimple() {
    return sudokuData.getStrategySimple();
  }

  public int getStrategySwordfish() {
    return sudokuData.getStrategySwordfish();
  }

  public int getStrategyXwing() {
    return sudokuData.getStrategyXwing();
  }

  public int getStrategyYwing() {
    return sudokuData.getStrategyYwing();
  }

  public String getSudokuAsString() {
    return sudokuData.getSudokuAsString();
  }

  public String getxSudoku() {
    return sudokuData.getxSudoku();
  }

  public void setCreated(Date erstellt) {
    sudokuData.setCreated(erstellt);
  }

  public void setFixed(int fixed) {
    sudokuData.setFixed(fixed);
  }

  public void setGeneratedAt(Date generatedAt) {
    sudokuData.setGeneratedAt(generatedAt);
  }

  public void setId(int id) {
    sudokuData.setId(id);
  }

  public void setLevel(int level) {
    sudokuData.setLevel(level);
  }

  public void setModified(Date geaendert) {
    sudokuData.setModified(geaendert);
  }

  public void setPrintedAt(Date printedAt) {
    sudokuData.setPrintedAt(printedAt);
  }

  public void setSize(int size) {
    sudokuData.setSize(size);
  }

  public void setStrategyBacktracking(int strategyBacktracking) {
    sudokuData.setStrategyBacktracking(strategyBacktracking);
  }

  public void setStrategyHiddenPair(int strategyHiddenPair) {
    sudokuData.setStrategyHiddenPair(strategyHiddenPair);
  }

  public void setStrategyHiddenQuad(int strategyHiddenQuad) {
    sudokuData.setStrategyHiddenQuad(strategyHiddenQuad);
  }

  public void setStrategyHiddenSingle(int strategyHiddenSingle) {
    sudokuData.setStrategyHiddenSingle(strategyHiddenSingle);
  }

  public void setStrategyHiddenTriple(int strategyHiddenTriple) {
    sudokuData.setStrategyHiddenTriple(strategyHiddenTriple);
  }

  public void setStrategyIntersectionRemoval(int strategyIntersectionRemoval) {
    sudokuData.setStrategyIntersectionRemoval(strategyIntersectionRemoval);
  }

  public void setStrategyJellyfish(int strategyJellyfish) {
    sudokuData.setStrategyJellyfish(strategyJellyfish);
  }

  public void setStrategyNakedPair(int strategyNakedPair) {
    sudokuData.setStrategyNakedPair(strategyNakedPair);
  }

  public void setStrategyNakedQuad(int strategyNakedQuad) {
    sudokuData.setStrategyNakedQuad(strategyNakedQuad);
  }

  public void setStrategyNakedTriple(int strategyNakedTriple) {
    sudokuData.setStrategyNakedTriple(strategyNakedTriple);
  }

  public void setStrategySimple(int strategySimple) {
    sudokuData.setStrategySimple(strategySimple);
  }

  public void setStrategySwordfish(int strategySwordfish) {
    sudokuData.setStrategySwordfish(strategySwordfish);
  }

  public void setStrategyXwing(int strategyXwing) {
    sudokuData.setStrategyXwing(strategyXwing);
  }

  public void setStrategyYwing(int strategyYwing) {
    sudokuData.setStrategyYwing(strategyYwing);
  }

  public void setSudokuAsString(String sudokuAsString) {
    sudokuData.setSudokuAsString(sudokuAsString);
  }

  public void setxSudoku(String xSudoku) {
    sudokuData.setxSudoku(xSudoku);
  }

}
