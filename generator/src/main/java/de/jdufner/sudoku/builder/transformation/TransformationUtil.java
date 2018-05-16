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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.BoxUtils;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.board.House;
import de.jdufner.sudoku.common.factory.SudokuFactory;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class TransformationUtil {
  private static final Logger log = Logger.getLogger(TransformationUtil.class);

  private static Random random;
  private static List<Method> transformationMethods;
  private static List<Method> symetricMethods;

  static {
    random = new Random();

    transformationMethods = new ArrayList<Method>();
    for (Method method : TransformationUtil.class.getMethods()) {
      for (Annotation annotation : method.getDeclaredAnnotations()) {
        if (annotation instanceof ArbitraryTransformation) {
          transformationMethods.add(method);
          break;
        }
      }
    }

    symetricMethods = new ArrayList<Method>();
    for (Method method : TransformationUtil.class.getMethods()) {
      for (Annotation annotation : method.getDeclaredAnnotations()) {
        if (annotation instanceof SymetricTransformation) {
          symetricMethods.add(method);
          break;
        }
      }
    }
  }

  /**
   * @param sudoku
   * @param column1
   * @param column2
   * @return Tauscht zwei Spalten innerhalb eines Blocks.
   */
  static Grid swapColumns(Grid sudoku, int column1, int column2) {
    assert sudoku.getBlock(0, column1) == sudoku.getBlock(0, column2) : column1 + " und " + column2
        + " sind nicht im selben Quandranten.";
    assert column1 < column2 : column1 + " ist nicht kleiner als " + column2;
    log.debug("Tausche Spalte " + column1 + " und " + column2);
    Grid newSudoku = SudokuFactory.INSTANCE.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        if (j == column1) {
          newSudoku.getCell(i, column2).setValue(sudoku.getCell(i, column1).getValue());
        } else if (j == column2) {
          newSudoku.getCell(i, column1).setValue(sudoku.getCell(i, column2).getValue());
        } else {
          newSudoku.getCell(i, j).setValue(sudoku.getCell(i, j).getValue());
        }
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  public static Grid swapArbitraryColumns(Grid sudoku) {
    int[] twoColumns = getTwoNumbersInOneColumnBlock(sudoku);
    return swapColumns(sudoku, twoColumns[0], twoColumns[1]);
  }

  private static int[] getTwoNumbersInOneColumnBlock(Grid sudoku) {
    int block = random.nextInt(sudoku.getSize().getBoxHeight()) * sudoku.getSize().getBoxWidth();
    int[] numbers = getTwoRandomNumbersByBlockWidth(sudoku);
    return new int[] { block + numbers[0], block + numbers[1] };
  }

  private static int[] getTwoNumbersInOneRowBlock(Grid sudoku) {
    int block = random.nextInt(sudoku.getSize().getBoxWidth()) * sudoku.getSize().getBoxHeight();
    int[] numbers = getTwoRandomNumbersByBlockHeight(sudoku);
    return new int[] { block + numbers[0], block + numbers[1] };
  }

  private static int[] getTwoRandomNumbersByBlockWidth(Grid sudoku) {
    return getTwoRandomNumbersBetween(0, sudoku.getSize().getBoxWidth());
  }

  private static int[] getTwoRandomNumbersByBlockHeight(Grid sudoku) {
    return getTwoRandomNumbersBetween(0, sudoku.getSize().getBoxHeight());
  }

  private static int[] getTwoRandomNumbersBetween(int min, int max) {
    assert max > min : "max: " + max + " muss größer als min: " + min + " sein.";
    int number1 = getRandomNumberBetween(min, max);
    int number2;
    do {
      number2 = getRandomNumberBetween(min, max);
    } while (number1 == number2);
    if (number1 > number2) {
      int swap = number1;
      number1 = number2;
      number2 = swap;
    }
    return new int[] { number1, number2 };
  }

  /**
   * @param min
   * @param max
   * @return A arbitrary integer value between min (inclusive) and max (exclusive).
   */
  private static int getRandomNumberBetween(int min, int max) {
    assert max > min : "max: " + max + " muss größer als min: " + min + " sein.";
    return random.nextInt(max - min) + min;
  }

  static Grid swapRows(Grid sudoku, int row1, int row2) {
    assert sudoku.getBlock(0, row1) == sudoku.getBlock(0, row2);
    assert row1 < row2;
    log.debug("Tausche Zeilen " + row1 + " und " + row2);
    Grid newSudoku = SudokuFactory.INSTANCE.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        if (i == row1) {
          newSudoku.getCell(row2, j).setValue(sudoku.getCell(row1, j).getValue());
        } else if (i == row2) {
          newSudoku.getCell(row1, j).setValue(sudoku.getCell(row2, j).getValue());
        } else {
          newSudoku.getCell(i, j).setValue(sudoku.getCell(i, j).getValue());
        }
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  public static Grid swapArbitraryRows(Grid sudoku) {
    int[] twoRows = getTwoNumbersInOneRowBlock(sudoku);
    return swapRows(sudoku, twoRows[0], twoRows[1]);
  }

  static Grid swapColumnBlock(Grid sudoku, int columnBlock1, int columnBlock2) {
    assert columnBlock1 >= 0 && columnBlock1 < sudoku.getSize().getBoxHeight();
    assert columnBlock2 >= 0 && columnBlock2 < sudoku.getSize().getBoxHeight();
    assert columnBlock1 < columnBlock2;
    Grid newSudoku = SudokuFactory.INSTANCE.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        if (isColumnIndexInBlock(j, sudoku, columnBlock1)) {
          newSudoku.getCell(i, j + (columnBlock2 - columnBlock1) * sudoku.getSize().getBoxWidth()).setValue(
              sudoku.getCell(i, j).getValue());
        } else if (isColumnIndexInBlock(j, sudoku, columnBlock2)) {
          newSudoku.getCell(i, j - (columnBlock2 - columnBlock1) * sudoku.getSize().getBoxWidth()).setValue(
              sudoku.getCell(i, j).getValue());
        } else {
          newSudoku.getCell(i, j).setValue(sudoku.getCell(i, j).getValue());
        }
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  public static Grid swapArbitraryColumnBlocks(Grid sudoku) {
    int[] twoColumnBlocks = getTwoRandomNumbersByBlockHeight(sudoku);
    return swapColumnBlock(sudoku, twoColumnBlocks[0], twoColumnBlocks[1]);
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Grid swapSymetricColumnBlocks(Grid sudoku) {
    int block = getRandomNumberBetween(0, (sudoku.getSize().getHouseSize() - 1) / 2);
    int[] columns = BoxUtils.getColumnsByBlock(block, sudoku.getSize());
    Grid result = sudoku;
    for (int i = 0; i < columns.length; i++) {
      result = swapColumns(result, columns[i], sudoku.getSize().getHouseSize() - columns[i] - 1);
    }
    return result;
  }

  static Grid swapRowBlock(Grid sudoku, int rowBlock1, int rowBlock2) {
    assert rowBlock1 >= 0 && rowBlock1 < sudoku.getSize().getBoxHeight();
    assert rowBlock2 >= 0 && rowBlock2 < sudoku.getSize().getBoxHeight();
    assert rowBlock1 < rowBlock2;
    Grid newSudoku = SudokuFactory.INSTANCE.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        if (isRowIndexInBlock(i, sudoku, rowBlock1)) {
          newSudoku.getCell(i + (rowBlock2 - rowBlock1) * sudoku.getSize().getBoxHeight(), j).setValue(
              sudoku.getCell(i, j).getValue());
        } else if (isRowIndexInBlock(i, sudoku, rowBlock2)) {
          newSudoku.getCell(i - (rowBlock2 - rowBlock1) * sudoku.getSize().getBoxHeight(), j).setValue(
              sudoku.getCell(i, j).getValue());
        } else {
          newSudoku.getCell(i, j).setValue(sudoku.getCell(i, j).getValue());
        }
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  public static Grid swapArbitraryRowBlocks(Grid sudoku) {
    int[] twoRowBlocks = getTwoRandomNumbersByBlockWidth(sudoku);
    return swapRowBlock(sudoku, twoRowBlocks[0], twoRowBlocks[1]);
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Grid swapSymetricRowBlocks(Grid sudoku) {
    int block = getRandomNumberBetween(0, (sudoku.getSize().getHouseSize() - 1) / 2);
    int[] columns = BoxUtils.getColumnsByBlock(block, sudoku.getSize());
    Grid result = sudoku;
    for (int i = 0; i < columns.length; i++) {
      result = swapRows(result, columns[i], sudoku.getSize().getHouseSize() - columns[i] - 1);
    }
    return result;
  }

  /**
   * @param columnIndex
   *          Spaltenindex
   * @param sudoku
   *          Ein {@link Grid}, das eine Referenz auf eine {@link SudokuSize} hat.
   * @param block
   *          Blockindex im Sinne von {@link House#getIndex()}
   * @return <code>true</code>, wenn der Spaltenindex im angegebenen Block ist, sonst <code>false</code>.
   */
  private static boolean isColumnIndexInBlock(int columnIndex, Grid sudoku, int block) {
    if (columnIndex >= getSmallestColumnIndexOfBlock(sudoku, block)
        && columnIndex <= getLargestColumnIndexOfBlock(sudoku, block)) {
      return true;
    }
    return false;
  }

  /**
   * @param rowIndex
   *          Zeilenindex
   * @param sudoku
   *          Ein {@link Grid}, das eine Referenz auf eine {@link SudokuSize} hat.
   * @param block
   *          Blockindex im Sinne von {@link House#getIndex()}
   * @return <code>true</code>, wenn der Zeilenindex im angegebenen Block ist, sonst <code>false</code>.
   */
  private static boolean isRowIndexInBlock(int rowIndex, Grid sudoku, int block) {
    if (rowIndex >= getSmallestRowIndexOfBlock(sudoku, block) && rowIndex <= getLargestRowIndexOfBlock(sudoku, block)) {
      return true;
    }
    return false;
  }

  /**
   * @param sudoku
   * @param block
   * @return Gibt den kleinsten Spaltenindex eines Blocks zurück.
   */
  private static int getSmallestColumnIndexOfBlock(Grid sudoku, int block) {
    assert block >= 0 && block < sudoku.getSize().getBoxWidth();
    return sudoku.getSize().getBoxWidth() * block;
  }

  /**
   * @param sudoku
   * @param block
   * @return Gibt den größten Spaltenindex eines Blocks zurück.
   */
  private static int getLargestColumnIndexOfBlock(Grid sudoku, int block) {
    assert block >= 0 && block < sudoku.getSize().getBoxWidth();
    return sudoku.getSize().getBoxWidth() * (block + 1) - 1;
  }

  /**
   * @param sudoku
   * @param block
   * @return Gibt den kleinsten Zeilenindex eines Blocks zurück.
   */
  private static int getSmallestRowIndexOfBlock(Grid sudoku, int block) {
    assert block >= 0 && block < sudoku.getSize().getBoxHeight();
    return sudoku.getSize().getBoxHeight() * block;
  }

  /**
   * @param sudoku
   * @param block
   * @return Gibt den größten Zeilenindex eines Blocks zurück.
   */
  private static int getLargestRowIndexOfBlock(Grid sudoku, int block) {
    assert block >= 0 && block < sudoku.getSize().getBoxHeight();
    return sudoku.getSize().getBoxHeight() * (block + 1) - 1;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Grid rotateBlockClockwise(Grid sudoku) {
    Grid newSudoku = SudokuFactory.INSTANCE.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        newSudoku.getCell(j, sudoku.getSize().getHouseSize() - i - 1).setValue(sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Grid rotateBlockCounterClockwise(Grid sudoku) {
    Grid newSudoku = SudokuFactory.INSTANCE.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        newSudoku.getCell(sudoku.getSize().getHouseSize() - j - 1, i).setValue(sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Grid rotateHalfClockwise(Grid sudoku) {
    Grid newSudoku = SudokuFactory.INSTANCE.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        newSudoku.getCell(sudoku.getSize().getHouseSize() - i - 1, sudoku.getSize().getHouseSize() - j - 1).setValue(
            sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Grid mirrorVertically(Grid sudoku) {
    Grid newSudoku = SudokuFactory.INSTANCE.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        newSudoku.getCell(i, sudoku.getSize().getHouseSize() - j - 1).setValue(sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Grid mirrorHorizontally(Grid sudoku) {
    Grid newSudoku = SudokuFactory.INSTANCE.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        newSudoku.getCell(sudoku.getSize().getHouseSize() - i - 1, j).setValue(sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Grid mirrorDiagonally(Grid sudoku) {
    Grid newSudoku = SudokuFactory.INSTANCE.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        newSudoku.getCell(sudoku.getSize().getHouseSize() - j - 1, sudoku.getSize().getHouseSize() - i - 1).setValue(
            sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Grid mirrorCounterDiagonally(Grid sudoku) {
    Grid newSudoku = SudokuFactory.INSTANCE.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        newSudoku.getCell(j, i).setValue(sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Grid swapValues(Grid sudoku) {
    int randomNumber[] = getTwoRandomNumbersBetween(1, sudoku.getSize().getHouseSize());
    assert randomNumber[0] >= 1 && randomNumber[0] <= sudoku.getSize().getHouseSize();
    Literal l1 = Literal.getInstance(randomNumber[0]);
    assert randomNumber[1] >= 1 && randomNumber[1] <= sudoku.getSize().getHouseSize();
    Literal l2 = Literal.getInstance(randomNumber[1]);
    return swapValues(sudoku, l1, l2);
  }

  private static Grid swapValues(Grid sudoku, Literal l1, Literal l2) {
    Grid newSudoku = SudokuFactory.INSTANCE.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getHouseSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getHouseSize(); j++) {
        if (sudoku.getCell(i, j).getValue().equals(l1)) {
          newSudoku.getCell(i, j).setValue(l2);
        } else if (sudoku.getCell(i, j).getValue().equals(l2)) {
          newSudoku.getCell(i, j).setValue(l1);
        } else {
          newSudoku.getCell(i, j).setValue(sudoku.getCell(i, j).getValue());
        }
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  public static Grid arbitraryTransformation(Grid sudoku) {
    int methodNumber = 0;
    Method arbitraryTransformation = null;
    try {
      methodNumber = getRandomNumberBetween(0, transformationMethods.size());
      arbitraryTransformation = transformationMethods.get(methodNumber);
      log.debug("Führe beliebige Transformation aus: " + arbitraryTransformation.getName());
      return (Grid) arbitraryTransformation.invoke(null, new Object[] { sudoku });
    } catch (IllegalAccessException iae) {
      log.error(iae.getMessage() + " methodNumber=" + methodNumber + ", arbitraryTransformation="
          + arbitraryTransformation, iae);
    } catch (IllegalArgumentException iae) {
      log.error(iae.getMessage(), iae);
    } catch (InvocationTargetException ite) {
      log.error(ite.getMessage(), ite);
      throw new RuntimeException(ite.getCause());
    }
    return null;
  }

  public static Grid arbitraryTransformation(Grid sudoku, int numberTransformation) {
    Grid newSudoku = sudoku;
    for (int i = 0; i < numberTransformation; i++) {
      newSudoku = arbitraryTransformation(newSudoku);
    }
    return newSudoku;
  }

}
