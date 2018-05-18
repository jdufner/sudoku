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


package de.jdufner.sudoku.builder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.solver.service.Solution;
import de.jdufner.sudoku.solver.service.Solver;
import de.jdufner.sudoku.test.AbstractGeneratorTestCase;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * 
 */
public final class SymetricRandomEleminationBuilderTest extends AbstractGeneratorTestCase {
  private static final Logger LOG = Logger.getLogger(SymetricRandomEleminationBuilderTest.class);

  private Builder builder;
  private Solver strategySolver;
  private Solver strategySolverWithBacktracking;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    builder = (SymetricRandomEleminationBuilder) GeneratorServiceFactory.INSTANCE
        .getBean(SymetricRandomEleminationBuilder.class);
    builder.setSize(SudokuSize.DEFAULT);
    strategySolver = getStrategySolver();
    strategySolverWithBacktracking = getStrategySolverWithBacktracking();
  }

  public void testBuild() throws Exception {
    Grid sudoku = builder.build();
    assertFalse(sudoku.isSolved());
    assertTrue(strategySolverWithBacktracking.isUnique(sudoku));
    assertFalse(sudoku.isSolved());
    assertTrue(strategySolverWithBacktracking.isSolvable(sudoku));
  }

  public void testBuildSudokus() throws Exception {
    Map<Level, Solution> map = builder.buildSudokus();
    assertTrue(map.size() >= 1);
    LOG.debug(map.size() + " Sudokus gefunden.");
    for (Level level : map.keySet()) {
      if (level.equals(Level.SEHR_SCHWER)) {
        Grid result = strategySolverWithBacktracking.solve(map.get(level).getQuest());
        assertTrue(result.isSolved());
        assertTrue(result.isSolvedByCheckSum());
        assertTrue(result.isValid());
        assertNotNull(map.get(level).getLevel());
        assertTrue(Level.UNBEKANNT.compareTo(map.get(level).getLevel()) < 0);
      } else {
        Grid result = strategySolver.solve(map.get(level).getQuest());
        assertTrue(result.isSolved());
        assertTrue(result.isSolvedByCheckSum());
        assertTrue(result.isValid());
        assertNotNull(map.get(level).getLevel());
        assertTrue(Level.UNBEKANNT.compareTo(map.get(level).getLevel()) < 0);
      }
    }
  }

}
