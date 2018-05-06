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
