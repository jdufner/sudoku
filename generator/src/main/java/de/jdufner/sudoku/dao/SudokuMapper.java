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
package de.jdufner.sudoku.dao;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.XsudokuUtils;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.service.Solution;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * 
 */
public final class SudokuMapper {

  private SudokuMapper() {
  }

  public static Grid map(SudokuData sudokuData) {
    return SudokuFactory.INSTANCE.buildSudoku(sudokuData.getSudokuAsString());
  }

  public static SudokuData map(Solution solution) {
    SudokuData sudokuData = new SudokuData();
    map(sudokuData, solution);
    return sudokuData;
  }

  public static void map(SudokuData to, Solution from) {
    to.setFixed(from.getQuest().getNumberOfFixed());
    to.setLevel(from.getLevel().getValue());
    to.setSize(from.getQuest().getSize().getHouseSize());
    to.setSudokuAsString(from.getQuest().toShortString());
    to.setxSudoku(XsudokuUtils.isXsudoku(from.getResult()) ? "J" : "N");
    to.setStrategySimple(from.getNumberSuccessfulCommand(StrategyNameEnum.SIMPLE));
    to.setStrategyHiddenSingle(from.getNumberSuccessfulCommand(StrategyNameEnum.HIDDEN_SINGLE));
    to.setStrategyNakedPair(from.getNumberSuccessfulCommand(StrategyNameEnum.NAKED_PAIR));
    to.setStrategyNakedTriple(from.getNumberSuccessfulCommand(StrategyNameEnum.NAKED_TRIPLE));
    to.setStrategyNakedQuad(from.getNumberSuccessfulCommand(StrategyNameEnum.NAKED_QUAD));
    to.setStrategyHiddenPair(from.getNumberSuccessfulCommand(StrategyNameEnum.HIDDEN_PAIR));
    to.setStrategyHiddenTriple(from.getNumberSuccessfulCommand(StrategyNameEnum.HIDDEN_TRIPLE));
    to.setStrategyHiddenQuad(from.getNumberSuccessfulCommand(StrategyNameEnum.HIDDEN_QUAD));
    to.setStrategyIntersectionRemoval(from.getNumberSuccessfulCommand(StrategyNameEnum.INTERSECTION_REMOVAL));
    to.setStrategyYwing(from.getNumberSuccessfulCommand(StrategyNameEnum.YWING));
    to.setStrategyXwing(from.getNumberSuccessfulCommand(StrategyNameEnum.XWING));
    to.setStrategySwordfish(from.getNumberSuccessfulCommand(StrategyNameEnum.SWORDFISH));
    to.setStrategyJellyfish(from.getNumberSuccessfulCommand(StrategyNameEnum.JELLYFISH));
    to.setStrategyBacktracking(from.getNumberSuccessfulCommand(StrategyNameEnum.BACKTRACKING));
  }

}
