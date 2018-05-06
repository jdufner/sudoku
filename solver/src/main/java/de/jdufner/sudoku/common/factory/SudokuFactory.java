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
package de.jdufner.sudoku.common.factory;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.SudokuSize;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomData;
import org.apache.commons.math3.random.RandomDataImpl;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Diese Klasse erzeugt verschiedene Spielfelder mit unterschiedlichen Schwierigkeitsgraden.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public enum SudokuFactory {

  INSTANCE;

  private static final Pattern SIZE_PATTERN = Pattern.compile("^(\\d+):");
  private static final Pattern CELLS_PATTERN = Pattern.compile(":([0-9,]+)$");
  private static final Pattern CANDIDATES_PATTERN_1 = Pattern.compile("^[0-9,\\(\\)]+$");
  private static final Pattern CANDIDATES_PATTERN_2 = Pattern.compile("^\\((\\d+)\\)$");

  private SudokuSize size = SudokuSize.DEFAULT;
  private RandomData randomData = new RandomDataImpl(new JDKRandomGenerator());

  public Grid buildSudoku(final String sudokuAsString) {
    if (SIZE_PATTERN.matcher(sudokuAsString).find()) {
      final Grid sudoku = new Grid(getGroesseFromString(sudokuAsString), getFelderFromString(sudokuAsString));
      if (!sudoku.isValid()) {
        throw new IllegalArgumentException("Das eingegebene Sudoku ist nicht gültig.");
      }
      return sudoku;
    } else {
      if (sudokuAsString.contains(",") && CANDIDATES_PATTERN_1.matcher(sudokuAsString).matches()) {
        final String[] felderAsStrings = Pattern.compile(",").split(sudokuAsString);
        final Cell[] cells = new Cell[felderAsStrings.length];
        for (int i = 0; i < felderAsStrings.length; i++) {
          if (felderAsStrings[i].contains("(") || felderAsStrings[i].contains(")")) {
            Matcher matcher = CANDIDATES_PATTERN_2.matcher(felderAsStrings[i]);
            if (matcher.matches()) {
              final String candidatesAsString = matcher.group(1);
              final Literal[] candidates = new Literal[candidatesAsString.length()];
              for (int j = 0; j < candidatesAsString.length(); j++) {
                candidates[j] = Literal.getInstance(Integer.parseInt(candidatesAsString.substring(j, j + 1)));
              }
              cells[i] = new Cell(i, null, Arrays.asList(candidates), SudokuSize.DEFAULT);
            }
          } else {
            cells[i] = new Cell(i, Literal.getInstance(Integer.parseInt(felderAsStrings[i])), null, SudokuSize.DEFAULT);
          }
        }
        return new Grid(SudokuSize.DEFAULT, cells);
      } else {
        final Grid sudoku = buildSudokuFrom81Chars(sudokuAsString);
        if (!sudoku.isValid()) {
          throw new IllegalArgumentException("Das eingegebene Sudoku ist nicht gültig.");
        }
        return sudoku;
      }
    }
  }

  private Grid buildSudokuFrom81Chars(final String sudokuAsString) {
    final char[] chars = sudokuAsString.toCharArray();
    Integer[] felder = new Integer[chars.length];
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '.') {
        felder[i] = Integer.valueOf(0);
      } else {
        felder[i] = Integer.valueOf(String.valueOf(chars[i]));
      }
    }
    final Grid sudoku = new Grid(SudokuSize.NEUN, felder);
    return sudoku;
  }

  private SudokuSize getGroesseFromString(final String boardAsString) {
    final Matcher matcher = SIZE_PATTERN.matcher(boardAsString);
    if (matcher.find()) {
      final int unitSize = Integer.parseInt(matcher.group(1));
      return SudokuSize.getByUnitSize(unitSize);
    }
    return SudokuSize.DEFAULT;
  }

  private Integer[] getFelderFromString(final String spielfeldAsString) {
    final Matcher matcher = CELLS_PATTERN.matcher(spielfeldAsString);
    if (matcher.find()) {
      final String felderAsString = matcher.group(1);
      final String[] felderAsStrings = Pattern.compile(",").split(felderAsString);
      Integer[] felder = new Integer[felderAsStrings.length];
      for (int i = 0; i < felderAsStrings.length; i++) {
        felder[i] = Integer.valueOf(felderAsStrings[i]);
      }
      return felder;
    }
    throw new IllegalStateException("Sudoku-Zeichenkette konnte nicht geparst werden.");
  }

  public Grid buildEmpty(final SudokuSize sudokuSize) {
    final Integer[] felder = new Integer[sudokuSize.getTotalSize()];
    for (int i = 0; i < sudokuSize.getTotalSize(); i++) {
      felder[i] = Integer.valueOf(0);
    }
    return new Grid(sudokuSize, felder);
  }

  /**
   * Creates a filled and valid Sudoku.
   * 
   * @param sudokuSize
   * @return
   */
  public Grid buildFilled(final SudokuSize sudokuSize) {
    final Grid sudoku = buildEmpty(sudokuSize);
    for (int i = 0; i < sudokuSize.getHouseSize(); i++) {
      for (int j = 0; j < sudokuSize.getHouseSize(); j++) {
        final int shiftLeft = (i % sudokuSize.getBoxHeight()) * sudokuSize.getBoxWidth();
        final int block = i / sudokuSize.getBoxHeight();
        int column = j - shiftLeft + block;
        if (column >= sudokuSize.getHouseSize()) {
          column -= sudokuSize.getHouseSize();
        }
        if (column < 0) {
          column += sudokuSize.getHouseSize();
        }
        sudoku.getCell(i, column).setValue(Literal.getInstance(j + 1));
      }
    }
    return sudoku;
  }

  private Grid buildShuffled(final SudokuSize sudokuSize, final RandomData randomData) {
    final Grid sudoku = buildEmpty(sudokuSize);
    final int[] literal = randomData.nextPermutation(sudokuSize.getHouseSize(), sudokuSize.getHouseSize());
    final int[] columnIndex = randomData.nextPermutation(sudokuSize.getHouseSize(), sudokuSize.getHouseSize());
    final int[] rowIndex = randomData.nextPermutation(sudokuSize.getHouseSize(), sudokuSize.getHouseSize());
    for (int i = 0; i < sudokuSize.getHouseSize(); i++) {
      sudoku.getCell(rowIndex[i], columnIndex[i]).setValue(Literal.getInstance(literal[i] + 1));
    }
    return sudoku;
  }

  /**
   * Nur für Tests verwenden, weil hier RandomDataImpl immer neu erzeugt wird.
   * 
   * @param sudokuSize
   * @return
   */
  public Grid buildShuffled(final SudokuSize sudokuSize) {
    return buildShuffled(sudokuSize, randomData);
  }

  public SudokuSize getSize() {
    return size;
  }

  public void setSize(final SudokuSize size) {
    this.size = size;
  }

  public RandomData getRandomData() {
    return randomData;
  }

}
