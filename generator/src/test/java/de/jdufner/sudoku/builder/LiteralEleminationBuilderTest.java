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
package de.jdufner.sudoku.builder;

import static org.junit.Assert.assertTrue;

import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.solver.service.Solution;
import de.jdufner.sudoku.test.AbstractGeneratorTestCase;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class LiteralEleminationBuilderTest extends AbstractGeneratorTestCase {
  private static final Logger LOG = Logger.getLogger(LiteralEleminationBuilderTest.class);

  private Builder builder;
  private Grid sudoku;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    builder = (LiteralEleminationBuilder) GeneratorServiceFactory.INSTANCE.getBean(LiteralEleminationBuilder.class);
    builder.setSize(SudokuSize.DEFAULT);
  }

  public void testBuild() throws Exception {
    sudoku = builder.build();
    LOG.debug("Prüfe Gültigkeit:");
    assertTrue(sudoku.isValid());
    LOG.debug("Prüfe Eindeutigkeit:");
    assertTrue(getStrategySolverWithBacktracking().isUnique(sudoku));
    LOG.debug(sudoku);
  }

  public void testBuildSudokus() {
    Map<Level, Solution> map = builder.buildSudokus();
  }

}
