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


package de.jdufner.sudoku.builder.transformation;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * 
 */
public final class TransformationUtilTest extends TestCase {
  private static final Logger log = Logger.getLogger(TransformationUtilTest.class);

  public void testSwapColumns() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    Grid swapped = TransformationUtil.swapColumns(original, 0, 1);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(0, 1).getValue());
    assertEquals(original.getCell(1, 0).getValue(), swapped.getCell(1, 1).getValue());
    assertEquals(original.getCell(2, 0).getValue(), swapped.getCell(2, 1).getValue());
    assertEquals(original.getCell(3, 0).getValue(), swapped.getCell(3, 1).getValue());
    assertEquals(original.getCell(4, 0).getValue(), swapped.getCell(4, 1).getValue());
    assertEquals(original.getCell(5, 0).getValue(), swapped.getCell(5, 1).getValue());
    assertEquals(original.getCell(6, 0).getValue(), swapped.getCell(6, 1).getValue());
    assertEquals(original.getCell(7, 0).getValue(), swapped.getCell(7, 1).getValue());
    assertEquals(original.getCell(8, 0).getValue(), swapped.getCell(8, 1).getValue());
  }

  public void testSwapRows() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    Grid swapped = TransformationUtil.swapRows(original, 1, 2);
    assertEquals(original.getCell(2, 0).getValue(), swapped.getCell(1, 0).getValue());
    assertEquals(original.getCell(2, 1).getValue(), swapped.getCell(1, 1).getValue());
    assertEquals(original.getCell(2, 2).getValue(), swapped.getCell(1, 2).getValue());
    assertEquals(original.getCell(2, 3).getValue(), swapped.getCell(1, 3).getValue());
    assertEquals(original.getCell(2, 4).getValue(), swapped.getCell(1, 4).getValue());
    assertEquals(original.getCell(2, 5).getValue(), swapped.getCell(1, 5).getValue());
    assertEquals(original.getCell(2, 6).getValue(), swapped.getCell(1, 6).getValue());
    assertEquals(original.getCell(2, 7).getValue(), swapped.getCell(1, 7).getValue());
    assertEquals(original.getCell(2, 8).getValue(), swapped.getCell(1, 8).getValue());
  }

  public void testSwapColumBlocks() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    log.debug("original");
    log.debug(original);
    Grid swapped = TransformationUtil.swapColumnBlock(original, 1, 2);
    log.debug("swapped");
    log.debug(swapped);
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(0, 6).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(0, 7).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(0, 8).getValue());
    assertEquals(original.getCell(1, 3).getValue(), swapped.getCell(1, 6).getValue());
    assertEquals(original.getCell(1, 4).getValue(), swapped.getCell(1, 7).getValue());
    assertEquals(original.getCell(1, 5).getValue(), swapped.getCell(1, 8).getValue());
    assertEquals(original.getCell(2, 3).getValue(), swapped.getCell(2, 6).getValue());
    assertEquals(original.getCell(2, 4).getValue(), swapped.getCell(2, 7).getValue());
    assertEquals(original.getCell(2, 5).getValue(), swapped.getCell(2, 8).getValue());
    assertEquals(original.getCell(3, 3).getValue(), swapped.getCell(3, 6).getValue());
    assertEquals(original.getCell(3, 4).getValue(), swapped.getCell(3, 7).getValue());
    assertEquals(original.getCell(3, 5).getValue(), swapped.getCell(3, 8).getValue());
    assertEquals(original.getCell(4, 3).getValue(), swapped.getCell(4, 6).getValue());
    assertEquals(original.getCell(4, 4).getValue(), swapped.getCell(4, 7).getValue());
    assertEquals(original.getCell(4, 5).getValue(), swapped.getCell(4, 8).getValue());
    assertEquals(original.getCell(5, 3).getValue(), swapped.getCell(5, 6).getValue());
    assertEquals(original.getCell(5, 4).getValue(), swapped.getCell(5, 7).getValue());
    assertEquals(original.getCell(5, 5).getValue(), swapped.getCell(5, 8).getValue());
    assertEquals(original.getCell(6, 3).getValue(), swapped.getCell(6, 6).getValue());
    assertEquals(original.getCell(6, 4).getValue(), swapped.getCell(6, 7).getValue());
    assertEquals(original.getCell(6, 5).getValue(), swapped.getCell(6, 8).getValue());
    assertEquals(original.getCell(7, 3).getValue(), swapped.getCell(7, 6).getValue());
    assertEquals(original.getCell(7, 4).getValue(), swapped.getCell(7, 7).getValue());
    assertEquals(original.getCell(7, 5).getValue(), swapped.getCell(7, 8).getValue());
    assertEquals(original.getCell(8, 3).getValue(), swapped.getCell(8, 6).getValue());
    assertEquals(original.getCell(8, 4).getValue(), swapped.getCell(8, 7).getValue());
    assertEquals(original.getCell(8, 5).getValue(), swapped.getCell(8, 8).getValue());
  }

  public void testSwapRowBlocks() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    Grid swapped = TransformationUtil.swapRowBlock(original, 1, 2);
    assertEquals(original.getCell(3, 0).getValue(), swapped.getCell(6, 0).getValue());
    assertEquals(original.getCell(4, 0).getValue(), swapped.getCell(7, 0).getValue());
    assertEquals(original.getCell(5, 0).getValue(), swapped.getCell(8, 0).getValue());
    assertEquals(original.getCell(3, 1).getValue(), swapped.getCell(6, 1).getValue());
    assertEquals(original.getCell(4, 1).getValue(), swapped.getCell(7, 1).getValue());
    assertEquals(original.getCell(5, 1).getValue(), swapped.getCell(8, 1).getValue());
    assertEquals(original.getCell(3, 2).getValue(), swapped.getCell(6, 2).getValue());
    assertEquals(original.getCell(4, 2).getValue(), swapped.getCell(7, 2).getValue());
    assertEquals(original.getCell(5, 2).getValue(), swapped.getCell(8, 2).getValue());
    assertEquals(original.getCell(3, 3).getValue(), swapped.getCell(6, 3).getValue());
    assertEquals(original.getCell(4, 3).getValue(), swapped.getCell(7, 3).getValue());
    assertEquals(original.getCell(5, 3).getValue(), swapped.getCell(8, 3).getValue());
    assertEquals(original.getCell(3, 4).getValue(), swapped.getCell(6, 4).getValue());
    assertEquals(original.getCell(4, 4).getValue(), swapped.getCell(7, 4).getValue());
    assertEquals(original.getCell(5, 4).getValue(), swapped.getCell(8, 4).getValue());
    assertEquals(original.getCell(3, 5).getValue(), swapped.getCell(6, 5).getValue());
    assertEquals(original.getCell(4, 5).getValue(), swapped.getCell(7, 5).getValue());
    assertEquals(original.getCell(5, 5).getValue(), swapped.getCell(8, 5).getValue());
    assertEquals(original.getCell(3, 6).getValue(), swapped.getCell(6, 6).getValue());
    assertEquals(original.getCell(4, 6).getValue(), swapped.getCell(7, 6).getValue());
    assertEquals(original.getCell(5, 6).getValue(), swapped.getCell(8, 6).getValue());
    assertEquals(original.getCell(3, 7).getValue(), swapped.getCell(6, 7).getValue());
    assertEquals(original.getCell(4, 7).getValue(), swapped.getCell(7, 7).getValue());
    assertEquals(original.getCell(5, 7).getValue(), swapped.getCell(8, 7).getValue());
    assertEquals(original.getCell(3, 8).getValue(), swapped.getCell(6, 8).getValue());
    assertEquals(original.getCell(4, 8).getValue(), swapped.getCell(7, 8).getValue());
    assertEquals(original.getCell(5, 8).getValue(), swapped.getCell(8, 8).getValue());
  }

  public void testRotateQuarterClockwise() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    Grid swapped = TransformationUtil.rotateBlockClockwise(original);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(0, 8).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(1, 8).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(2, 8).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(3, 8).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(4, 8).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(5, 8).getValue());
  }

  public void testRotateQuarterCounterClockwise() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    Grid swapped = TransformationUtil.rotateBlockCounterClockwise(original);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(8, 0).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(7, 0).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(6, 0).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(5, 0).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(4, 0).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(3, 0).getValue());
  }

  public void testRotateHalfClockwise() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    Grid swapped = TransformationUtil.rotateHalfClockwise(original);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(8, 8).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(8, 7).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(8, 6).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(8, 5).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(8, 4).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(8, 3).getValue());
  }

  public void testMirrorVertically() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    Grid swapped = TransformationUtil.mirrorVertically(original);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(0, 8).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(0, 7).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(0, 6).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(0, 5).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(0, 4).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(0, 3).getValue());
  }

  public void testMirrorHorizontally() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    Grid swapped = TransformationUtil.mirrorHorizontally(original);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(8, 0).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(8, 1).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(8, 2).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(8, 3).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(8, 4).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(8, 5).getValue());
  }

  public void testMirrorDiagonally() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    Grid swapped = TransformationUtil.mirrorDiagonally(original);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(8, 8).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(7, 8).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(6, 8).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(5, 8).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(4, 8).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(3, 8).getValue());
  }

  public void testMirrorCounterDiagonally() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    Grid swapped = TransformationUtil.mirrorCounterDiagonally(original);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(0, 0).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(1, 0).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(2, 0).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(3, 0).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(4, 0).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(5, 0).getValue());
  }

  public void _testSwapArbitraryColumns() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    for (int i = 0; i < 10; i++) {
      TransformationUtil.swapArbitraryColumns(original);
    }
  }

  public void _testArbitraryTransformation1() {
    Grid original = SudokuFactory.INSTANCE.buildSudoku(Examples.LEICHT);
    Grid swapped = original;
    log.debug(original);
    for (int i = 0; i < 100; i++) {
      swapped = TransformationUtil.arbitraryTransformation(swapped);
    }
    log.debug(swapped);
  }

  public void _testArbitraryTransformation2() {
    Grid original = SudokuFactory.INSTANCE.buildFilled(SudokuSize.DEFAULT);
    Grid swapped = original;
    log.debug(original);
    for (int i = 0; i < 100; i++) {
      swapped = TransformationUtil.arbitraryTransformation(swapped);
      assertTrue(swapped.isValid());
    }
    log.debug(swapped);
  }

}
