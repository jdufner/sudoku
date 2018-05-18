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


package de.jdufner.sudoku.generator.service;

import junit.framework.TestCase;
import org.apache.log4j.Logger;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * 
 * 
 */
public class SudokuGeneratorServiceTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(SudokuGeneratorServiceTest.class);

  private SudokuGeneratorService sudokuGenerator;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
//    sudokuGenerator = (SudokuGeneratorService) GeneratorServiceFactory.INSTANCE.getBean(SudokuGeneratorService.class);
  }

  public void _testGetSudoku() throws Exception {
    LOG.debug("Start Test");
    sudokuGenerator.generate();
    LOG.debug("End Test");
  }

  public void testDummy() {
    assertTrue(true);
  }

}
