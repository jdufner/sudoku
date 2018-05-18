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


package de.jdufner.sudoku.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import de.jdufner.sudoku.test.AbstractGeneratorTestCase;
import org.apache.log4j.Logger;

public class SudokuDataTest extends AbstractGeneratorTestCase {

  private static final Logger LOG = Logger.getLogger(SudokuData.class);

  public void testEqualsAndHashCode() {
    SudokuData sudokuData1 = new SudokuData();
    sudokuData1.setId(1);
    SudokuData sudokuData2 = new SudokuData();
    sudokuData2.setId(1);
    SudokuData sudokuData3 = new SudokuData();
    sudokuData3.setId(1);
    assertEquals(sudokuData1.hashCode(), sudokuData2.hashCode());
    assertEquals(sudokuData2.hashCode(), sudokuData3.hashCode());
    assertEquals(sudokuData1.hashCode(), sudokuData3.hashCode());
    assertEquals(sudokuData1, sudokuData2);
    assertEquals(sudokuData2, sudokuData3);
    assertEquals(sudokuData1, sudokuData3);
    assertNotNull(sudokuData1.toString());
    LOG.debug(sudokuData1);
  }

}
